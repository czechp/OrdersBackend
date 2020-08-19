package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.ItemSuperClass;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service()
public class CsvToItem {
    private CsvSchema csvSchema;
    private CsvMapper csvMapper;

    @Autowired()
    public CsvToItem(CsvSchema csvSchema, CsvMapper csvMapper) {
        this.csvSchema = csvSchema;
        this.csvMapper = csvMapper;
    }

    public void readItems(){
        File file = new File("test.csv");
        try {
            MappingIterator<Object> objectMappingIterator = csvMapper.readerFor(Item.class).with(csvSchema).readValues(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));
            List<Item> collect = objectMappingIterator.readAll().stream()
                    .map(x -> (Item) x)
                    .collect(Collectors.toList());

            System.out.println(collect);
            System.out.println(collect.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        readItems();
    }
}
