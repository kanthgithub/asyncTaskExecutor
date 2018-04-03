package com.asyncTaskExecutor.task;

import com.asyncTaskExecutor.model.AccountBalanceRequestModel;
import com.asyncTaskExecutor.model.AccountBalanceResposeModel;
import com.asyncTaskExecutor.service.IAccountBalanceQueryService;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.LoggerFactory;

public class QueryAccountTask implements Callable<ResponseEntity<?>> {

    private final Logger log = LoggerFactory.getLogger(QueryAccountTask.class);

    private final AccountBalanceRequestModel accountBalanceRequestModel;

    private IAccountBalanceQueryService accountBalanceQueryService;

    public QueryAccountTask(AccountBalanceRequestModel accountBalanceRequestModel, IAccountBalanceQueryService accountBalanceQueryService) {

        if (accountBalanceRequestModel == null) {
            throw new IllegalStateException("account is not set.");
        }

        if (accountBalanceQueryService == null) {
            throw new IllegalStateException("accountBalanceQueryService is not set.");
        }

        this.accountBalanceRequestModel = accountBalanceRequestModel;
        this.accountBalanceQueryService = accountBalanceQueryService;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public ResponseEntity<?> call() throws Exception {
        log.info("QueryAccountTask: triggering query for account details: {} ",accountBalanceRequestModel);

        AccountBalanceResposeModel accountBalanceResposeModel = accountBalanceQueryService.queryAccount(accountBalanceRequestModel);

        log.info("QueryAccountTask: completed querying account details: {} ",accountBalanceResposeModel);

        return new ResponseEntity<AccountBalanceResposeModel>(
                accountBalanceResposeModel, HttpStatus.OK);
    }

}