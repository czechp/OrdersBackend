package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Producer;
import com.company.ordersbackend.model.ProducerDTO;
import com.company.ordersbackend.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProducerService {
    private ProducerRepository producerRepository;
    private DTOMapper dtoMapper;

    public ProducerService(ProducerRepository producerRepository, DTOMapper dtoMapper) {
        this.producerRepository = producerRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<ProducerDTO> findAll() {
        return mapToList(producerRepository.findAll());
    }

    private List<ProducerDTO> mapToList(List<Producer> all) {
        List<ProducerDTO> result = new ArrayList<>();
        for (Producer producer : all) {
            result.add(dtoMapper.producerDTO(producer));
        }
        return result;
    }

}
