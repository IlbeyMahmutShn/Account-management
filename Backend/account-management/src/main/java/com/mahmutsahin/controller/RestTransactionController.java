package com.mahmutsahin.controller;

import com.mahmutsahin.dto.DtoTransaction;
import com.mahmutsahin.model.RootEntity;

import java.util.List;

public interface RestTransactionController {

    public DtoTransaction saveTransaction(DtoTransaction transaction);

    public RootEntity<List<DtoTransaction>> getAllTransactions();

    public RootEntity<DtoTransaction> getTransactionById(Integer id);

    public RootEntity<String> deleteTransactionById(Integer id);

    public RootEntity<Object> updateTransaction(Integer id , DtoTransaction transaction);
}
