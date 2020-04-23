package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.model.ProviderDTO;
import com.company.ordersbackend.repository.ProviderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<ProviderDTO> save(ProviderDTO providerDTO, Errors errors){
        if(!errors.hasErrors()){
            Provider result = providerRepository.save(modelMapper.map(providerDTO, Provider.class));
            return Optional.of(modelMapper.map(result, ProviderDTO.class));
        }

        return Optional.empty();
    }

    public boolean deleteById(long id){
        if(providerRepository.existsById(id)){
            providerRepository.deleteById(id);
            return true;
        }

        return false;
    }
    private List<ProviderDTO> mapToList(List<Provider> all) {
        List<ProviderDTO>  result = new ArrayList<>();
        for (Provider provider : all) {
            result.add(modelMapper.map(provider, ProviderDTO.class));
        }
        return result;
    }


}
