package com.asyncTaskExecutor.controller;

import com.asyncTaskExecutor.AsyncTaskExecutorApplication;
import com.asyncTaskExecutor.config.AsyncTaskExecutorDemoAppLoadTestForFailureConfig;
import com.asyncTaskExecutor.model.AccountBalanceResposeModel;
import com.asyncTaskExecutor.model.AccountMaintenanceResposeModel;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Re
 */
@ContextConfiguration(classes = {AsyncTaskExecutorDemoAppLoadTestForFailureConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AsyncTaskExecutorApplication.class},
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class AsyncTaskExecutorDemoAppIntegrationTimeoutTest {
    static final Logger log = LoggerFactory.getLogger(AsyncTaskExecutorDemoAppIntegrationTimeoutTest.class);

    @LocalServerPort
    private int serverport;

    private String url;

    RestTemplate restTemplate = new RestTemplate();


    @Before
    public void setUp() throws Exception{
        url="http://localhost:"+serverport;
    }

    public void initializeAccounts(String account,String initialBalance) throws Exception{
        String addAccountsURL = String.format("%s/rejiMobile/addAccount/%s/%s",url,account,initialBalance);
        restTemplate.postForEntity(addAccountsURL,null,AccountMaintenanceResposeModel.class);

    }

    public void cleanUpAccounts() throws Exception{
        String cleanUpAccountsURL = url+"/rejiMobile/cleanAllAccounts";
        restTemplate.postForEntity(cleanUpAccountsURL,null,Boolean.class);
    }

    @Test
    public void load_Testing_Async_Calls() throws Exception{
        cleanUpAccounts();
        initializeAccounts("99875432","100.0000");
        String queryResourceUrl = url+"/rejiMobile/getAccountBalance/99875432";
        ResponseEntity<AccountBalanceResposeModel> response = null;
        try {
           response = restTemplate.getForEntity(queryResourceUrl, AccountBalanceResposeModel.class);
        }catch(Exception excep){
            assertNull(response);
            assertNotNull(excep);
            assertThat(excep, IsInstanceOf.instanceOf(HttpServerErrorException.class));

            HttpServerErrorException httpServerErrorException = (HttpServerErrorException)excep;
        }
    }

}
