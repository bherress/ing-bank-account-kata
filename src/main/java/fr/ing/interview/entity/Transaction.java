package fr.ing.interview.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="time")
    private LocalDateTime time;

    @Column(name="type")
    private TransactionTypeEnum type;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", nullable = false)
    private Account account;

    public Transaction(TransactionTypeEnum type, Account account, BigDecimal amount){
        this.time = LocalDateTime.now();
        this.type = type;
        this.account = account;
        this.amount = amount;
    }
}
