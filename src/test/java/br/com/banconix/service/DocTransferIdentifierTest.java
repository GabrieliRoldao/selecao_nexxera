package br.com.banconix.service;

import br.com.banconix.enums.TransferType;
import br.com.banconix.model.Account;
import br.com.banconix.model.Transfer;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DocTransferIdentifierTest {

    @Test
    public void shouldReturnTransferTypeDocWhenBankIsDifferentAndTransferIsBetween4PmAnd10Pm() {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("CC");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);

        DocTransferIdentifier docTransferIdentifier = new DocTransferIdentifier();
        TransferType transferType = docTransferIdentifier.identify(transfer);

        assertEquals(TransferType.DOC, transferType);
    }

    @Test
    public void shouldReturnTransferTypeDocWhenBankIsDifferentAndTransferIsBetween4PmAnd10PmAndValueIsGreaterThan5k() {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("CC");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);
        transfer.setValue(new BigDecimal(5800));

        DocTransferIdentifier docTransferIdentifier = new DocTransferIdentifier();
        TransferType transferType = docTransferIdentifier.identify(transfer);

        assertEquals(TransferType.DOC, transferType);
    }

    @Test
    public void shouldReturnTransferNullWhenBankIsTheSame() {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("BB");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);

        DocTransferIdentifier docTransferIdentifier = new DocTransferIdentifier();
        TransferType transferType = docTransferIdentifier.identify(transfer);

        assertNull(transferType);
    }

    @Test
    public void shouldReturnTransferNullWhenBankIsDifferentAndTransferIsBetween10AmAnd4Pm() {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("CC");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);
        transfer.setCreatedAt(Timestamp.valueOf(LocalDateTime.parse("2019-02-25T11:15:00")));

        DocTransferIdentifier docTransferIdentifier = new DocTransferIdentifier();
        TransferType transferType = docTransferIdentifier.identify(transfer);

        assertNull(transferType);
    }
}
