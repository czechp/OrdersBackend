package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.exception.BadRequestException;
import com.company.ordersbackend.model.ItemDTO;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service()
public class CsvMapperService {
    private CsvSchema csvSchema;
    private CsvMapper csvMapper;

    @Autowired()
    public CsvMapperService(CsvSchema csvSchema, CsvMapper csvMapper) {
        this.csvSchema = csvSchema;
        this.csvMapper = csvMapper;
    }

    public List<ItemDTO> mapToItem(MultipartFile multipartFile, long categoryId){
        try {
            MappingIterator<Object> itemsList = csvMapper.readerFor(Item.class)
                    .with(csvSchema)
                    .readValues(new InputStreamReader(multipartFile.getInputStream()));
            List<Item> items = itemsList.readAll().stream()
                    .map(x -> (Item) x)
                    .collect(Collectors.toList());

            items.forEach(x-> System.out.println(x));

        } catch (IOException e) {
            throw new BadRequestException(multipartFile.getOriginalFilename());
        }

        return null;
    }

}
