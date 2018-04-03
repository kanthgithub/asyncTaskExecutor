package com.asyncTaskExecutor.controller;

import com.asyncTaskExecutor.model.AccountBalanceRequestModel;
import com.asyncTaskExecutor.service.IAccountBalanceQueryService;
import com.asyncTaskExecutor.task.QueryAccountTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.springframework.http.*;

import java.util.concurrent.*;


@Controller
public class AccountBalanceQueryController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(AccountBalanceQueryController.class);

    @Autowired
    private IAccountBalanceQueryService accountBalanceQueryService;

    @RequestMapping(value = "/rejiMobile/getAccountBalance/{account}", method = RequestMethod.GET)
    public Callable<ResponseEntity<?>> getAccountBalance(@PathVariable Integer account) {
        log.info("AccountBalanceQueryController queryBalance for account {}",account);

        return new QueryAccountTask(new AccountBalanceRequestModel().setAccount(account), accountBalanceQueryService);
    }

}
