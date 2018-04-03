package com.asyncTaskExecutor.service;

import com.asyncTaskExecutor.model.AccountMaintenanceRequestModel;
import com.asyncTaskExecutor.model.AccountMaintenanceResposeModel;

public interface IAccountMaintenanceService {

    public AccountMaintenanceResposeModel addAccount(AccountMaintenanceRequestModel accountMaintenanceRequestModel);

    public void clearAllAccounts();

    }
