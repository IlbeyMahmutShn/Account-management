package com.mahmutsahin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private BigDecimal balance;
}