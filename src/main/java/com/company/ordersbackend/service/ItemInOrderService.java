package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.ItemInOrder;
import com.company.ordersbackend.domain.ItemStatus;
import com.company.ordersbackend.exception.AccessDeniedException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.model.ItemInOrderDTO;
import com.company.ordersbackend.repository.ItemInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service()
public class ItemInOrderService {
    private ItemInOrderRepository itemInOrderRepository;
    private DTOMapper dtoMapper;
    private AppUserService appUserService;


    @Autowired()
    public ItemInOrderService(ItemInOrderRepository itemInOrderRepository, DTOMapper dtoMapper, AppUserService appUserService) {
        this.itemInOrderRepository = itemInOrderRepository;
        this.dtoMapper = dtoMapper;
        this.appUserService = appUserService;
    }

    public Optional<ItemInOrderDTO> update(ItemInOrderDTO itemInOrderDTO, Errors errors) {
        if (itemInOrderRepository.existsById(itemInOrderDTO.getId()) && !errors.hasErrors()) {
            ItemInOrder result = itemInOrderRepository.save(dtoMapper.itemInOrderPOJO(itemInOrderDTO));
            return Optional.of(dtoMapper.itemInOrderDTO(result));
        }
        return Optional.empty();
    }

    public List<ItemInOrderDTO> findAll() {
        return toDtoArray(itemInOrderRepository.findAll());
    }

    public Optional<ItemInOrderDTO> delete(long id) {
        Optional<ItemInOrder> optionalItemInOrder = itemInOrderRepository.findById(id);
        if (optionalItemInOrder.isPresent()) {
            itemInOrderRepository.deleteById(id);
            return Optional.of(dtoMapper.itemInOrderDTO(optionalItemInOrder.get()));
        }
        return Optional.empty();
    }

    public Optional<ItemInOrderDTO> findById(long id) {
        Optional<ItemInOrder> result = itemInOrderRepository.findById(id);
        return result.map(itemInOrder -> dtoMapper.itemInOrderDTO(itemInOrder));
    }

    public ItemInOrderDTO changeStatus(final long id, ItemStatus itemStatus, Principal principal) {
        if (appUserService.isSuperUser(principal)) {
            ItemInOrder itemInOrder = itemInOrderRepository.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
            if (itemStatus == ItemStatus.ORDERED) {
                setOrdered(itemInOrder);
            } else {
                setDelivered(itemInOrder);
            }
            return dtoMapper.itemInOrderDTO(itemInOrderRepository.save(itemInOrder));
        } else {
            throw new AccessDeniedException("FORBIDEN");
        }
    }

    private void setOrdered(ItemInOrder itemInOrder) {
        itemInOrder.setOrdered(true);
        itemInOrder.setOrderDate(LocalDateTime.now());
    }


    private void setDelivered(ItemInOrder itemInOrder) {
        itemInOrder.setDelivered(true);
        itemInOrder.setOrdered(false);
        itemInOrder.setDeliverDate(LocalDateTime.now());
    }


    private List<ItemInOrderDTO> toDtoArray(List<ItemInOrder> all) {
        return all.stream()
                .map(x -> dtoMapper.itemInOrderDTO(x))
                .collect(Collectors.toList());
    }


    public List<ItemInOrderDTO> findAllOrderedItems() {
        return itemInOrderRepository.findByIsOrderedAndIsDelivered(true, false)
                .stream()
                .map(x-> dtoMapper.itemInOrderDTO(x))
                .collect(Collectors.toList());
    }


}
