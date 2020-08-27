package com.company.ordersbackend.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity()
@Data()
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    @OneToOne()
    private AppUser appUser;

    public VerificationToken() {
        this.token = UUID.randomUUID().toString();
    }

    public VerificationToken(AppUser appUser) {
        this.token = UUID.randomUUID().toString();
        this.appUser = appUser;
    }
}

