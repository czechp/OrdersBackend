package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.VerificationToken;
import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.repository.AppUserRepository;
import com.company.ordersbackend.repository.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    VerificationTokenRepository verificationTokenRepository;

    @Mock
    Errors  errors;

    @Autowired
    DTOMapper dtoMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    AppUserService appUserService;

    @BeforeEach
    public void init() {
        this.appUserService = new AppUserService(appUserRepository, dtoMapper, passwordEncoder, verificationTokenRepository);
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

    @Test()
    public void saveAppUser_Test(){
        //given
        AppUserDTO appUserDTO = new AppUserDTO(1L, "user", "user", "USER", "1234@gmail.com");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(appUserRepository.existsByUsername(any())).thenReturn(false);
        when(appUserRepository.existsByEmail(any())).thenReturn(false);
        when(verificationTokenRepository.save(any())).thenReturn(new VerificationToken(dtoMapper.appUserPOJO(appUserDTO)));
        boolean result = appUserService.saveAppUser(appUserDTO, errors);
        //then
        assertTrue(result);
    }

    @Test()
    public void saveAppUser_hasError(){
        //given
        AppUserDTO appUserDTO = new AppUserDTO(1L, "user", "user", "USER", "1234@gmail.com");
        //when
        when(errors.hasErrors()).thenReturn(true);
        //then
        boolean result = appUserService.saveAppUser(appUserDTO, errors);
        assertFalse(result);
    }

    @Test()
    public void saveAppUser_usernameExists(){
        //given
        AppUserDTO appUserDTO = new AppUserDTO(1L, "user", "user", "USER", "1234@gmail.com");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(appUserRepository.existsByUsername(any())).thenReturn(true);
        when(appUserRepository.existsByEmail(any())).thenReturn(false);
        //then
        boolean result = appUserService.saveAppUser(appUserDTO, errors);
        assertFalse(result);
    }

    @Test()
    public void saveAppUser_emailExist(){
        //given
        AppUserDTO appUserDTO = new AppUserDTO(1L, "user", "user", "USER", "1234@gmail.com");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(appUserRepository.existsByUsername(any())).thenReturn(false);
        when(appUserRepository.existsByEmail(any())).thenReturn(true);
        //then
        boolean result = appUserService.saveAppUser(appUserDTO, errors);
        assertFalse(result);
    }
}