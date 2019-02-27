package br.com.banconix.model;

import br.com.banconix.enums.TransferStatus;
import br.com.banconix.enums.TransferType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Transfer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private java.lang.Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountName", column = @Column(name = "payerName", length = 128)),
            @AttributeOverride(name = "accountBank", column = @Column(name = "payerBank", length = 3)),
            @AttributeOverride(name = "accountAgency", column = @Column(name = "payerAgency", length = 4)),
            @AttributeOverride(name = "accountNrAccount", column = @Column(name = "payerAccount", length = 6)),
    })
    @Column(nullable = false)
    private Account accountPayer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountName", column = @Column(name = "recipientName", length = 128)),
            @AttributeOverride(name = "accountBank", column = @Column(name = "recipientBank", length = 3)),
            @AttributeOverride(name = "accountAgency", column = @Column(name = "recipientAgency", length = 4)),
            @AttributeOverride(name = "accountNrAccount", column = @Column(name = "recipientAccount", length = 6)),
    })
    @Column(nullable = false)
    private Account accountRecipient;

    @NotNull
    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false, length = 12)
    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @NotNull
    @Column(nullable = false, length = 4)
    @Enumerated(EnumType.STRING)
    private TransferStatus transferStatus;

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(nullable = true)
    private Timestamp deletedAt;


    public Transfer() {
        this.accountPayer = new Account();
        this.accountRecipient = new Account();
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public java.lang.Long getId() {
        return id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccountPayer() {
        return accountPayer;
    }

    public void setAccountPayer(Account account) {
        this.accountPayer = account;
    }

    public Account getAccountRecipient() {
        return accountRecipient;
    }

    public void setAccountRecipient(Account accountRecipient) {
        this.accountRecipient = accountRecipient;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(id, transfer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
