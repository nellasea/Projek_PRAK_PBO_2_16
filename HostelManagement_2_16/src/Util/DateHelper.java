/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;

public class DateHelper {
    public static String formatToIndonesian(Date sqlDate) {
        if (sqlDate == null) return "-";
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        return sdf.format(sqlDate);
    }

    public static Date parseToDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(dateStr);
            return new Date(utilDate.getTime());
        } catch (Exception e) {
            return null;
        }
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
}
