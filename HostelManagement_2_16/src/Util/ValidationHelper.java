/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.regex.Pattern;

public class ValidationHelper {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[0-9]{10,13}$"
    );
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.replaceAll("\\s+", "")).matches();
    }
    
    public static boolean isValidKTP(String ktp) {
        return ktp != null && ktp.length() == 16 && ktp.matches("\\d+");
    }
    
    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
    
    public static String sanitize(String text) {
        return text != null ? text.trim() : "";
    }
}
