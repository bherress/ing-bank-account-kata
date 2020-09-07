package fr.ing.interview.exception;

import lombok.Getter;

@Getter
public class UnauthorizedOperationException extends RuntimeException{
    private ErrorTypeEnum errorTypeEnum;

    public UnauthorizedOperationException(ErrorTypeEnum errorTypeEnum) {
        super(errorTypeEnum.getMessage());
        this.errorTypeEnum = errorTypeEnum;
    }
}
