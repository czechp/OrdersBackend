package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.ItemBorrowed;
import com.company.ordersbackend.exception.BadInputDataException;
import com.company.ordersbackend.exception.NotFoundException;
import com.company.ordersbackend.repository.ItemBorrowedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest()
@RunWith(SpringRunner.class)
class ItemBorrowedServiceTest {

    private ItemBorrowedService itemBorrowedService;


    @MockBean()
    private ItemBorrowedRepository itemBorrowedRepository;

    @MockBean()
    private AppUserService appUserService;

    @MockBean()
    private Errors errors;

    @BeforeEach()
    public void init(){
    }


}