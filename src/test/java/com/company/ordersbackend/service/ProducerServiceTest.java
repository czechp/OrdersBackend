package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Producer;
import com.company.ordersbackend.model.ProducerDTO;
import com.company.ordersbackend.repository.ProducerRepository;
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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
@RunWith(SpringRunner.class)
class ProducerServiceTest {

    @Autowired
    private DTOMapper dtoMapper;

    @Mock
    private ProducerRepository producerRepository;

    @Mock
    Errors errors;


    private ProducerService producerService;

    @BeforeEach
    public void init(){
        this.producerService = new ProducerService(producerRepository, dtoMapper);
    }

    @Test
    void findAll() {
        //given
        List<Producer> producers = Arrays.asList(
                new Producer(1L, "XXX"),
                new Producer(2L, "YYY")
        );
        //when
        when(producerRepository.findAll()).thenReturn(producers);
        List<ProducerDTO> result = producerService.findAll();
        //then
        assertThat(result, hasSize(producers.size()));
        assertThat(result.get(0), instanceOf(ProducerDTO.class));
    }

    @Test
    public void saveTest(){
        //given
        ProducerDTO producerDTO = new ProducerDTO(0L, "XXX");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(producerRepository.save(any())).thenReturn(dtoMapper.producerPOJO(producerDTO));
        Optional<ProducerDTO> result = producerService.save(producerDTO, errors);
        //then
        assertTrue(result.isPresent());
        assertEquals(result.get().getName(), producerDTO.getName());
        verify(producerRepository, times(1)).save(any());
    }

    @Test
    public void updateTest(){
        //given
        long id = 1L;
        ProducerDTO producerDTO = new ProducerDTO(id, "XXX");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(producerRepository.existsById(any())).thenReturn(true);
        when(producerRepository.findById(id)).thenReturn(Optional.of(dtoMapper.producerPOJO(producerDTO)));
        boolean result = producerService.update(id, producerDTO, errors);

        //then
        assertTrue(result);
        verify(producerRepository, times(1)).save(any());
    }
}