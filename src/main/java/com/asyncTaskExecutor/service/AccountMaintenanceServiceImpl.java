package com.asyncTaskExecutor.service;

import com.asyncTaskExecutor.exception.InValidAccountException;
import com.asyncTaskExecutor.exception.InvalidTopupAmountException;
import com.asyncTaskExecutor.repository.AccountStore;
import com.asyncTaskExecutor.util.MathHelper;
import com.asyncTaskExecutor.exception.DuplicateAccountException;
import com.asyncTaskExecutor.model.AccountMaintenanceRequestModel;
import com.asyncTaskExecutor.model.AccountMaintenanceResposeModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountMaintenanceServiceImpl implements IAccountMaintenanceService {

    @Autowired
    private AccountStore accountStore;

    public AccountMaintenanceResposeModel addAccount(AccountMaintenanceRequestModel accountMaintenanceRequestModel){

        BigDecimal accountBalance = null;

        Boolean isSuccessful = Boolean.FALSE;

        String messageDescription = null;

        String accountBalanceInput = accountMaintenanceRequestModel.getAccountBalance();
        Integer account = accountMaintenanceRequestModel.getAccount();

        BigDecimal accountBalanceInDecimal = new BigDecimal(accountBalanceInput).setScale(4, BigDecimal.ROUND_HALF_UP);

        if (!MathHelper.isValidAmount(accountBalanceInDecimal)) {
            messageDescription = String.format("Invalid accountBalanceInput: %s for account: %d", accountBalanceInput, account);
        } else {

            try {
                accountBalance = accountStore.addAccount(accountMaintenanceRequestModel.getAccount(),
                        accountBalanceInDecimal);
                isSuccessful = Boolean.TRUE;
                messageDescription =
                        String.format("Successfully added Account: %d by accountBalance: %s"
                                , account,accountBalanceInput);
            } catch (InValidAccountException excep) {
                messageDescription = excep.getDescription();
            }catch(InvalidTopupAmountException invalidTopupExc){
                messageDescription = invalidTopupExc.getDescription();
            }catch(DuplicateAccountException dupAccExc){
                messageDescription = dupAccExc.getDescription();
            }catch(Exception excepObj){
                messageDescription = String.format(
                        "fatal Error during the adding new account: %s and topupAmount: %s"
                        ,account,accountBalanceInput) ;
            }
        }

        return new AccountMaintenanceResposeModel().setAccount(accountMaintenanceRequestModel.getAccount())
                .setAccountBalance(accountBalanceInDecimal)
                .setSuccessful(isSuccessful)
                .setMessageDescription(messageDescription);

    }

    public void clearAllAccounts(){
        accountStore.clearAll();
    }


}
