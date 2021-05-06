package com.importer.domain;

public class DateFormat {
    private boolean dateTimeFormat;
    private String datePattern;

    public DateFormat(boolean dateTimeFormat, String datePattern) {
        this.dateTimeFormat = dateTimeFormat;
        this.datePattern = datePattern;
    }

    public boolean isDateTimeFormat() {
        return dateTimeFormat;
    }

    public String getDatePattern() {
        return datePattern;
    }
}
