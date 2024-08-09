package com.davidefella.infoquiz.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalRounder {

    private DecimalRounder(){}

    public static double roundToTwoDecimals(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
