package fr.ing.interview.exception;

import lombok.Getter;

@Getter
public enum ErrorTypeEnum {
    NEGATIVE_BALANCE("Opération impossible, le solde est insuffisant"),
    NOT_VALID_AMOUNT("Opération impossible, Montant déposé insuffisant"),
    ACCOUNT_CUSTOMER_NOT_FOUND("Opération impossible, Ce client ne possède pas de compte avec ce numero"),
    TRANSACTION_NOT_FOUND("Opération impossible, aucune transaction trouvé pour ce couple compte client");


    private String message;

    ErrorTypeEnum(String message){
        this.message = message;
    }
}
