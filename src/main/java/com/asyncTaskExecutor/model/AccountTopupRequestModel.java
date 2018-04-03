package com.asyncTaskExecutor.model;

import java.io.Serializable;
import java.util.Objects;

public class AccountTopupRequestModel implements Serializable
{
    private Integer account;
    private String topupAmount;

    public Integer getAccount() {
        return account;
    }

    public AccountTopupRequestModel setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public String getTopupAmount() {
        return topupAmount;
    }

    public AccountTopupRequestModel setTopupAmount(String topupAmount) {
        this.topupAmount = topupAmount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountTopupRequestModel that = (AccountTopupRequestModel) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(topupAmount, that.topupAmount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(account, topupAmount);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AccountTopupRequestModel{");
        sb.append("account=").append(account);
        sb.append(", topupAmount=").append(topupAmount);
        sb.append('}');
        return sb.toString();
    }
}
