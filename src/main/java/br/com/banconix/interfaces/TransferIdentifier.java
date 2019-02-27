package br.com.banconix.interfaces;

import br.com.banconix.enums.TransferType;
import br.com.banconix.model.Transfer;

public interface TransferIdentifier {

    TransferType identify(Transfer transfer);
}
