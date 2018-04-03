package com.asyncTaskExecutor.repository;

import com.asyncTaskExecutor.AsyncTaskExecutorApplication;
import com.asyncTaskExecutor.configuration.AsyncConfig;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.test.context.support.*;
import org.springframework.test.context.*;

@ContextConfiguration(classes = {AsyncConfig.class})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AsyncTaskExecutorApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestExecutionListeners(listeners = {DirtiesContextBeforeModesTestExecutionListener.class, DirtiesContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
public class AccountStoreTest {

    @Autowired
    AccountStore accountStore;

    @Test
    public void test_Query_Account_Balance(){

        //given
        Integer account = 99871122;

        BigDecimal accountBalance_ExpectedValue = BigDecimal.ZERO;

        accountStore.clearAll();

        accountStore.addAccount(99871122,new BigDecimal(0.0));


        //when
        BigDecimal accountBalance_Actual = accountStore.queryBalance(account);

        //then (assert Initialized Balance as Zero)
        assertNotNull(accountBalance_Actual);
        assertEquals(accountBalance_ExpectedValue,accountBalance_Actual);

        //given (topup by non-zero amount
        accountBalance_ExpectedValue =
                accountStore.topupAccount(account,BigDecimal.valueOf(121.22));


        //when
        accountBalance_Actual = accountStore.queryBalance(account);

        //then (assert that accountBalance is equal to toppedup Amount)
        assertNotNull(accountBalance_Actual);
        assertEquals(accountBalance_ExpectedValue,accountBalance_Actual);
    }

    @Test
    public void test_topup_Account(){

        //given
        Integer account = 99871122;

        accountStore.clearAll();

        accountStore.addAccount(99871122,new BigDecimal(0.0));

        BigDecimal accountBalance_ExpectedValue = BigDecimal.ZERO;

        BigDecimal accountBalance_Actual = accountStore.queryBalance(account);

        // (assert Initialized Balance as Zero)
        assertNotNull(accountBalance_Actual);
        assertEquals(accountBalance_ExpectedValue,accountBalance_Actual);

        //when (topup by non-zero amount
        accountBalance_ExpectedValue =
                accountStore.topupAccount(account,BigDecimal.valueOf(1000));


        //then
        accountBalance_Actual = accountStore.queryBalance(account);

        //then (assert that accountBalance is equal to toppedup Amount)
        assertNotNull(accountBalance_Actual);
        assertEquals(accountBalance_ExpectedValue,accountBalance_Actual);
    }


}
