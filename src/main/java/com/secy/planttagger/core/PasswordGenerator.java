/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.secy.planttagger.core;

import java.security.SecureRandom;
import java.math.BigInteger;

public class PasswordGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String generate() {
        return new BigInteger(130, random).toString(32);
    }
}