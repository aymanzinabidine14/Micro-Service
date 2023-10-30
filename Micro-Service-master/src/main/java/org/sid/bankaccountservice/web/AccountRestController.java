package org.sid.bankaccountservice.web;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")

public class AccountRestController {

    private BankAccountRepository bankAccountRepository;

    @Autowired
    private AccountServiceImpl accountService;

    public AccountRestController(BankAccountRepository bankAccountRepository){
        this.bankAccountRepository=bankAccountRepository;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccount> bankAccounts(){
        return bankAccountRepository.findAll();
    }

    @GetMapping("/bankAccount/{id}")
    public BankAccount bankAccount(@PathVariable String id){
        return bankAccountRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Account %s not found",id)));
    }


    @PostMapping("/save")
    public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO bankAccountRequestDTO){
        return accountService.addAccount(bankAccountRequestDTO);
    }

    @PutMapping("/update/{id}")
    public BankAccount update(@PathVariable String id,@RequestBody BankAccount bankAccount){
        BankAccount account=bankAccountRepository.findById(id).orElseThrow();

        if (bankAccount.getBalance()!= null) account.setBalance(bankAccount.getBalance());
        if (bankAccount.getCurrency()!= null) account.setCurrency(bankAccount.getCurrency());
        if (bankAccount.getType()!= null) account.setType(bankAccount.getType());
        if (bankAccount.getCreatedAt()!= null)  account.setCreatedAt(new Date());
        return bankAccountRepository.save(account);

    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id){
        bankAccountRepository.deleteById(id);
    }

}