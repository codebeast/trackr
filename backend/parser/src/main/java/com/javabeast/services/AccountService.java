package com.javabeast.services;

import com.javabeast.account.Account;
import com.javabeast.account.dto.CreateAccount;
import com.javabeast.account.User;
import com.javabeast.repo.AccountRepo;
import com.javabeast.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AccountService {

    private final AccountRepo accountRepo;

    private final UserRepo userRepo;

    @Autowired
    public AccountService(final AccountRepo accountRepo, final UserRepo userRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    public Account createAccount(final CreateAccount createAccount) {
        final boolean validData = isValidData(createAccount);
        if (validData) {
            return null;
        }

        final Account account = createAccount.getAccount();
        final boolean doesAccountExist = doesAccountExist(account);
        if (doesAccountExist) {
            return null;
        }

        final User user = createAccount.getUser();
        final boolean doesUserExist = doesUserExist(user);
        if (doesUserExist) {
            return null;
        }

        final Account savedAccount = accountRepo.save(account);
        user.setAccount(savedAccount);
        userRepo.save(user);
        return savedAccount;
    }


    private boolean doesUserExist(final User user) {
        final User findByEmail = userRepo.findByEmail(user.getEmail());
        return findByEmail != null;
    }

    private boolean doesAccountExist(final Account account) {
        final Account findByName = accountRepo.findByName(account.getName());
        return findByName != null;
    }

    private boolean isValidData(CreateAccount createAccount) {
        if (createAccount == null || createAccount.getAccount() == null || createAccount.getUser() == null) {
            return true;
        }
        final Account account = createAccount.getAccount();
        if (StringUtils.isEmpty(account.getName())) {
            return true;
        }

        final User user = createAccount.getUser();
        return StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getPasswordHash());
    }
}
