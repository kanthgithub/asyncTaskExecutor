package com.asyncTaskExecutor.service;

import com.asyncTaskExecutor.model.AccountTopupRequestModel;
import com.asyncTaskExecutor.model.AccountTopupResposeModel;
import com.asyncTaskExecutor.repository.AccountStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountTopupServiceProxy extends AccountTopupServiceImpl {

    private final Logger log = LoggerFactory.getLogger(AccountTopupServiceProxy.class);

    public AccountTopupServiceProxy(AccountStore accountStore) {
        super(accountStore);
    }

    private Boolean accountTopupDelayRequired;
    private Integer accountTopupDelayPeriodInMilliSec;

    public AccountTopupServiceProxy setAccountTopupDelayRequired(Boolean accountTopupDelayRequired) {
        this.accountTopupDelayRequired = accountTopupDelayRequired;
        return this;
    }

    public AccountTopupServiceProxy setAccountTopupDelayPeriodInMilliSec(Integer accountTopupDelayPeriodInMilliSec) {
        this.accountTopupDelayPeriodInMilliSec = accountTopupDelayPeriodInMilliSec;
        return this;
    }

    @Override
    public AccountTopupResposeModel topupAccount(AccountTopupRequestModel accountTopupRequestModel) {
        if(accountTopupDelayRequired){
            addDelay(accountTopupDelayPeriodInMilliSec);
        }

        return execute(accountTopupRequestModel);

    }

    public void addDelay(Integer accountTopupDelayPeriodInMilliSec){

        try{
            Thread.sleep(accountTopupDelayPeriodInMilliSec);
        }catch(Exception excep){

            log.error("error during adding Delay");

        }


    }

}
