package com.pension.pms.pensionmanagement.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PensionAPIException extends RuntimeException

{
    private HttpStatus status;
    private String message;
    public PensionAPIException(String message, HttpStatus status, String message1)
    {
        super(message);
        this.status = status;
        this.message = message1;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}