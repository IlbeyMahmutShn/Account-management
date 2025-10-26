package com.mahmutsahin.service.impl;

import com.mahmutsahin.dto.DtoTransaction;
import com.mahmutsahin.dto.DtoUser;
import com.mahmutsahin.exception.BaseException;
import com.mahmutsahin.exception.ErrorMessage;
import com.mahmutsahin.exception.MessageType;
import com.mahmutsahin.model.Transaction;
import com.mahmutsahin.model.User;
import com.mahmutsahin.repository.TransactionRepository;
import com.mahmutsahin.service.ITransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class
TransactionServiceImpl implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    @Transactional
    public DtoTransaction saveTransaction(DtoTransaction dtoTransaction) {
        DtoTransaction response = new DtoTransaction();
        Transaction transaction = new Transaction();

        // DTO -> Entity (user hariç)
        BeanUtils.copyProperties(dtoTransaction, transaction, "user", "CariId");

        // User set et
        if (dtoTransaction.getUser() != null && dtoTransaction.getUser().getId() != null) {
            User user = new User();
            user.setId(dtoTransaction.getUser().getId());
            transaction.setUser(user);
        }

        Transaction dbTransaction = transactionRepository.save(transaction);

        // Entity -> DTO (user hariç)
        BeanUtils.copyProperties(dbTransaction, response, "user");

        if (dbTransaction.getUser() != null) {
            User u = dbTransaction.getUser();
            response.setUser(new DtoUser(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getBalance()));
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DtoTransaction> getAllTransactions() {
        List<DtoTransaction> dtoTransactionList = new ArrayList<>();

        List<Transaction> transactionList = transactionRepository.findAll();
        if (transactionList != null && !transactionList.isEmpty()) {
            for (Transaction transaction : transactionList) {
                DtoTransaction dto = new DtoTransaction();
                BeanUtils.copyProperties(transaction, dto);

                if (transaction.getUser() != null) {
                    User u = transaction.getUser();
                    dto.setUser(new DtoUser(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getBalance()));
                }
                dtoTransactionList.add(dto);
            }
        }
        return dtoTransactionList;
    }

    @Override
    @Transactional(readOnly = true)
    public DtoTransaction getTransactionById(Integer id) {
        DtoTransaction dto = new DtoTransaction();
        DtoUser dtoUser = new DtoUser();

        Optional<Transaction> optional = transactionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }
        Transaction transaction = optional.get();
        User user = transaction.getUser();
        BeanUtils.copyProperties(transaction, dto);
        if (user != null) {
            BeanUtils.copyProperties(user, dtoUser);
            dto.setUser(dtoUser);
        }

        return dto;
    }

    @Override
    @Transactional
    public DtoTransaction deleteTransactionById(Integer id) {
        Optional<Transaction> optional = transactionRepository.findById(id);
        if (optional.isPresent()) {
            transactionRepository.deleteById(id);
        }
        return null;
    }

    @Override
    @Transactional
    public DtoTransaction updateTransaction(Integer id, DtoTransaction dtoTransaction) {
        DtoTransaction dto = new DtoTransaction();

        Optional<Transaction> optional = transactionRepository.findById(id);
        if (optional.isPresent()) {
            Transaction dbTransaction = optional.get();

            // DTO -> Entity (id ve user hariç)
            BeanUtils.copyProperties(dtoTransaction, dbTransaction, "CariId", "user");

            Transaction updatedTransaction = transactionRepository.save(dbTransaction);

            // Entity -> DTO (user hariç)
            BeanUtils.copyProperties(updatedTransaction, dto, "user");
            if (updatedTransaction.getUser() != null) {
                User u = updatedTransaction.getUser();
                dto.setUser(new DtoUser(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getBalance()));
            }

            return dto;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public java.math.BigDecimal getTotalBalance() {
        java.math.BigDecimal sum = java.math.BigDecimal.ZERO;
        List<Transaction> list = transactionRepository.findAll();
        if (list != null) {
            for (Transaction t : list) {
                if (t.getAmount() != null) {
                    sum = sum.add(t.getAmount());
                }
            }
            if (!list.isEmpty() && list.get(0).getUser() != null && list.get(0).getUser().getBalance() != null) {
                sum = sum.add(list.get(0).getUser().getBalance());
            }
        }
        return sum;
    }
}