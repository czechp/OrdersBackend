package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.ItemInOrder;
import com.company.ordersbackend.model.ItemInOrderDTO;
import com.company.ordersbackend.repository.ItemInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service()
public class ItemInOrderService {
    private ItemInOrderRepository itemInOrderRepository;
    private DTOMapper dtoMapper;

    @Autowired()
    public ItemInOrderService(ItemInOrderRepository itemInOrderRepository, DTOMapper dtoMapper) {
        this.itemInOrderRepository = itemInOrderRepository;
        this.dtoMapper = dtoMapper;
    }

    //todo Make test for it
    public Optional<ItemInOrderDTO> update(ItemInOrderDTO itemInOrderDTO, Errors errors) {
        if(itemInOrderRepository.existsById(itemInOrderDTO.getId()) && !errors.hasErrors()){
            ItemInOrder result = itemInOrderRepository.save(dtoMapper.itemInOrderPOJO(itemInOrderDTO));
            return Optional.of(dtoMapper.itemInOrderDTO(result));
        }
        return Optional.empty();
    }

    public List<ItemInOrderDTO> findAll() {
        return toDtoArray(itemInOrderRepository.findAll());
    }

    private List<ItemInOrderDTO> toDtoArray(List<ItemInOrder> all) {
        return all.stream()
                .map(x-> dtoMapper.itemInOrderDTO(x))
                .collect(Collectors.toList());
    }
}
