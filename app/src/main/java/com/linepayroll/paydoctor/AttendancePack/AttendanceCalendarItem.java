package com.linepayroll.paydoctor.AttendancePack;

/**
 * Created by eisen on 2017-11-15.
 */

public class AttendanceCalendarItem {

    private String Day;
    private boolean inMonth;

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public boolean isInMonth() {
        return inMonth;
    }

    public void setInMonth(boolean inMonth) {
        this.inMonth = inMonth;
    }
}
