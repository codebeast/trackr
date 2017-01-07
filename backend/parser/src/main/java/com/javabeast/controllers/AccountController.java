package com.javabeast.controllers;

import com.javabeast.account.Account;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {


    @PostMapping
    public Account createAccount(final Account account) {




        //todo create an account
        return new Account();

    }


}
