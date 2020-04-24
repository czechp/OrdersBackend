package com.company.ordersbackend;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.domain.Producer;
import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.repository.ItemCategoryRepository;
import com.company.ordersbackend.repository.ItemRepository;
import com.company.ordersbackend.repository.ProducerRepository;
import com.company.ordersbackend.repository.ProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("development")
@Slf4j
public class Setup {

    private ItemCategoryRepository itemCategoryRepository;
    private ProviderRepository providerRepository;
    private ProducerRepository producerRepository;
    private ItemRepository itemRepository;

    public Setup(ItemCategoryRepository itemCategoryRepository, ProviderRepository providerRepository, ProducerRepository producerRepository, ItemRepository itemRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.providerRepository = providerRepository;
        this.producerRepository = producerRepository;
        this.itemRepository = itemRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Development mode active");

        itemCategoryRepository.save(new ItemCategory("PLC"));
        itemCategoryRepository.save(new ItemCategory("HMI"));


        providerRepository.save(new Provider("XXX"));
        providerRepository.save(new Provider("ZZZ"));

        producerRepository.save(new Producer("IFM"));
        Producer producer = producerRepository.findById(1L).get();
        System.out.println(producer);

        Provider provider = providerRepository.findById(1L).get();
        System.out.println(provider);
        ItemCategory itemCategory = itemCategoryRepository.findById(1L).get();
        System.out.println(itemCategory);


        Item item = new Item("stycznik", "xxx1213", "asdasda", "sadsadsadas", producer, provider, itemCategory);


        itemRepository.save(item);

        System.out.println(itemRepository.findAll());
    }
}
