package com.asyncTaskExecutor.config;

import com.asyncTaskExecutor.configuration.AsyncConfig;
import com.asyncTaskExecutor.repository.AccountStore;
import com.asyncTaskExecutor.service.AccountBalanceQueryServiceProxy;
import com.asyncTaskExecutor.service.AccountTopupServiceProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AsyncTaskExecutorDemoAppLoadTestForFailureConfig extends AsyncConfig {


    @Value("${accountBalance.loadtest.delayRequired}")
    private Boolean accountBalanceDelayRequired;

    @Value("${accountBalance.loadtest_failure.delayPeriodInMilliSec}")
    private Integer accountBalanceDelayPeriodInMilliSec;


    @Value("${accountTopup.loadtest.delayRequired}")
    private Boolean accountTopupDelayRequired;

    @Value("${accountTopup.loadtest_failure.delayPeriodInMilliSec}")
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
