package fr.ing.interview.bean;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionDTO {
    @NotNull
    private String customerCode;
    @NotNull
    private String accountNumber;
    @NotNull
    private BigDecimal amount;
}
