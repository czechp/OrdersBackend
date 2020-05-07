package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.Provider;
import com.company.ordersbackend.model.ProviderDTO;
import com.company.ordersbackend.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {
    private ProviderRepository providerRepository;
    private DTOMapper dtoMapper;

    public ProviderService(ProviderRepository providerRepository, DTOMapper dtoMapper) {
        this.providerRepository = providerRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<ProviderDTO> findAll() {
        return mapToList(providerRepository.findAll());
    }

    public Optional<ProviderDTO> save(ProviderDTO providerDTO, Errors errors) {
        if (!errors.hasErrors()) {
            Provider result = providerRepository.save(dtoMapper.providerPOJO(providerDTO));
            return Optional.of(dtoMapper.providerDTO(result));
        }

        return Optional.empty();
    }

    public boolean deleteById(long id) {
        if (providerRepository.existsById(id)) {
            if(!providerRepository.findById(id).get().getItemList().isEmpty()){
                providerRepository.deleteById(id);
                return true;
            }

        }

        return false;
    }

    public boolean update(long id, ProviderDTO providerDTO, Errors errors) {
        if (providerRepository.existsById(id) && !errors.hasErrors()) {
            Provider provider = providerRepository.findById(id).get();
            provider.setName(providerDTO.getName());
            providerRepository.save(provider);
            return true;
        }
        return false;
    }

    private List<ProviderDTO> mapToList(List<Provider> all) {
        List<ProviderDTO> result = new ArrayList<>();
        for (Provider provider : all) {
            result.add(dtoMapper.providerDTO(provider));
        }
        return result;
    }


}
