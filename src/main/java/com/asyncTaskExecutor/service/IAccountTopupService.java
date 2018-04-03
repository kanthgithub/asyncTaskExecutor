package com.asyncTaskExecutor.service;

import com.asyncTaskExecutor.model.AccountTopupRequestModel;
import com.asyncTaskExecutor.model.AccountTopupResposeModel;

public interface IAccountTopupService {

    public AccountTopupResposeModel topupAccount(AccountTopupRequestModel accountTopupRequestModel);

    }
