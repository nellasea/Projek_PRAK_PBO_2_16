/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberHelper {
    private static final NumberFormat IDR_FORMAT = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public static String formatRupiah(double amount) {
        return IDR_FORMAT.format(amount);
    }

    public static String formatRupiah(long amount) {
        return IDR_FORMAT.format(amount);
    }

    public static double parseRupiah(String rupiah) {
        try {
            return IDR_FORMAT.parse(rupiah).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }
}