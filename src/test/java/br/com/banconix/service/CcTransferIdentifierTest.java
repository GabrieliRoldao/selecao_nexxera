package br.com.banconix.service;

import br.com.banconix.enums.TransferType;
import br.com.banconix.model.Account;
import br.com.banconix.model.Transfer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CcTransferIdentifierTest {

    @Test
    public void shouldReturnTransferTypeCcWhenTransferIsFromSameBank() {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("BB");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);

        CcTransferIdentifier ccTransferIdentifier = new CcTransferIdentifier();

        TransferType transferType = ccTransferIdentifier.identify(transfer);

        assertEquals(TransferType.CC, transferType);
    }

    @Test
    public void shouldReturnNullWhenTransferIfFromDifferentBank() {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("CC");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);

        CcTransferIdentifier ccTransferIdentifier = new CcTransferIdentifier();

        TransferType transferType = ccTransferIdentifier.identify(transfer);

        assertNull(transferType);
    }
}
