package br.com.banconix.service;

import br.com.banconix.enums.TransferType;
import br.com.banconix.interfaces.TransferIdentifier;
import br.com.banconix.model.Transfer;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DocTransferIdentifier implements TransferIdentifier {

    @Override
    public TransferType identify(Transfer transfer) {
        boolean isTransferBetweenDifferentBank = !transfer.getAccountPayer().getAccountBank().equals
                (transfer.getAccountRecipient().getAccountBank());

        LocalTime tenAm = LocalTime.parse("10:00:00");
        LocalTime fourPm = LocalTime.parse("16:00:00");
        LocalTime twelvePm = LocalTime.parse("00:00");

        LocalDateTime nowDateTime = transfer.getCreatedAt().toLocalDateTime();
        LocalTime now = LocalTime.of(nowDateTime.getHour(), nowDateTime.getMinute(), nowDateTime.getSecond());

        boolean isTransferAfter4Pm = now.isAfter(fourPm);
        boolean isTransferBetween12PmAnd10Am = now.isAfter(twelvePm) && now.isBefore(tenAm);

        if (isTransferBetweenDifferentBank && (isTransferAfter4Pm || isTransferBetween12PmAnd10Am)) {
            return TransferType.DOC;
        }
        return null;
    }
}
