package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Item;
import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.model.ProviderDTO;
import com.company.ordersbackend.repository.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProviderServiceTest {

    @Autowired
    DTOMapper dtoMapper;

    @Mock
    ProviderRepository providerRepository;

    @Mock
    Errors errors;

    ProviderService providerService;

    @BeforeEach
    void init(){
        providerService = new ProviderService(providerRepository, dtoMapper);
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

    @Test
    public void saveTest_noErrors(){
        //given
        ProviderDTO providerDTO = new ProviderDTO("XXX");
        Provider provider = new Provider("XXX");
        //when
        when(providerRepository.save(provider)).thenReturn(provider);
        when(errors.hasErrors()).thenReturn(false);
        Optional<ProviderDTO> result = providerService.save(providerDTO, errors);
        //then
        assertTrue(result.isPresent());
        assertThat(result.get(), instanceOf(ProviderDTO.class));
        assertEquals(result.get().getName(), providerDTO.getName());
    }

    @Test
    public void saveTest_hasErrors(){
        //given
        //when
        when(errors.hasErrors()).thenReturn(true);
        Optional<ProviderDTO> result = providerService.save(new ProviderDTO("XXX"), errors);
        //then
        assertFalse(result.isPresent());
    }

    @Test
    public void deleteById_Exists(){
        //given
        long id = 1L;
        Provider provider = new Provider("XXX");
        //when
        when(providerRepository.existsById(id)).thenReturn(true);
        when(providerRepository.findById(id)).thenReturn(Optional.of(provider));
        doNothing().when(providerRepository).deleteById(id);
        boolean result = providerService.deleteById(id);
        //then
        assertTrue(result);
        verify(providerRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteById_NotExists(){
        //given
        long id = 1L;
        //when
        when(providerRepository.existsById(id)).thenReturn(false);
        //then
        boolean result = providerService.deleteById(id);
        assertFalse(result);
        verify(providerRepository, times(0)).deleteById(id);
    }

    @Test
    public void deleteById_ItemListIsNotEmpty(){
        //given
        long id = 1L;
        Provider provider = new Provider("XXX");
        provider.getItemList().add(new Item());

        //when
        when(providerRepository.existsById(id)).thenReturn(true);
        when(providerRepository.findById(id)).thenReturn(Optional.of(provider));
        boolean result = providerService.deleteById(id);
        //then
        assertFalse(result);
    }

    @Test
    public void updateTest(){
        //given
        long id  = 1L;
        ProviderDTO providerDTO = new ProviderDTO(id, "XXX");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(providerRepository.existsById(id)).thenReturn(true);
        when(providerRepository.findById(id)).thenReturn(Optional.of(dtoMapper.providerPOJO(providerDTO)));
        boolean result = providerService.update(id, providerDTO, errors);
        //then
        assertTrue(result);
        verify(providerRepository, times(1)).save(any());
    }

    @Test
    public void updateTest_hasErrors(){
        //given
        long id  = 1L;
        ProviderDTO providerDTO = new ProviderDTO(id, "XXXX");
        //when
        when(errors.hasErrors()).thenReturn(true);
        when(providerRepository.existsById(id)).thenReturn(true);
        boolean result = providerService.update(id, providerDTO, errors);
        //then
        assertFalse(result);
    }


    @Test
    public void updateTest_notExists(){
        //given
        long id  = 1L;
        ProviderDTO providerDTO = new ProviderDTO(id, "XXXX");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(providerRepository.existsById(id)).thenReturn(false);
        boolean result = providerService.update(id, providerDTO, errors);
        //then
        assertFalse(result);
    }
}