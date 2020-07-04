package com.company.ordersbackend;

import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("development")
@Slf4j
public class Setup {

    private ItemCategoryRepository itemCategoryRepository;
    private ProviderRepository providerRepository;
    private ProducerRepository producerRepository;
    private ItemRepository itemRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    private ItemInOrderRepository itemInOrderRepository;

    public Setup(ItemCategoryRepository itemCategoryRepository, ProviderRepository providerRepository, ProducerRepository producerRepository, ItemRepository itemRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, ItemInOrderRepository itemInOrderRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.providerRepository = providerRepository;
        this.producerRepository = producerRepository;
        this.itemRepository = itemRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemInOrderRepository = itemInOrderRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Development mode active");

        itemCategoryRepository.save(new ItemCategory("PLC"));
        itemCategoryRepository.save(new ItemCategory("HMI"));
        itemCategoryRepository.save(new ItemCategory("Osprzet"));


        providerRepository.save(new Provider("ElektroADA"));
        providerRepository.save(new Provider("DolinaSerwis"));
        providerRepository.save(new Provider("Internet"));

        producerRepository.save(new Producer("IFM"));
        producerRepository.save(new Producer("Siemens"));
        producerRepository.save(new Producer("Adidas"));
        Producer producer = producerRepository.findById(1L).get();

        Provider provider = providerRepository.findById(1L).get();
        ItemCategory itemCategory = itemCategoryRepository.findById(1L).get();

        Item item1 = new Item("Stycznik", "123", "32a", "https://www.google.com/", producer, provider, itemCategory);
        Item item2 = new Item("Zabezpieczenie termiczne", "123", "32A", "http://123.com", producer, provider, itemCategory);
        Item item3 = new Item("Bezpiecznik", "123", "32A", "http://123.com", producer, provider, itemCategory);

        itemRepository.saveAll(Arrays.asList(item1, item2, item3));
        AppUser user = new AppUser("user", passwordEncoder.encode("user"), "user", "webcodsdaerc@gmail.com");
        user.setActive(true);
        user = appUserRepository.save(user);

        ItemInOrder itemInOrder = new ItemInOrder(item1);
        itemInOrderRepository.save(itemInOrder);

    }
}
