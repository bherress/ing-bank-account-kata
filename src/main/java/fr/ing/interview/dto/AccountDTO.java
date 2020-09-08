package fr.ing.interview.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDTO {
    private String id;
    private BigDecimal balance;
}
