package br.com.banconix.service;

import br.com.banconix.enums.TransferType;
import br.com.banconix.interfaces.TransferIdentifier;
import br.com.banconix.model.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TedTransferIdentifier implements TransferIdentifier {

    @Override
    public TransferType identify(Transfer transfer) {
        LocalTime tenAm = LocalTime.of(10, 00,00);
        LocalTime fourPm = LocalTime.of(16,00,00);

        LocalDateTime nowDateTime = transfer.getCreatedAt().toLocalDateTime();
        LocalTime now = LocalTime.of(nowDateTime.getHour(), nowDateTime.getMinute(), nowDateTime.getSecond());

        boolean isTransferBetween10AmAnd4Pm = now.isAfter(tenAm) && now.isBefore(fourPm);
        boolean isValueLessThan5k = transfer.getValue().compareTo(new BigDecimal("5000")) < 0;

        if (isTransferBetween10AmAnd4Pm && isValueLessThan5k) {
            return TransferType.TED;
        }
        return null;
    }
}
