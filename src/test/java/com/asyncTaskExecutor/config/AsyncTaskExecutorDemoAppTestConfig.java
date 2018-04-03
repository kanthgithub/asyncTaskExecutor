package com.asyncTaskExecutor.config;

import com.asyncTaskExecutor.configuration.AsyncConfig;
import com.asyncTaskExecutor.repository.AccountStore;
import com.asyncTaskExecutor.service.AccountBalanceQueryServiceProxy;
import com.asyncTaskExecutor.service.AccountTopupServiceProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

@Configuration
public class AsyncTaskExecutorDemoAppTestConfig extends AsyncConfig {


    @Value("${accountBalance.delayRequired}")
    private Boolean accountBalanceDelayRequired;

    @Value("${accountBalance.delayPeriodInMilliSec}")
    private Integer accountBalanceDelayPeriodInMilliSec;


    @Value("${accountTopup.delayRequired}")
    private Boolean accountTopupDelayRequired;

    @Value("${accountTopup.delayPeriodInMilliSec}")
    private Integer accountTopupDelayPeriodInMilliSec;


    @Bean
    @Primary
    AccountBalanceQueryServiceProxy accountBalanceQueryServiceProxy(AccountStore accountStore){

        return new AccountBalanceQueryServiceProxy(accountStore)
                .setAccountBalanceDelayRequired(accountBalanceDelayRequired)
                .setAccountBalanceDelayPeriodInMilliSec(accountBalanceDelayPeriodInMilliSec);
    }

    @Bean
    @Primary
    AccountTopupServiceProxy accountTopupServiceProxy(AccountStore accountStore){

        return new AccountTopupServiceProxy(accountStore)
                .setAccountTopupDelayRequired(accountTopupDelayRequired)
                .setAccountTopupDelayPeriodInMilliSec(accountTopupDelayPeriodInMilliSec);
    }



}
