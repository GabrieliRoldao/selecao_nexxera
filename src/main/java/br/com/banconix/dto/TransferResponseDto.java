package br.com.banconix.dto;

import br.com.banconix.enums.TransferStatus;

import java.io.Serializable;

public class TransferResponseDto implements Serializable {

    private TransferStatus transferStatus;

    private String message;

    public TransferResponseDto(TransferStatus transferStatus, String message) {
        this.transferStatus = transferStatus;
        this.message = message;
    }

    public TransferResponseDto(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
