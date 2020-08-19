package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.domain.Producer;
import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.exception.BadRequestException;
import com.company.ordersbackend.exception.NotFoundException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service()
public class CsvMapperService {
    private CsvSchema csvSchema;
    private CsvMapper csvMapper;
    private ProducerService producerService;
    private ItemCategoryService itemCategoryService;
    private ItemService itemService;
    private ProviderService providerService;

    @Autowired()
    public CsvMapperService(ProducerService producerService,
                            ItemService itemService,
                            ItemCategoryService itemCategoryService,
                            ProviderService providerService) {
        this.csvSchema = CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
        this.csvMapper = new CsvMapper();
        this.producerService = producerService;
        this.itemService = itemService;
        this.itemCategoryService = itemCategoryService;
        this.providerService = providerService;
    }

    public void mapToItem(MultipartFile multipartFile, long categoryId) {
        ItemCategory itemCategory = itemCategoryService.findById(categoryId).orElseThrow(() -> new NotFoundException(" category id - " + categoryId));
        try {
            MappingIterator<Object> itemsList = csvMapper.readerFor(Item.class)
                    .with(csvSchema)
                    .readValues(new InputStreamReader(multipartFile.getInputStream(), "UTF-8"));
            List<Item> items = itemsList.readAll().stream()
                    .map(x -> (Item) x)
                    .collect(Collectors.toList());
            checkNewProducers(items);
            saveItems(items, itemCategory);
        } catch (IOException e) {
            throw new BadRequestException(multipartFile.getOriginalFilename());
        }
    }

    private void saveItems(List<Item> items, ItemCategory itemCategory) {
        Provider provider = providerService.findByName("Inny").orElseGet(() -> providerService.save(new Provider("Inny")));
        List<Item> result = items.stream()
                .filter(x -> producerService.existByName(x.getProducer().getName()) && !itemService.existsByName(x.getName()))
                .collect(Collectors.toList());
        result.stream().forEach(
                x -> {
                    x.setProducer(producerService.findByName(x.getProducer().getName()).get());
                    x.setItemCategory(itemCategory);
                    x.setProvider(provider);
                }
        );
        itemService.saveAll(result);

    }

    private void checkNewProducers(List<Item> items) {
        items.stream()
                .filter(x -> !producerService.existByName(x.getProducer().getName()))
                .forEach(x -> producerService.save(new Producer(x.getProducer().getName())));
    }

}
