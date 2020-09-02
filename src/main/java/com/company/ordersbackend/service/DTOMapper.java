package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.model.*;
import org.springframework.stereotype.Service;

@Service
public class DTOMapper {

    public ItemCategoryDTO itemCategoryDTO(ItemCategory itemCategory) {
        ItemCategoryDTO result = new ItemCategoryDTO();
        result.setId(itemCategory.getId());
        result.setName(itemCategory.getName());
        return result;
    }

    public ItemCategory itemCategoryPOJO(ItemCategoryDTO itemCategoryDTO) {
        ItemCategory result = new ItemCategory();
        result.setId(itemCategoryDTO.getId());
        result.setName(itemCategoryDTO.getName());
        return result;
    }

    public ProviderDTO providerDTO(Provider provider) {
        ProviderDTO result = new ProviderDTO();
        result.setId(provider.getId());
        result.setName(provider.getName());
        return result;
    }

    public Provider providerPOJO(ProviderDTO providerDTO) {
        Provider result = new Provider();
        result.setId(providerDTO.getId());
        result.setName(providerDTO.getName());
        return result;
    }

    public ProducerDTO producerDTO(Producer producer) {
        ProducerDTO result = new ProducerDTO();
        result.setId(producer.getId());
        result.setName(producer.getName());
        return result;
    }

    public Producer producerPOJO(ProducerDTO producerDTO) {
        Producer result = new Producer();
        result.setId(producerDTO.getId());
        result.setName(producerDTO.getName());

        return result;
    }

    public ItemDTO itemDTO(Item item) {
        ItemDTO result = new ItemDTO();
        result.setId(item.getId());
        result.setName(item.getName());
        result.setSerialNumber(item.getSerialNumber());
        result.setUrl(item.getUrl());
        result.setDescription(item.getDescription());
        result.setProducer(producerDTO(item.getProducer()));
        result.setProvider(providerDTO(item.getProvider()));
        result.setItemCategory(itemCategoryDTO(item.getItemCategory()));

        return result;
    }

    public Item itemPOJO(ItemDTO itemDTO) {
        Item result = new Item();
        result.setId(itemDTO.getId());
        result.setName(itemDTO.getName());
        result.setSerialNumber(itemDTO.getSerialNumber());
        result.setUrl(itemDTO.getUrl());
        result.setDescription(itemDTO.getDescription());
        result.setProducer(producerPOJO(itemDTO.getProducer()));
        result.setProvider(providerPOJO(itemDTO.getProvider()));
        result.setItemCategory(itemCategoryPOJO(itemDTO.getItemCategory()));
        return result;
    }

    public AppUser appUserPOJO(AppUserDTO appUserDTO) {
        AppUser result = new AppUser();
        result.setId(appUserDTO.getId());
        result.setUsername(appUserDTO.getUsername());
        result.setPassword(appUserDTO.getPassword());
        result.setEmail(appUserDTO.getEmail());
        result.setRole(appUserDTO.getRole());
        return result;
    }

    public AppUserDTO appUserDTO(AppUser appUser) {
        AppUserDTO result = new AppUserDTO();
        result.setId(appUser.getId());
        result.setUsername(appUser.getUsername());
        result.setPassword(appUser.getPassword());
        result.setEmail(appUser.getEmail());
        result.setRole(appUser.getRole());
        return result;
    }

    public ItemInOrder itemInOrderPOJO(ItemInOrderDTO itemInOrderDTO) {
        ItemInOrder result = new ItemInOrder();
        result.setId(itemInOrderDTO.getId());
        result.setName(itemInOrderDTO.getName());
        result.setSerialNumber(itemInOrderDTO.getSerialNumber());
        result.setUrl(itemInOrderDTO.getUrl());
        result.setDescription(itemInOrderDTO.getDescription());
        result.setProducer(producerPOJO(itemInOrderDTO.getProducer()));
        result.setProvider(providerPOJO(itemInOrderDTO.getProvider()));
        result.setItemCategory(itemCategoryPOJO(itemInOrderDTO.getItemCategory()));
        result.setOrderDate(itemInOrderDTO.getOrderDate());
        result.setDeliverDate(itemInOrderDTO.getDeliverDate());
        result.setDelivered(itemInOrderDTO.isDelivered());
        result.setOrdered(itemInOrderDTO.isDelivered());
        result.setAmount(itemInOrderDTO.getAmount());
        return result;
    }

    public ItemInOrderDTO itemInOrderDTO(ItemInOrder itemInOrder) {
        ItemInOrderDTO result = new ItemInOrderDTO();
        result.setId(itemInOrder.getId());
        result.setName(itemInOrder.getName());
        result.setSerialNumber(itemInOrder.getSerialNumber());
        result.setUrl(itemInOrder.getUrl());
        result.setDescription(itemInOrder.getDescription());
        result.setProducer(producerDTO(itemInOrder.getProducer()));
        result.setProvider(providerDTO(itemInOrder.getProvider()));
        result.setItemCategory(itemCategoryDTO(itemInOrder.getItemCategory()));
        result.setOrderDate(itemInOrder.getOrderDate());
        result.setDeliverDate(itemInOrder.getDeliverDate());
        result.setDelivered(itemInOrder.isDelivered());
        result.setOrdered(itemInOrder.isOrdered());
        result.setAmount(itemInOrder.getAmount());
        return result;
    }

    public OrderDTO orderDTO(Order order) {
        OrderDTO result = new OrderDTO();
        result.setId(order.getId());
        result.setName(order.getName());
        result.setAppUser(order.getAppUser());
        result.setCreationDate(order.getCreationDate());
        result.setClosedDate(order.getClosedDate());
        result.setOrderStatus(order.getOrderStatus());
        result.setCommentary(order.getCommentary());
        for (ItemInOrder item : order.getItemsInOrder()) {
            result.getItemsInOrder().add(itemInOrderDTO(item));
        }
        return result;
    }

    public Order orderPOJO(OrderDTO orderDTO) {
        Order result = new Order();
        result.setId(orderDTO.getId());
        result.setName(orderDTO.getName());
        result.setAppUser(orderDTO.getAppUser());
        result.setCreationDate(orderDTO.getCreationDate());
        result.setClosedDate(orderDTO.getClosedDate());
        result.setOrderStatus(orderDTO.getOrderStatus());
        result.setCommentary(orderDTO.getCommentary());
        for (ItemInOrderDTO item : orderDTO.getItemsInOrder()) {
            result.getItemsInOrder().add(itemInOrderPOJO(item));
        }
        return result;
    }
}
