package com.asyncTaskExecutor.task;

import com.asyncTaskExecutor.model.AccountMaintenanceRequestModel;
import com.asyncTaskExecutor.model.AccountMaintenanceResposeModel;
import com.asyncTaskExecutor.service.IAccountMaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

public class AccountMaintenanceTask implements Callable<ResponseEntity<?>> {


    private final AccountMaintenanceRequestModel accountMaintenanceRequestModel;

    private final IAccountMaintenanceService accountMaintenanceService;

    public AccountMaintenanceTask(AccountMaintenanceRequestModel accountMaintenanceRequestModel,
                                  IAccountMaintenanceService accountMaintenanceService) {

        if (accountMaintenanceRequestModel == null) {
            throw new IllegalStateException("accountMaintenanceRequestModel is not set.");
        }

        if (accountMaintenanceService == null) {
            throw new IllegalStateException("accountMaintenanceService is not set.");
        }

        this.accountMaintenanceRequestModel = accountMaintenanceRequestModel;
        this.accountMaintenanceService = accountMaintenanceService;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public ResponseEntity<?> call() throws Exception {
        AccountMaintenanceResposeModel accountMaintenanceResponseModel =
                accountMaintenanceService.addAccount(accountMaintenanceRequestModel);

        return new ResponseEntity<AccountMaintenanceResposeModel>(
                accountMaintenanceResponseModel, HttpStatus.OK);

    }

}