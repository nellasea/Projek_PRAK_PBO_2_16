/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;

public class DateHelper {
    
    private static final SimpleDateFormat INDONESIAN_FORMAT = new SimpleDateFormat("dd MMMM yyyy");
    private static final SimpleDateFormat SQL_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    public static String formatToIndonesian(Date sqlDate) {
        if (sqlDate == null) return "-";
        return INDONESIAN_FORMAT.format(sqlDate);
    }

    // FIXED: Improved parseToDate with better error handling
    public static Date parseToDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        
        try {
            // Try parsing yyyy-MM-dd format
            java.util.Date utilDate = SQL_FORMAT.parse(dateStr.trim());
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            try {
                // Try alternative format dd/MM/yyyy
                SimpleDateFormat altFormat = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date utilDate = altFormat.parse(dateStr.trim());
                return new Date(utilDate.getTime());
            } catch (ParseException e2) {
                System.err.println("Error parsing date: " + dateStr);
                return null;
            }
        }
    }
    
    // FIXED: Added method to parse from java.util.Date
    public static Date toSqlDate(java.util.Date date) {
        if (date == null) return null;
        return new Date(date.getTime());
    }

    public static int calculateDaysBetween(Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) return 0;
        long diff = checkOut.getTime() - checkIn.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return new Date(cal.getTimeInMillis());
    }
    
    // FIXED: Added method to get current date as String
    public static String getCurrentDateString() {
        return SQL_FORMAT.format(new java.util.Date());
    }
}