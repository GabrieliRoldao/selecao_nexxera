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

public class TedTransferIdentifierTest {

    @Test
    public void shouldReturnTransferTypeTedWhenTransferIsBetween10AmAnd4PmAndValueIsLessThan5k () {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("CC");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);
        transfer.setValue(new BigDecimal(4999));
        transfer.setCreatedAt(Timestamp.valueOf(LocalDateTime.parse("2019-02-25T11:15:00")));

        TedTransferIdentifier tedTransferIdentifier = new TedTransferIdentifier();
        TransferType transferType = tedTransferIdentifier.identify(transfer);

        assertEquals(TransferType.TED, transferType);
    }

    @Test
    public void shouldReturnTransferTypeTedWhenTransferIsBetween10AmAnd4PmAndValueIsEqual5k () {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("CC");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);
        transfer.setValue(new BigDecimal(500));
        transfer.setCreatedAt(Timestamp.valueOf(LocalDateTime.parse("2019-02-25T11:15:00")));

        TedTransferIdentifier tedTransferIdentifier = new TedTransferIdentifier();
        TransferType transferType = tedTransferIdentifier.identify(transfer);

        assertEquals(TransferType.TED, transferType);
    }

    @Test
    public void shouldReturnNullWhenTransferIsBetween10AmAnd4PmAndValueIsGreaterThan5k () {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("CC");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);
        transfer.setValue(new BigDecimal(5001));
        transfer.setCreatedAt(Timestamp.valueOf(LocalDateTime.parse("2019-02-25T11:15:00")));

        TedTransferIdentifier tedTransferIdentifier = new TedTransferIdentifier();
        TransferType transferType = tedTransferIdentifier.identify(transfer);

        assertNull(transferType);
    }

    @Test
    public void shouldReturnNullWhenTransferIsNotBetween10AmAnd4PmAndValueIsLessThan5k () {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("CC");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);
        transfer.setValue(new BigDecimal(3500));
        transfer.setCreatedAt(Timestamp.valueOf(LocalDateTime.parse("2019-02-25T16:00:01")));

        TedTransferIdentifier tedTransferIdentifier = new TedTransferIdentifier();
        TransferType transferType = tedTransferIdentifier.identify(transfer);

        assertNull(transferType);
    }

    @Test
    public void shouldReturnNullWhenTransferIsNotBetween10AmAnd4PmAndValueIsGreaterThan5k () {
        Account accountPayer = new Account();
        Account accountRecipient = new Account();

        accountPayer.setAccountBank("BB");
        accountRecipient.setAccountBank("BB");

        Transfer transfer = new Transfer();
        transfer.setAccountPayer(accountPayer);
        transfer.setAccountRecipient(accountRecipient);
        transfer.setValue(new BigDecimal(8000));
        transfer.setCreatedAt(Timestamp.valueOf(LocalDateTime.parse("2019-02-25T16:01:01")));

        TedTransferIdentifier tedTransferIdentifier = new TedTransferIdentifier();
        TransferType transferType = tedTransferIdentifier.identify(transfer);

        assertNull(transferType);
    }

}
