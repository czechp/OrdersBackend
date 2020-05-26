package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            System.out.println(optionalAppUser.get());
            System.out.println(passwordEncoder.encode(appUserDTO.getPassword()));
            if (optionalAppUser.get().getPassword().equals(passwordEncoder.encode(appUserDTO.getPassword()))) {
                AppUserDTO result = dtoMapper.appUserDTO(optionalAppUser.get());
                result.setPassword("");
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }
}
