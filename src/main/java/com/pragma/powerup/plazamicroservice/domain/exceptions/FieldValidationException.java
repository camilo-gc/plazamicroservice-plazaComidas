package com.pragma.powerup.plazamicroservice.domain.exceptions;

import java.util.Map;

public class FieldValidationException extends RuntimeException {

    private Map field;
    public FieldValidationException(Map field) {
        super();
        this.field = field;

    }

    public Map getField(){
        return this.field;
    }
}
