package com.wallet.controller;

import com.wallet.model.Account;
import com.wallet.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/account")
    public Account addAccount(@RequestBody Account account) {
        return accountService.insertAccount(account);
    }

    @GetMapping("/account")
    public List<Account> getAllAccount(){
        return accountService.getAll();
    }

    @GetMapping("/account/{id}")
    public Account findAccountById(@PathVariable Long id){
        return accountService.getById(id);
    }

    @DeleteMapping("/account")
    public void deleteAccount(@RequestBody Account toDelete){
        accountService.deleteById(toDelete);
    }

}
