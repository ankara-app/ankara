package io.ankara.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 8/17/16
 */
public class NumberUtils {
    public static BigDecimal getBigDecimal(String text) {
        if (StringUtils.isEmpty(text) || !StringUtils.isNumericSpace(text))
            return BigDecimal.ZERO;
        else
            return new BigDecimal(text.trim().replaceAll(" ", ""));
    }

    public static String formatMoney(BigDecimal amount, String currency) {
        return String.format("%,.2f "+currency,amount.setScale(2, RoundingMode.HALF_DOWN));
    }
}
