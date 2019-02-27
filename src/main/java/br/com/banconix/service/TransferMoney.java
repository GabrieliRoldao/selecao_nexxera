package br.com.banconix.service;

import br.com.banconix.dto.TransferDto;
import br.com.banconix.dto.TransferPageableDto;
import br.com.banconix.enums.TransferStatus;
import br.com.banconix.enums.TransferType;
import br.com.banconix.exception.InvalidMaxValueToTransferException;
import br.com.banconix.exception.InvalidValueToTransferException;
import br.com.banconix.interfaces.TransferIdentifier;
import br.com.banconix.model.Transfer;
import br.com.banconix.model.User;
import br.com.banconix.repository.TransferRepository;
import br.com.banconix.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransferMoney {

    private List<TransferIdentifier> allowedTransfers;

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;

    public TransferMoney(TransferRepository transferRepository,
                         UserRepository userRepository) {
        this.allowedTransfers = new ArrayList<>();
        this.allowedTransfers.add(new CcTransferIdentifier());
        this.allowedTransfers.add(new TedTransferIdentifier());
        this.allowedTransfers.add(new DocTransferIdentifier());


        this.transferRepository = transferRepository;
        this.userRepository = userRepository;
    }

    public void transfer(TransferDto transferDto) {
        isTransferValid(transferDto);

        Transfer transferData = transferDto.transformToTransfer();
        transferData.setUser(this.findUser(transferDto.getUserId()));
        transferData.setTransferStatus(TransferStatus.OK);

        for (TransferIdentifier transferIdentifier : this.allowedTransfers) {
            TransferType transferType = transferIdentifier.identify(transferData);
            if (transferType != null) {
                transferData.setTransferType(transferType);
                break;
            }
        }

        transferRepository.save(transferData);
    }

    public TransferPageableDto findAllWithTotal(Pageable pageable) {

        Page<Transfer> transfers = this.transferRepository.findAll(pageable);

        return new TransferPageableDto(transfers, this.sumValues(transfers.getContent()));
    }

    public TransferPageableDto findAllByCreatedAt(String createdAt, Pageable pageable){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = null;
            parsedDate = dateFormat.parse(createdAt);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            Page<Transfer> transfers =  this.transferRepository.findAllByCreatedAtIsIn(timestamp, pageable);

            return new TransferPageableDto(transfers, this.sumValues(transfers.getContent()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TransferPageableDto findAllByPayerName(String payerName, Pageable pageable){
        Page<Transfer> transfers =  this.transferRepository.findAllByAccountPayer_AccountNameIgnoreCaseContaining(payerName, pageable);
        return new TransferPageableDto(transfers, this.sumValues(transfers.getContent()));
    }

    public TransferPageableDto findAllByRecipientName(String recipientName, Pageable pageable){
        Page<Transfer> transfers = this.transferRepository.findAllByAccountRecipient_AccountNameIgnoreCaseContaining(recipientName, pageable);
        return new TransferPageableDto(transfers, this.sumValues(transfers.getContent()));
    }

    public void delete(Long id) {
        Optional<Transfer> optionalTransfer = this.transferRepository.findById(id);
        if (optionalTransfer.isPresent()) {
            Transfer transfer = optionalTransfer.get();
            if (!transfer.isDeleted()) {
                transfer.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
                this.transferRepository.save(transfer);
            }
        }
    }

    private void isTransferValid(TransferDto transfer) {
        if (transfer.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidValueToTransferException("Por favor insira um valor maior que zero.");
        }

        if (transfer.getValue().compareTo(new BigDecimal("100000")) == 1) {
            throw new InvalidMaxValueToTransferException("O valor máximo permitido para transferência é de R$100.000");
        }
    }

    private BigDecimal sumValues(List<Transfer> transfers) {
        BigDecimal totalValue = new BigDecimal(0);
        for (Transfer transfer : transfers) {
            totalValue = totalValue.add(transfer.getValue());
        }
        return totalValue;
    }


    private User findUser(Long id) {
        return this.userRepository.findById(id).get();
    }

}
