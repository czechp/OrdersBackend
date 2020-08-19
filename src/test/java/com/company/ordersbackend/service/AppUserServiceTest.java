package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.VerificationToken;
import com.company.ordersbackend.exception.AccessDeniedException;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        assertThat(result, instanceOf(AppUserDTO.class));
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
        assertThrows(AccessDeniedException.class, () -> appUserService.changeRole(id, role, principal));
    }

    @Test()
    public void changeRoleTest_roleNotExists() {
        //given
        long id = 1L;
        String role = "user123";
        //when
        when(principal.getName()).thenReturn("admin");
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(true);
        //then
        assertThrows(NotFoundException.class, () -> appUserService.changeRole(id, role, principal));
    }

    @Test()
    public void changeRoleTest_appUserNotExists() {
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

    @Test()
    public void deleteTest() {
        //given
        long id = 1L;
        //when
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(true);
        when(appUserRepository.existsById(anyLong())).thenReturn(true);
        when(principal.getName()).thenReturn("admin");
        appUserService.delete(id, principal);
        //then
        verify(appUserRepository, times(1)).deleteById(id);
    }

    @Test()
    public void deleteTest_isNotAdmin() {
        //given
        long id = 1L;
        //when
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(false);
        //then
        assertThrows(AccessDeniedException.class, () -> appUserService.delete(id, principal));
    }

    @Test()
    public void deleteTest_userNotExists() {
        //given
        long id = 1L;
        //when
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(true);
        when(principal.getName()).thenReturn("ADMIN");
        when(appUserRepository.existsById(anyLong())).thenReturn(false);
        //then
        assertThrows(NotFoundException.class, () -> appUserService.delete(id, principal));
    }

    @Test()
    public void findALl() {
        //given
        //when
        when(principal.getName()).thenReturn("admin");
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(true);
        when(appUserRepository.findAll()).thenReturn(
                Arrays.asList(
                        new AppUser(),
                        new AppUser()
                )
        );
        List<AppUserDTO> result = appUserService.findAll(principal);
        //then
        assertThat(result, hasSize(2));
        assertThat(result.get(0), instanceOf(AppUserDTO.class));
    }

    @Test()
    public void findAll_isNotAdmin() {
        //given
        //when
        when(principal.getName()).thenReturn("user");
        when(appUserRepository.existsByUsernameAndRole(anyString(), anyString())).thenReturn(false);
        //then
        assertThrows(AccessDeniedException.class, () -> appUserService.findAll(principal));
    }
}