package io.ankara.ui.vaadin.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

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

    public static BigDecimal calculateDiscount(BigDecimal taxedTotal, BigDecimal discountPercentage) {
        return taxedTotal.multiply(discountPercentage).divide(new BigDecimal("100"));
    }

    public static BigDecimal calculateTax(BigDecimal amount, BigDecimal taxPercentage) {
        return amount.multiply(taxPercentage).divide(new BigDecimal("100"));
    }
}
