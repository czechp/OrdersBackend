package com.company.ordersbackend;

import com.company.ordersbackend.domain.ItemCategory;
import com.company.ordersbackend.repository.ItemCategoryRepository;
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


    public Setup(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
      log.info("Development mode active");

      itemCategoryRepository.save(new ItemCategory("PLC"));
      itemCategoryRepository.save(new ItemCategory("HMI"));

        System.out.println(itemCategoryRepository.findAll());

    }
}
