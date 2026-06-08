package com.hands_on.zomato.Entity;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
@AllArgsConstructor
public class UserEntity {

    @Id
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private UUID id = Generators.timeBasedEpochGenerator().generate();
    private String firstName;
    private String lastName;
    private Long phoneNo;
    private String address;
    private Long cityPin;
    @Column(unique = true)
    private String emailAddress;

    protected UserEntity(){

    }
}