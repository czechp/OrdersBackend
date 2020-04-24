package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Producer;
import com.company.ordersbackend.model.ProducerDTO;
import com.company.ordersbackend.repository.ProducerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<ProducerDTO> save(ProducerDTO producerDTO, Errors erros){
        if(!erros.hasErrors()){
            Producer result = producerRepository.save(dtoMapper.producerPOJO(producerDTO));
            return Optional.of(dtoMapper.producerDTO(result));
        }

        return Optional.empty();
    }

    public boolean update(long id, ProducerDTO producerDTO,  Errors errors){
        if(producerRepository.existsById(id) && !errors.hasErrors()){
            Producer producer = producerRepository.findById(id).get();
            producer.setName(producerDTO.getName());
            producerRepository.save(producer);
            return true;
        }
        return false;
    }
    private List<ProducerDTO> mapToList(List<Producer> all) {
        List<ProducerDTO> result = new ArrayList<>();
        for (Producer producer : all) {
            result.add(dtoMapper.producerDTO(producer));
        }
        return result;
    }

}
