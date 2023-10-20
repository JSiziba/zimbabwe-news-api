package com.siziba.zim_news.zim_news.library;

import lombok.Data;

@Data
public class MillisecondsUtility {
    public static final long ONE_DAY_IN_MILLISECONDS = 60 * 60 * 24 * 1000;
    public static final long TEN_HOURS_IN_MILLISECONDS = 60 * 60 * 10 * 1000;
    public static final long ONE_HOUR_IN_MILLISECONDS = 60 * 60 * 1000;
    public static final long ONE_MINUTE_IN_MILLISECONDS = 60 * 1000;
    public static final long ONE_MONTH_IN_MILLISECONDS = 60L * 60 * 24 * 30 * 1000;
    public static final long ONE_YEAR_IN_MILLISECONDS = 60L * 60 * 24 * 365 * 1000;
    public static final long ONE_WEEK_IN_MILLISECONDS = 60L * 60 * 24 * 7 * 1000;
    public static final long SIX_MONTHS_IN_MILLISECONDS = 60L * 60 * 24 * 30 * 6 * 1000;
}
