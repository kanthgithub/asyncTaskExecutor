package com.asyncTaskExecutor.model;

import java.io.Serializable;
import java.util.Objects;

public class AccountBalanceRequestModel implements Serializable
{
    private Integer account;

    public Integer getAccount() {
        return account;
    }

    public AccountBalanceRequestModel setAccount(Integer account) {
        this.account = account;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBalanceRequestModel that = (AccountBalanceRequestModel) o;
        return Objects.equals(account, that.account) ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(account);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AccountBalanceRequestModel{");
        sb.append("account=").append(account);
        sb.append('}');
        return sb.toString();
    }
}
