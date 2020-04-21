package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.model.ProviderDTO;
import com.company.ordersbackend.repository.ProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderService {
    private ProviderRepository providerRepository;
    private ModelMapper  modelMapper;

    public ProviderService(ProviderRepository providerRepository, ModelMapper modelMapper) {
        this.providerRepository = providerRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProviderDTO> findAll(){
        return mapToList(providerRepository.findAll());
    }

    private List<ProviderDTO> mapToList(List<Provider> all) {
        List<ProviderDTO>  result = new ArrayList<>();
        for (Provider provider : all) {
            result.add(modelMapper.map(provider, ProviderDTO.class));
        }
        return result;
    }
}
