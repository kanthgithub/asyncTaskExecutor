package com.asyncTaskExecutor.service;

import com.asyncTaskExecutor.model.AccountBalanceRequestModel;
import com.asyncTaskExecutor.model.AccountBalanceResposeModel;


public interface IAccountBalanceQueryService {

    public AccountBalanceResposeModel queryAccount(AccountBalanceRequestModel AccountBalanceRequestModel);
}
