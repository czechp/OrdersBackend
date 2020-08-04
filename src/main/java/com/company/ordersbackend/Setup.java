package com.company.ordersbackend;

import com.company.ordersbackend.domain.*;
import com.company.ordersbackend.repository.*;
import com.company.ordersbackend.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("development")
@Slf4j
public class Setup {

    private ItemCategoryRepository itemCategoryRepository;
    private ProviderRepository providerRepository;
    private ProducerRepository producerRepository;
    private ItemRepository itemRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    private ItemInOrderRepository itemInOrderRepository;
    private OrderService orderService;
    private OrderRepository orderRepository;

    public Setup(ItemCategoryRepository itemCategoryRepository, ProviderRepository providerRepository, ProducerRepository producerRepository, ItemRepository itemRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, ItemInOrderRepository itemInOrderRepository, OrderService orderService, OrderRepository orderRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.providerRepository = providerRepository;
        this.producerRepository = producerRepository;
        this.itemRepository = itemRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemInOrderRepository = itemInOrderRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("Development mode active");

        itemCategoryRepository.save(new ItemCategory("PLC"));
        itemCategoryRepository.save(new ItemCategory("HMI"));
        itemCategoryRepository.save(new ItemCategory("Osprzet"));


        providerRepository.save(new Provider("ElektroADA"));
        providerRepository.save(new Provider("DolinaSerwis"));
        providerRepository.save(new Provider("Internet"));

        producerRepository.save(new Producer("IFM"));
        producerRepository.save(new Producer("Siemens"));
        producerRepository.save(new Producer("Adidas"));
        Producer producer = producerRepository.findById(1L).get();

        Provider provider = providerRepository.findById(1L).get();
        ItemCategory itemCategory = itemCategoryRepository.findById(1L).get();

        Item item1 = new Item("Stycznik", "123", "32a", "https://www.google.com/", producer, provider, itemCategory);
        Item item2 = new Item("Zabezpieczenie termiczne", "123", "32A", "http://123.com", producer, provider, itemCategory);
        Item item3 = new Item("Bezpiecznik", "123", "32A", "http://123.com", producer, provider, itemCategory);

        itemRepository.saveAll(Arrays.asList(item1, item2, item3));
        AppUser user = new AppUser("user", passwordEncoder.encode("user"), "superUser", "webcodsdaerc@gmail.com");
        user.setActive(true);
        user = appUserRepository.save(user);


        AppUser user1= new AppUser("user1", passwordEncoder.encode("user1"), "user", "webcodsdaerc@gmail.com");
        user1.setActive(true);
        user1 = appUserRepository.save(user1);

        ItemInOrder itemInOrder = new ItemInOrder(item1);
        itemInOrderRepository.save(itemInOrder);

        Order order = new Order();
        order.setName("Testing name");
        order.setAppUser(appUserRepository.findById(1L).get());
        order.setOrderStatus(OrderStatus.NEW);
        orderRepository.save(order);

        Order orderFinished = new Order();
        orderFinished.setName("Finished order");
        orderFinished.setAppUser(appUserRepository.findById(1L).get());
        orderFinished.setOrderStatus(OrderStatus.FINISHED);
        orderRepository.save(orderFinished);

        System.out.println(appUserRepository.existsByUsernameAndRole("user1", AppUserRole.SUPERUSER.toString()));


    }
}
