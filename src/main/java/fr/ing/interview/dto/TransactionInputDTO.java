package fr.ing.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInputDTO {
    @NotNull
    private String customerId;
    @NotNull
    private String accountId;
    @NotNull
    private BigDecimal amount;
}
