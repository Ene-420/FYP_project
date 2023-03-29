package com.example.fypproject;

import java.util.Random;

public class RandomIdGenerator {

    private final String letters = "abcdefghijklmnopqrstuvwxyz";
    private final char[] alphaNumeric = (letters + letters.toUpperCase() + "0123456789").toCharArray();

    public String randomAlphaValue(){
        int length= 6;
        StringBuilder result = new StringBuilder();

        for(int i= 0; i < length; i++){
            result.append(alphaNumeric[new Random().nextInt(alphaNumeric.length)]);
        }
        return result.toString();
    }
}
