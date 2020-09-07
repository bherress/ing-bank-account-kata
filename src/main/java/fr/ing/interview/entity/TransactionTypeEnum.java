package fr.ing.interview.entity;

public enum TransactionTypeEnum {
    DEPOSIT("Deposit"),
    WITHDRAM("Withdraw");

    private String name;

    TransactionTypeEnum(String name){
        this.name = name;
    }
}
