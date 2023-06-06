package com.pragma.powerup.plazamicroservice.domain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static boolean isNumber(String number){

        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static int calculateAge(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date birthDate;
        try {
            birthDate = dateFormat.parse(date);
        } catch (ParseException e) {
            return 0;
        }
        Date currentDate = new Date();
        final long yearInMilliiseconds = 31557600000L;//31536000000L;

        long edadEnMiliseg = currentDate.getTime() - birthDate.getTime();
        int edad = Math.round(edadEnMiliseg/yearInMilliiseconds);

        return edad;
    }



}
