package fr.ing.interview.dto;

import fr.ing.interview.entity.TransactionTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionOutputDTO {
    private LocalDateTime time;
    private TransactionTypeEnum type;
    private BigDecimal amount;
}
