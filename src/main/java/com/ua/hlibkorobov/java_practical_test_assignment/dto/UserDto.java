package com.ua.hlibkorobov.java_practical_test_assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {

    @JsonProperty(value = "name")
    private String email;

    @JsonProperty(value = "firstName")
    private String firstName;

    @JsonProperty(value = "lastName")
    private String lastName;

    @JsonProperty(value = "birthday")
    private LocalDate birthDate;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "number")
    private String phoneNumber;
}
