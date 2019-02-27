package br.com.banconix.dto;

import br.com.banconix.model.Transfer;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public class TransferPageableDto {
    private Page<Transfer> data;

    private BigDecimal transfersTotalValue;

    public TransferPageableDto(Page<Transfer> data, BigDecimal transfersTotalValue) {
        this.data = data;
        this.transfersTotalValue = transfersTotalValue;
    }

    public TransferPageableDto() {
    }

    public Page<Transfer> getData() {
        return data;
    }

    public void setData(Page<Transfer> data) {
        this.data = data;
    }

    public BigDecimal getTransfersTotalValue() {
        return transfersTotalValue;
    }

    public void setTransfersTotalValue(BigDecimal transfersTotalValue) {
        this.transfersTotalValue = transfersTotalValue;
    }

}
