package com.linepayroll.paydoctor.AttendancePack;

/**
 * Created by eisen on 2017-11-15.
 */

public class AttendanceCalendarItem {

    private String Day;
    private boolean inMonth;
    private boolean Punch;

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

    public boolean isPunch() {
        return Punch;
    }

    public void setPunch(boolean punch) {
        Punch = punch;
    }
}
