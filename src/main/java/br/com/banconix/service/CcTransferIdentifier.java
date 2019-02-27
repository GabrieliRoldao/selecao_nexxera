package br.com.banconix.service;

import br.com.banconix.enums.TransferType;
import br.com.banconix.interfaces.TransferIdentifier;
import br.com.banconix.model.Transfer;

public class CcTransferIdentifier implements TransferIdentifier {

    @Override
    public TransferType identify(Transfer transfer) {
        if (transfer.getAccountPayer().getAccountBank().equals(transfer.getAccountRecipient().getAccountBank())) {
            return TransferType.CC;
        }
        return null;
    }
}
