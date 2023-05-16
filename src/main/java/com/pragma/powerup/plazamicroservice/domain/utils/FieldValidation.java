package com.pragma.powerup.plazamicroservice.domain.utils;

import com.pragma.powerup.plazamicroservice.domain.exceptions.FieldValidationException;
import com.pragma.powerup.plazamicroservice.domain.model.Restaurant;

import java.util.HashMap;
import java.util.Map;

import static com.pragma.powerup.plazamicroservice.configuration.Constants.FIELD_NAME;
import static com.pragma.powerup.plazamicroservice.configuration.Constants.FIELD_VALIDATION;

public class FieldValidation {


    public static void restaurantValidate(Restaurant restaurant){
        Map<String, String> fieldValidation = new HashMap<>();

        if ( !nameIsValid( restaurant.getName() ) ) {
            fieldValidation.put( FIELD_NAME, FIELD_VALIDATION );
        }


        if ( fieldValidation.size() > 0 ) {
            throw new FieldValidationException( fieldValidation );
        }

    }

    public static boolean nameIsValid(String name){

        if ( utils.isNumber( name ) ) {
            return false;
        }
        return true;

    }


    public static boolean phoneIsValid(String phone){

        if ( phone.length() != 13 ) {
            return false;
        }

        if ( phone.charAt( 0 ) != '+' ) {
            return false;
        }

        for (int i = 1; i < 13; i++) {
            if ( !Character.isDigit( phone.charAt( i ) ) ) {
                return false;
            }
        }

        return true;

    }

    public static boolean dniIsValid(String dni){

        return utils.isNumber( dni );

    }

    public static boolean ageIsValid(String birthDate){

        if ( utils.calculateAge( birthDate ) < 18 ) {
            return false;
        }
        return true;
    }




}
