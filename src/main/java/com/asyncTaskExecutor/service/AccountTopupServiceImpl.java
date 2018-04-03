package com.asyncTaskExecutor.service;

import com.asyncTaskExecutor.exception.InValidAccountException;
import com.asyncTaskExecutor.exception.InvalidTopupAmountException;
import com.asyncTaskExecutor.model.AccountTopupRequestModel;
import com.asyncTaskExecutor.model.AccountTopupResposeModel;
import com.asyncTaskExecutor.repository.AccountStore;
import com.asyncTaskExecutor.util.MathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountTopupServiceImpl implements IAccountTopupService {

    private AccountStore accountStore;


    @Autowired
    public AccountTopupServiceImpl(AccountStore accountStore) {
        this.accountStore = accountStore;
    }


    public AccountTopupResposeModel topupAccount(AccountTopupRequestModel accountTopupRequestModel){

        return execute(accountTopupRequestModel);

    }

    public AccountTopupResposeModel execute(AccountTopupRequestModel accountTopupRequestModel) {
        BigDecimal accountBalance = null;

        Boolean isSuccessful = Boolean.FALSE;

        String messageDescription = null;

        String topupAmount = accountTopupRequestModel.getTopupAmount();
        Integer account = accountTopupRequestModel.getAccount();

        BigDecimal topupAmountInDecimal = new BigDecimal(topupAmount).setScale(4, BigDecimal.ROUND_HALF_UP);

        if (!MathHelper.isValidAmount(topupAmountInDecimal)) {
            messageDescription = String.format("Invalid TopupAmount: %s for account: %d", topupAmount, account);
        } else {

            try {
                accountBalance = accountStore.topupAccount(accountTopupRequestModel.getAccount(),
                        topupAmountInDecimal);
                isSuccessful = Boolean.TRUE;
                messageDescription =
                        String.format("Successfully Topped-up Account: %d by topupAmount: %s and new AccountBalance is: %s"
                                , account,topupAmount, String.valueOf(accountBalance));
            } catch (InValidAccountException excep) {
                messageDescription = excep.getDescription();
            }catch(InvalidTopupAmountException invalidTopupExc){
                messageDescription = invalidTopupExc.getDescription();
            }catch(Exception excepObj){
                messageDescription = String.format(
                        "fatal Error during the topup activity for account: %s and topupAmount: %s"
                        ,account,topupAmount) ;
            }
        }

        return new AccountTopupResposeModel().setAccount(accountTopupRequestModel.getAccount())
                .setAccountBalance(accountBalance)
                .setTopupAmount(topupAmountInDecimal)
                .setSuccessful(isSuccessful)
                .setMessageDescription(messageDescription);
    }

}
