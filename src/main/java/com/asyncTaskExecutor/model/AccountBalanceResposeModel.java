package com.asyncTaskExecutor.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class AccountBalanceResposeModel implements Serializable
{
    private Integer account;
    private BigDecimal balance;
    private Boolean isSuccessful;
    private String messageDescription;

    public Integer getAccount() {
        return account;
    }

    public AccountBalanceResposeModel setAccount(Integer account) {
        this.account = account;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountBalanceResposeModel setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public Boolean getSuccessful() {
        return isSuccessful;
    }

    public AccountBalanceResposeModel setSuccessful(Boolean successful) {
        isSuccessful = successful;
        return this;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public AccountBalanceResposeModel setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBalanceResposeModel that = (AccountBalanceResposeModel) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(isSuccessful, that.isSuccessful) &&
                Objects.equals(messageDescription, that.messageDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(account, balance, isSuccessful, messageDescription);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AccountBalanceResposeModel{");
        sb.append("account=").append(account);
        sb.append(", balance=").append(balance);
        sb.append(", isSuccessful=").append(isSuccessful);
        sb.append(", messageDescription='").append(messageDescription).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
