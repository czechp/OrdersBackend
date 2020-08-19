package com.company.ordersbackend.configuration;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsefulBeansConfiguration {

    @Bean()
    public CsvSchema getCsvSchema() {
        return CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
    }

    @Bean()
    public CsvMapper getCsvMapper() {
        return new CsvMapper();
    }

}
