package com.asyncTaskExecutor.controller;

import com.asyncTaskExecutor.AsyncTaskExecutorApplication;
import com.asyncTaskExecutor.config.AsyncTaskExecutorDemoAppLoadTestConfig;
import com.asyncTaskExecutor.model.AccountBalanceResposeModel;
import com.asyncTaskExecutor.model.AccountMaintenanceResposeModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import java.util.concurrent.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


@ContextConfiguration(classes = {AsyncTaskExecutorDemoAppLoadTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AsyncTaskExecutorApplication.class},
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class AsyncTaskExecutorDemoAppIntegrationLoadTest {
    static final Logger log = LoggerFactory.getLogger(AsyncTaskExecutorDemoAppIntegrationLoadTest.class);

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
        ResponseEntity<AccountBalanceResposeModel> response = restTemplate.getForEntity(queryResourceUrl, AccountBalanceResposeModel.class);
        assertNotNull(response);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        AccountBalanceResposeModel accountBalanceResposeModel = response.getBody();
        assertNotNull(accountBalanceResposeModel);
        assertEquals(BigDecimal.valueOf(100.0000).setScale(4,BigDecimal.ROUND_UP),accountBalanceResposeModel.getBalance());
    }



    @Test
    public void testConcurrentMaxUsers() throws Exception{

        cleanUpAccounts();

        for(int index=0;index<40;index++) {
            initializeAccounts(index+"","100.0000");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(35);

        for(int index=0;index<25;index++){
            log.info("Running RestTemplate Get Call--> {}",index);

            String getRequestURL = url+"/rejiMobile/getAccountBalance/"+index;
            TestAsyncExecutorTask testAsyncExecutorTask = new TestAsyncExecutorTask(restTemplate,getRequestURL);
            executorService.submit(testAsyncExecutorTask);
        }

        String getRequestURL = url+"/rejiMobile/getAccountBalance/"+26;
        restTemplate.getForEntity(getRequestURL, AccountBalanceResposeModel.class);

    }

    public static class TestAsyncExecutorTask implements Callable<ResponseEntity<AccountBalanceResposeModel>>{
        private RestTemplate restTemplate;
        private String requestURL;

        public TestAsyncExecutorTask(RestTemplate restTemplate,String requestURL){
            this.restTemplate = restTemplate;
            this.requestURL = requestURL;
        }


        public ResponseEntity<AccountBalanceResposeModel> call(){
            try{
                return restTemplate.getForEntity(requestURL, AccountBalanceResposeModel.class);
            }catch (Exception ex){

            }

            return null;
        }
    }





}
