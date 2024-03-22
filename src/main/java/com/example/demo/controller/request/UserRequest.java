package com.example.demo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name;

    private String surname;

    private LocalDate birthDate;

    private String login;

    private String password;

    private List<Long> roleIds;

}
