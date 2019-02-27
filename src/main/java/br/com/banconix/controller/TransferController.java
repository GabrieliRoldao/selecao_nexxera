package br.com.banconix.controller;

import br.com.banconix.dto.TransferDto;
import br.com.banconix.dto.TransferPageableDto;
import br.com.banconix.dto.TransferResponseDto;
import br.com.banconix.enums.TransferStatus;
import br.com.banconix.exception.InvalidMaxValueToTransferException;
import br.com.banconix.exception.InvalidValueToTransferException;
import br.com.banconix.service.TransferMoney;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/transfer")
public class TransferController {

    @Autowired
    private TransferMoney transferMoney;

    @PostMapping
    @ApiOperation(value = "Save a Transfer object.", httpMethod = "POST", response = TransferResponseDto.class)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<TransferResponseDto> transfer(@RequestBody TransferDto transferDto) {
        try {
            transferMoney.transfer(transferDto);
            return new ResponseEntity<>(new TransferResponseDto(TransferStatus.OK, "Sucesso"), HttpStatus.OK);
        } catch (InvalidValueToTransferException | InvalidMaxValueToTransferException e) {
            return new ResponseEntity<>(new TransferResponseDto(TransferStatus.ERRO, e.getMessage()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TransferResponseDto(TransferStatus.ERRO, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @ApiOperation(value = "List all transfers.", httpMethod = "GET", response = TransferPageableDto.class)
    public ResponseEntity<?> allTransfers(Pageable pageable) {
        try {
            TransferPageableDto transfers = transferMoney.findAllWithTotal(pageable);
            return new ResponseEntity<>(transfers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TransferResponseDto(TransferStatus.ERRO, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/byCreatedAt/{createdAt}")
    @ApiOperation(value = "List all transfers by createdAt.", httpMethod = "GET", response = TransferPageableDto.class)
    public ResponseEntity<?> allTransfersByCreatedAt(@PathVariable("createdAt") String createdAt, Pageable pageable) {
        try {
            TransferPageableDto transfers = transferMoney.findAllByCreatedAt(createdAt, pageable);
            return new ResponseEntity<>( transfers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TransferResponseDto(TransferStatus.ERRO, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/payerName/{payerName}")
    @ApiOperation(value = "List all transfers by payer name.", httpMethod = "GET", response = TransferPageableDto.class)
    public ResponseEntity<?> allTransfersByPayerName(@PathVariable("payerName") String payerName, Pageable pageable) {
        try {
            TransferPageableDto transfers = transferMoney.findAllByPayerName(payerName, pageable);
            return new ResponseEntity<>( transfers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TransferResponseDto(TransferStatus.ERRO, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/recipientName/{recipientName}")
    @ApiOperation(value = "List all transfers by recipient name.", httpMethod = "GET", response = TransferPageableDto.class)
    public ResponseEntity<?> allTransfersByRecipientName(@PathVariable("recipientName") String recipientName, Pageable pageable) {
        try {
            TransferPageableDto transfers = transferMoney.findAllByRecipientName(recipientName, pageable);
            return new ResponseEntity<>( transfers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TransferResponseDto(TransferStatus.ERRO, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "{transfer}")
    @ApiOperation(value = "Delete an transfer by id.", httpMethod = "DELETE", response = ResponseEntity.class)
    public ResponseEntity<?> delete(@PathVariable("transfer") Long id) {
        try {
            transferMoney.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TransferResponseDto(TransferStatus.ERRO, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }

    }
}
