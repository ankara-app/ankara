package io.ankara.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/10/17 8:30 PM
 */
public class DateUtils {
    public static String formatDateTime(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}
