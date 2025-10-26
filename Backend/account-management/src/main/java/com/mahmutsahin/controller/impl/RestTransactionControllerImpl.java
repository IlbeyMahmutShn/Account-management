package com.mahmutsahin.controller.impl;

import com.mahmutsahin.controller.RestTransactionController;
import com.mahmutsahin.dto.DtoTransaction;
import com.mahmutsahin.model.RootEntity;
import com.mahmutsahin.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/rest/api/transaction")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001", "http://localhost:3002"})
public class RestTransactionControllerImpl extends RestBaseController implements RestTransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping(path = "/save")
    @Override
    public DtoTransaction saveTransaction(@RequestBody DtoTransaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @GetMapping(path = "/list")
    @Override
    public RootEntity<List<DtoTransaction>> getAllTransactions() {
        try {
            List<DtoTransaction> dtoList = transactionService.getAllTransactions();

            if (dtoList == null || dtoList.isEmpty()) {
                return RootEntity.error("Hiç işlem bulunamadı.");
            }
            return RootEntity.ok(dtoList);
        } catch (Exception e) {
            return RootEntity.error("Beklenmeyen hata oluştu: " + e.getMessage());
        }
    }

    @GetMapping(path = "/balance")
    public RootEntity<BigDecimal> getTotalBalance() {
        try {
            BigDecimal total = transactionService.getTotalBalance();
            return RootEntity.ok(total);
        } catch (Exception e) {
            return RootEntity.error("Bakiye hesaplanamadı: " + e.getMessage());
        }
    }


    @GetMapping(path = "/list/{id}")
    @Override
    public RootEntity<DtoTransaction> getTransactionById(@PathVariable(value = "id") Integer id) {
        try {
            DtoTransaction dto = transactionService.getTransactionById(id);
            if (dto == null) {
                return error(String.valueOf(id));
            }
            return ok(dto);
        }
        catch(Exception e) {
            return error(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public RootEntity<String> deleteTransactionById(@PathVariable(value = "id") Integer id) {
        try {
            DtoTransaction dto = transactionService.getTransactionById(id);

            if (dto == null) {
                return RootEntity.error("Silinecek işlem bulunamadı. ID: " + id);
            }
            transactionService.deleteTransactionById(id);
            return ok("İşlem başarılı.");

        } catch (Exception e) {
            return RootEntity.error("Silme sırasında hata oluştu: " + e.getMessage());
        }
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public RootEntity<Object> updateTransaction(@PathVariable(value = "id") Integer id,@RequestBody DtoTransaction dtoTransaction) {
        try {
            DtoTransaction dto = transactionService.getTransactionById(id);

            if (dto == null) {
                return RootEntity.error("Güncellenecek işlem bulunamadı " + id);
            }
            transactionService.updateTransaction(id, dtoTransaction);
            return ok("İşlem başarılı ");

        } catch (Exception e) {
            return RootEntity.error("Güncellenme sırasında hata oluştu " + e.getMessage());
        }
    }
}