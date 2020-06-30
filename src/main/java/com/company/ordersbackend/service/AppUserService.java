package com.company.ordersbackend.service;

import com.company.ordersbackend.domain.AppUser;
import com.company.ordersbackend.domain.VerificationToken;
import com.company.ordersbackend.model.AppUserDTO;
import com.company.ordersbackend.repository.AppUserRepository;
import com.company.ordersbackend.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;

@Service
public class AppUserService {
    private AppUserRepository appUserRepository;
    private DTOMapper dtoMapper;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired()
    public AppUserService(AppUserRepository appUserRepository, DTOMapper dtoMapper, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository) {
        this.appUserRepository = appUserRepository;
        this.dtoMapper = dtoMapper;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
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
            if (!appUserRepository.existsByUsername(appUserDTO.getUsername()) && !appUserRepository.existsByEmail(appUserDTO.getEmail())) {
                AppUser appUser = dtoMapper.appUserPOJO(appUserDTO);
                appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
                //remove after tests
                VerificationToken result = verificationTokenRepository.save(new VerificationToken(appUserRepository.save(appUser)));
                System.out.println(result.getToken());
                return true;
            }
        }
        return false;
    }

    public boolean activateAppUser(String token){
        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByToken(token);
        if(optionalVerificationToken.isPresent()){
            VerificationToken verificationToken = optionalVerificationToken.get();
            Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(verificationToken.getAppUser().getUsername());
            if(optionalAppUser.isPresent()){
                AppUser appUser = optionalAppUser.get();
                appUser.setActive(true);
                appUserRepository.save(appUser);
                return true;
            }
        }
        return false;
    }

}
