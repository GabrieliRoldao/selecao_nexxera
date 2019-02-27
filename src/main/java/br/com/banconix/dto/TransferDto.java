package br.com.banconix.dto;

import br.com.banconix.model.Transfer;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;


public class TransferDto {

    private Long userId;

    private String payerName;

    private String payerBank;

    private String payerAgency;

    private String payerAccount;

    private String recipientName;

    private String recipientBank;

    private String recipientAgency;

    private String recipientAccount;

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal value;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerBank() {
        return payerBank;
    }

    public void setPayerBank(String payerBank) {
        this.payerBank = payerBank;
    }

    public String getPayerAgency() {
        return payerAgency;
    }

    public void setPayerAgency(String payerAgency) {
        this.payerAgency = payerAgency;
    }

    public String getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientBank() {
        return recipientBank;
    }

    public void setRecipientBank(String recipientBank) {
        this.recipientBank = recipientBank;
    }

    public String getRecipientAgency() {
        return recipientAgency;
    }

    public void setRecipientAgency(String recipientAgency) {
        this.recipientAgency = recipientAgency;
    }

    public String getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Transfer transformToTransfer() {
        Transfer transfer = new Transfer();
        transfer.getAccountPayer().setAccountName(this.payerName);
        transfer.getAccountPayer().setAccountNrAccount(this.payerAccount);
        transfer.getAccountPayer().setAccountBank(this.payerBank);
        transfer.getAccountPayer().setAccountAgency(this.payerAgency);
        transfer.getAccountRecipient().setAccountName(this.recipientName);
        transfer.getAccountRecipient().setAccountNrAccount(this.recipientAccount);
        transfer.getAccountRecipient().setAccountBank(this.recipientBank);
        transfer.getAccountRecipient().setAccountAgency(this.recipientAgency);
        transfer.setValue(this.value);
        return transfer;
    }
}
