package com.asyncTaskExecutor.model;

import java.io.Serializable;
import java.util.Objects;

public class AccountMaintenanceRequestModel implements Serializable
{
    private Integer account;
    private String accountBalance;

    public Integer getAccount() {
        return account;
    }

    public AccountMaintenanceRequestModel setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public AccountMaintenanceRequestModel setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountMaintenanceRequestModel that = (AccountMaintenanceRequestModel) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(accountBalance, that.accountBalance);
    }

    @Override
    public int hashCode() {

        return Objects.hash(account, accountBalance);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AccountTopupRequestModel{");
        sb.append("account=").append(account);
        sb.append(", accountBalance=").append(accountBalance);
        sb.append('}');
        return sb.toString();
    }
}
