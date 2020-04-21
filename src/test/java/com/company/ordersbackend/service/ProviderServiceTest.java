package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.model.ProviderDTO;
import com.company.ordersbackend.repository.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProviderServiceTest {

    @Autowired
    ModelMapper modelMapper;

    @Mock
    ProviderRepository providerRepository;

    ProviderService providerService;

    @BeforeEach
    void init(){
        providerService = new ProviderService(providerRepository, modelMapper);
    }

    @Test
    void findAll() {
        //given
        List<Provider> providerList = Arrays.asList(
                new Provider("Xxx"),
                new Provider("Yyy"),
                new Provider("Zzz")
        );
        //when
        when(providerRepository.findAll()).thenReturn(providerList);
        List<ProviderDTO> result = providerService.findAll();
        //then
        assertThat(result, hasSize(providerList.size()));
        assertThat(result.get(0), instanceOf(ProviderDTO.class));
        assertEquals(result.get(0).getName(), providerList.get(0).getName());
        assertEquals(result.get(0).getId(), providerList.get(0).getId());
    }
}