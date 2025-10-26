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
public class DtoTransaction {

    private Integer CariId;

    private String cariName;

    private String tckn;

    private String taxNo;

    private String email;

    private String phone;

    private String address;

    private BigDecimal amount;

    private DtoUser user;
 }