package com.company.ordersbackend.service;

import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @Autowired
    DTOMapper dtoMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    AppUserService appUserService;

    @BeforeEach
    public void init() {
        this.appUserService = new AppUserService(appUserRepository, dtoMapper, passwordEncoder);
    }

    @Test
    void authorizationTest() {
        //given
        long id = 1L;
        AppUserDTO appUserDTO = new AppUserDTO(id, "xxx", "yyyyyyyyyy", "USER", "any@gmial.com");

        //when
        when(appUserRepository.findByUsername(appUserDTO.getUsername())).thenReturn(Optional.of(dtoMapper.appUserPOJO(appUserDTO)));
        Optional<AppUserDTO> result = appUserService.authorization(appUserDTO);

        //then
        assertTrue(result.isPresent());
        assertEquals(appUserDTO.getUsername(), result.get().getUsername());
        assertEquals(appUserDTO.getId(), result.get().getId());
        assertEquals("", result.get().getPassword());
        assertEquals(appUserDTO.getRole(), result.get().getRole());
    }

    @Test
    void authorizationTest_UserNotExists(){
        //given
        long id = 1L;
        AppUserDTO appUserDTO = new AppUserDTO(id, "xxx", "yyyyyyyyyy", "USER", "any@gmial.com");

        //when
        when(appUserRepository.findByUsername(appUserDTO.getUsername())).thenReturn(Optional.empty());
        Optional<AppUserDTO> result = appUserService.authorization(appUserDTO);

        //then
        assertTrue(result.isEmpty());

    }
}