package com.javabeast.controllers;

import com.javabeast.account.Account;
import com.javabeast.account.CreateAccount;
import com.javabeast.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;


    @Autowired
    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestBody final CreateAccount createAccount) {
        return accountService.createAccount(createAccount);
    }


}
