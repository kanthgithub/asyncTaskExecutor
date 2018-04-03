package com.asyncTaskExecutor.service;

import com.asyncTaskExecutor.exception.InValidAccountException;
import com.asyncTaskExecutor.model.AccountBalanceRequestModel;
import com.asyncTaskExecutor.model.AccountBalanceResposeModel;
import com.asyncTaskExecutor.repository.AccountStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountBalanceQueryServiceImpl implements IAccountBalanceQueryService {

    @Autowired
    public AccountBalanceQueryServiceImpl(AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    private AccountStore accountStore;

    public AccountBalanceResposeModel queryAccount(AccountBalanceRequestModel accountBalanceRequestModel){

        return execute(accountBalanceRequestModel);


    }

    public AccountBalanceResposeModel execute(AccountBalanceRequestModel accountBalanceRequestModel) {

        BigDecimal accountBalance = null;

        Boolean isSuccessful = Boolean.FALSE;

        String messageDescription = null;

        try {
            accountBalance = accountStore.queryBalance(accountBalanceRequestModel.getAccount());
            isSuccessful = Boolean.TRUE;
            messageDescription =
                    String.format("Account: %d has Balance: %s"
                            , accountBalanceRequestModel.getAccount(), String.valueOf(accountBalance));
        } catch (InValidAccountException excep) {
            messageDescription = excep.getDescription();
        }

        return new AccountBalanceResposeModel().setAccount(accountBalanceRequestModel.getAccount()).setBalance(accountBalance)
                .setSuccessful(isSuccessful).setMessageDescription(messageDescription);
    }

}
