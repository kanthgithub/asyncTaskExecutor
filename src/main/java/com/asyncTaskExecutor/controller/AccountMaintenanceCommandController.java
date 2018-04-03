package com.asyncTaskExecutor.controller;

import com.asyncTaskExecutor.model.AccountMaintenanceRequestModel;
import com.asyncTaskExecutor.service.IAccountMaintenanceService;
import com.asyncTaskExecutor.task.AccountCleanupTask;
import com.asyncTaskExecutor.task.AccountMaintenanceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.Callable;

@Controller
public class AccountMaintenanceCommandController {

    private final Logger log = LoggerFactory.getLogger(AccountMaintenanceCommandController.class);

    @Autowired
    private IAccountMaintenanceService accountMaintenanceService;

    @RequestMapping(value = "/rejiMobile/addAccount/{account}/{accountBalance:.+}", method = RequestMethod.POST)
    public Callable<ResponseEntity<?>> addAccount(@PathVariable Integer account,
                                                    @PathVariable String accountBalance) {
        log.info("AccountMaintenanceCommandController addAccount for account: {} and accountBalance: {}"
                ,account,accountBalance);

        return new AccountMaintenanceTask(new AccountMaintenanceRequestModel()
                                                .setAccount(account).setAccountBalance(accountBalance),
                                                accountMaintenanceService);
    }

    @RequestMapping(value = "/rejiMobile/cleanAllAccounts", method = RequestMethod.POST)
    public Callable<ResponseEntity<?>> cleanAllAccounts() {
        log.info("AccountTopupCommandController cleanAllAccounts");

        return new AccountCleanupTask(accountMaintenanceService);
    }

}
