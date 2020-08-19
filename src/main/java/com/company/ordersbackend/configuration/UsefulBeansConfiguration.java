package com.company.ordersbackend.configuration;

import com.company.ordersbackend.service.CsvToItem;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsefulBeansConfiguration {


    @Bean()
    public CsvSchema getCsvSchema(){
        return CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
    }

    @Bean()
    public CsvMapper getCsvMapper(){
        return new CsvMapper();
    }
}
