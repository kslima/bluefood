package com.kslima.bluefood.util;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.stream.Collectors;

public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }

        return str.trim().length() == 0;
    }

    public static String encrypt(String rawString) {
        if (isEmpty(rawString)) {
            return null;
        }
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(rawString);
    }

    public static String concatenate(Collection<String> strings) {

        if (strings == null || strings.isEmpty()) {
            return null;
        }

        return String.join(", ", strings).trim();
    }
    
}
