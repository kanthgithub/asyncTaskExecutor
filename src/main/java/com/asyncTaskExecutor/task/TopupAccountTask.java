package com.asyncTaskExecutor.task;

import com.asyncTaskExecutor.model.AccountTopupRequestModel;
import com.asyncTaskExecutor.model.AccountTopupResposeModel;
import com.asyncTaskExecutor.service.IAccountTopupService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

import org.slf4j.LoggerFactory;

public class TopupAccountTask implements Callable<ResponseEntity<?>> {

    private final Logger log = LoggerFactory.getLogger(TopupAccountTask.class);


    private final AccountTopupRequestModel accountTopupRequestModel;

    private final IAccountTopupService accountTopupService;

    public TopupAccountTask(AccountTopupRequestModel accountTopupRequestModel, IAccountTopupService accountTopupService) {

        if (accountTopupRequestModel == null) {
            throw new IllegalStateException("accountTopupRequestModel is not set.");
        }

        if (accountTopupService == null) {
            throw new IllegalStateException("accountStore is not set.");
        }

        this.accountTopupRequestModel = accountTopupRequestModel;
        this.accountTopupService = accountTopupService;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public ResponseEntity<?> call() throws Exception {
        log.info("TopupAccountTask: triggering topup for account details: {} ",accountTopupRequestModel);

        AccountTopupResposeModel accountTopupResposeModel =
                accountTopupService.topupAccount(accountTopupRequestModel);
        log.info("TopupAccountTask: completed topup account details: {} ",accountTopupResposeModel);

        return new ResponseEntity<AccountTopupResposeModel>(
                accountTopupResposeModel, HttpStatus.OK);

    }

}