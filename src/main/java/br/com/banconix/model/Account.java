package br.com.banconix.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Embeddable
public class Account {

    @NotEmpty
    @NotNull
    @Column(length = 128, nullable = false)
    private String accountName;

    @NotEmpty
    @NotNull
    @Column(length = 3, nullable = false)
    private String accountBank;

    @NotEmpty
    @NotNull
    @Column(length = 4, nullable = false)
    private String accountAgency;

    @NotEmpty
    @Column(length = 6, nullable = false)
    private String accountNrAccount;

    public Account(@NotEmpty String accountName, @NotEmpty String accountBank, @NotEmpty String accountAgency, @NotEmpty String accountNrAccount) {
        this.accountName = accountName;
        this.accountBank = accountBank;
        this.accountAgency = accountAgency;
        this.accountNrAccount = accountNrAccount;
    }

    public Account() {
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountAgency() {
        return accountAgency;
    }

    public void setAccountAgency(String accountAgency) {
        this.accountAgency = accountAgency;
    }

    public String getAccountNrAccount() {
        return accountNrAccount;
    }

    public void setAccountNrAccount(String accountNrAccount) {
        this.accountNrAccount = accountNrAccount;
    }


}
