package com.asyncTaskExecutor.controller;

import com.asyncTaskExecutor.model.AccountMaintenanceResposeModel;
import com.asyncTaskExecutor.model.AccountTopupResposeModel;
import com.asyncTaskExecutor.config.AsyncTaskExecutorDemoAppLoadTestConfig;
import com.asyncTaskExecutor.model.AccountBalanceResposeModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.boot.context.embedded.*;

import java.math.BigDecimal;

import com.asyncTaskExecutor.AsyncTaskExecutorApplication;
import org.springframework.web.client.*;
import org.springframework.http.*;
import static org.hamcrest.CoreMatchers.equalTo;


/**
 * Re
 */
@ContextConfiguration(classes = {AsyncTaskExecutorDemoAppLoadTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AsyncTaskExecutorApplication.class},
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class AsyncTaskExecutorDemoAppIntegrationTest {

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
    public void test_Account_Balance() throws Exception{
        cleanUpAccounts();
        initializeAccounts("99875432","100.0000");
        String queryResourceUrl = url+"/rejiMobile/getAccountBalance/99875432";
        ResponseEntity<AccountBalanceResposeModel> response = restTemplate.getForEntity(queryResourceUrl, AccountBalanceResposeModel.class);
        assertNotNull(response);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        AccountBalanceResposeModel accountBalanceResposeModel = response.getBody();
        assertNotNull(accountBalanceResposeModel);
        assertEquals(BigDecimal.valueOf(100.0000).setScale(4,BigDecimal.ROUND_UP),accountBalanceResposeModel.getBalance());
    }

    @Test
    public void test_TopUp_Account() throws Exception{
        cleanUpAccounts();
        initializeAccounts("99875432","100.0000");
        String topupResourceUrl = url+"/rejiMobile/topupAccount/99875432/123.123";

        ResponseEntity<AccountTopupResposeModel> response =
                restTemplate.postForEntity(topupResourceUrl,null,AccountTopupResposeModel.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        AccountTopupResposeModel accountTopupResposeModel = response.getBody();
        assertNotNull(accountTopupResposeModel);
        assertEquals(BigDecimal.valueOf(223.1230).setScale(4,BigDecimal.ROUND_UP),accountTopupResposeModel.getAccountBalance());
    }


    @Test
    public void test_For_Invalid_Account() throws Exception{
        cleanUpAccounts();
        initializeAccounts("99875432","100.0000");
        String queryResourceUrl = url+"/rejiMobile/getAccountBalance/89875433";
        ResponseEntity<AccountBalanceResposeModel> response = restTemplate.getForEntity(queryResourceUrl, AccountBalanceResposeModel.class);
        assertNotNull(response);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        AccountBalanceResposeModel accountBalanceResposeModel = response.getBody();
        assertNotNull(accountBalanceResposeModel);
        assertNull(accountBalanceResposeModel.getBalance());
        assertNotNull(accountBalanceResposeModel.getMessageDescription());
        assertFalse(accountBalanceResposeModel.getSuccessful());
        assertEquals("Account Query Failed - Invalid Account 89875433",accountBalanceResposeModel.getMessageDescription());
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void test_For_Invalid_Topup_Amount() throws Exception{
        cleanUpAccounts();
        initializeAccounts("99875432","100.0000");
        String topupResourceUrl = url+"/rejiMobile/topupAccount/99875432/-123.123";

        ResponseEntity<AccountTopupResposeModel> response =
                restTemplate.postForEntity(topupResourceUrl,null,AccountTopupResposeModel.class);

        assertNotNull(response);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        AccountTopupResposeModel accountTopupResposeModel = response.getBody();
        assertNotNull(accountTopupResposeModel);
        assertNull(accountTopupResposeModel.getAccountBalance());
        assertNotNull(accountTopupResposeModel.getMessageDescription());
        assertFalse(accountTopupResposeModel.getSuccessful());
        assertEquals("Invalid TopupAmount: -123.123 for account: 99875432",accountTopupResposeModel.getMessageDescription());
    }






}
