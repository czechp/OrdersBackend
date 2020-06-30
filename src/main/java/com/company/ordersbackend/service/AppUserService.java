package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;

@Service
public class AppUserService {
    private AppUserRepository appUserRepository;
    private DTOMapper dtoMapper;
    private PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, DTOMapper dtoMapper, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.dtoMapper = dtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AppUserDTO> authorization(AppUserDTO appUserDTO) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(appUserDTO.getUsername());
        if (optionalAppUser.isPresent()) {
            AppUserDTO result = dtoMapper.appUserDTO(optionalAppUser.get());
            result.setPassword("");
            return Optional.of(result);
        }
        return Optional.empty();
    }

    public boolean saveAppUser(AppUserDTO appUserDTO, Errors errors) {
        if (!errors.hasErrors()) {
            if (!appUserRepository.existsByUsername(appUserDTO.getUsername())) {
                if (!appUserRepository.existsByEmail(appUserDTO.getEmail())) {
                    AppUser appUser = dtoMapper.appUserPOJO(appUserDTO);
                    appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
                    appUserRepository.save(appUser);
                    return true;
                }
            }
        }
        return false;
    }


}
