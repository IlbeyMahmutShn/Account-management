package com.mahmutsahin.service;

import com.mahmutsahin.dto.DtoTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {

    public DtoTransaction saveTransaction(DtoTransaction transaction);

    public List<DtoTransaction> getAllTransactions();

    public DtoTransaction getTransactionById(Integer id);

    public DtoTransaction deleteTransactionById(Integer id);

    public DtoTransaction updateTransaction(Integer id , DtoTransaction dtoTransaction);

    // Toplam bakiye: user.balance + tüm transaction amount toplamı
    public BigDecimal getTotalBalance();
}
