package br.com.banconix.repository;

import br.com.banconix.model.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface TransferRepository extends PagingAndSortingRepository<Transfer, Long> {
    Page<Transfer> findAll(Pageable pageable);

    Page<Transfer> findAllByCreatedAtIsIn(Timestamp createdAt, Pageable pageable);

    Page<Transfer> findAllByAccountPayer_AccountNameIgnoreCaseContaining(String payerName, Pageable pageable);

    Page<Transfer> findAllByAccountRecipient_AccountNameIgnoreCaseContaining(String recipientName, Pageable pageable);

}
