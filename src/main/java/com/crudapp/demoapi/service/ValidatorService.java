package com.crudapp.demoapi.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    public boolean isValidUserName(String username) {
        if (!StringUtils.isEmpty(username))
        {
            if (username.length() >= 4 && username.length() <= 15) {
                char[] characters = username.toCharArray();
                for (Character character : characters) {
                    if (!Character.isLetter(character) && !Character.isDigit(character)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean isValidPassword(String password) {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        if (!StringUtils.isEmpty(password)) {
            if (password.length() >= 8 && password.length() <= 15) {
                char[] chars = password.toCharArray();
                for (char c : chars) {
                    if (Character.isUpperCase(c)) {
                        hasUpper = true;
                    } else if (Character.isLowerCase(c)) {
                        hasLower = true;
                    } else if (Character.isDigit(c)) {
                        hasDigit = true;
                    } else if (!Character.isLetterOrDigit(c)) {
                        hasSpecial = true;
                    }
                }
            }
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
