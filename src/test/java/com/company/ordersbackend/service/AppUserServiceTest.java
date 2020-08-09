package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.AppUserRole;
import com.company.ordersbackend.domain.VerificationToken;
import com.company.ordersbackend.exception.AccesDeniedException;
import com.company.ordersbackend.exception.NotFoundException;
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

import javax.servlet.ServletRequest;
import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class AppUserServiceTest {

    @Mock
    Principal principal;

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    VerificationTokenRepository verificationTokenRepository;

    @Mock
    Errors errors;

    @Mock
    EmailSenderService emailSenderService;

    @Mock
    ServletRequest servletRequest;

    @Autowired
    DTOMapper dtoMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    AppUserService appUserService;

    @BeforeEach
    public void init() {
        this.appUserService = new AppUserService(appUserRepository, dtoMapper, passwordEncoder, verificationTokenRepository, emailSenderService);
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
    void authorizationTest_UserNotExists() {
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
    public void saveAppUser_Test() {
        //given
        AppUserDTO appUserDTO = new AppUserDTO(1L, "user", "user", "USER", "1234@gmail.com");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(appUserRepository.existsByUsername(any())).thenReturn(false);
        when(appUserRepository.existsByEmail(any())).thenReturn(false);
        when(verificationTokenRepository.save(any())).thenReturn(new VerificationToken(dtoMapper.appUserPOJO(appUserDTO)));
//        boolean result = appUserService.saveAppUser(appUserDTO, errors);
        //then
//        assertTrue(result);
    }

    @Test()
    public void saveAppUser_hasError() {
        //given
        AppUserDTO appUserDTO = new AppUserDTO(1L, "user", "user", "USER", "1234@gmail.com");
        //when
        when(errors.hasErrors()).thenReturn(true);
        boolean result = appUserService.saveAppUser(appUserDTO, errors, servletRequest);

        //then
//        assertFalse(result);
    }

    @Test()
    public void saveAppUser_usernameExists() {
        //given
        AppUserDTO appUserDTO = new AppUserDTO(1L, "user", "user", "USER", "1234@gmail.com");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(appUserRepository.existsByUsername(any())).thenReturn(true);
        when(appUserRepository.existsByEmail(any())).thenReturn(false);
        //then
        boolean result = appUserService.saveAppUser(appUserDTO, errors, servletRequest);
        assertFalse(result);
    }

    @Test()
    public void saveAppUser_emailExist() {
        //given
        AppUserDTO appUserDTO = new AppUserDTO(1L, "user", "user", "USER", "1234@gmail.com");
        //when
        when(errors.hasErrors()).thenReturn(false);
        when(appUserRepository.existsByUsername(any())).thenReturn(false);
        when(appUserRepository.existsByEmail(any())).thenReturn(true);
        //then
        boolean result = appUserService.saveAppUser(appUserDTO, errors, servletRequest);
        assertFalse(result);
    }

    @Test()
    public void changeRoleTest() {
        //given
        long id = 1L;
        String role = "ADMIN";
        AppUser appUser = new AppUser("admin", "admin", role, "anyEmail@gmail.com");
        appUser.setId(id);
        //when
        when(principal.getName()).thenReturn("admin");
        when(appUserRepository.findById(anyLong())).thenReturn(
                Optional.of(appUser));
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(true);
        when(appUserRepository.save(appUser)).thenReturn(appUser);
        AppUserDTO result = appUserService.changeRole(id, role, principal);

        //then
        assertThat(result).isNotNull();
    }

    @Test()
    public void changeRoleTest_isNotAdmin() {
        //given
        long id = 1L;
        String role = "ADMIN";
        //when
        when(principal.getName()).thenReturn("user");
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(false);
        //then
        assertThrows(AccesDeniedException.class, () -> appUserService.changeRole(id, role, principal));
    }

    @Test()
    public void changeRoleTest_roleNotExists(){
        //given
        long id = 1L;
        String role = "user123";
        //when
        when(principal.getName()).thenReturn("admin");
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(true);
        //then
        assertThrows(NotFoundException.class, ()->appUserService.changeRole(id, role, principal));
    }

    @Test()
    public void changeRoleTest_appUserNotExists(){
        //given
        long id = 1L;
        String role = "ADMIN";
        //when
        when(principal.getName()).thenReturn("admin");
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(true);
        when(appUserRepository.findById(anyLong())).thenReturn(Optional.empty());
        //then
        assertThrows(NotFoundException.class, () -> appUserService.changeRole(id, role, principal));
    }

}