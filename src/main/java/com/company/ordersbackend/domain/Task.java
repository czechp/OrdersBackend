package com.company.ordersbackend.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "tasks")
@Data()
@NoArgsConstructor()
public class Task {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    //setting by hibernate interceptor
    private LocalDateTime creatingDate;

    @ManyToOne()
    @NotNull()
    private AppUser appUser;


}
