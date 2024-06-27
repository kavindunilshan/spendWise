package com.app.spendWise.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class CommonUtils {
    public static HashMap<String, Double> generateMonthMap() {
        HashMap<String, Double> monthMap = new HashMap<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 5; i >= 0; i--) {
            LocalDate date = currentDate.minusMonths(i);
            String monthLabel = date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            double value = 0.0;
            monthMap.put(monthLabel, value);
        }

        return monthMap;
    }
}
