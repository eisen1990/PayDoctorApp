package com.linepayroll.paydoctor.AttendancePack;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linepayroll.paydoctor.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by eisen on 2017-11-15.
 * AttendanceStatus Activity에 띄워줄 달력 Adapter
 */

public class AttendanceCalendarAdapter extends BaseAdapter {

    private ArrayList<AttendanceCalendarItem> Items;
    private LayoutInflater Inflater;
    private int Layout;

    private java.util.Calendar Month;
    public GregorianCalendar pMonth; // calendar instance for previous month

    public AttendanceCalendarAdapter(Context context, int layout, ArrayList<AttendanceCalendarItem> items) {
        this.Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.Layout = layout;

        this.Items = items;
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AttendanceCalendarItem item = Items.get(position);

        MonthViewHolder ViewHolder;

        if(convertView == null) {

        } else {
            ViewHolder = (MonthViewHolder) convertView.getTag();
        }

        return null;
    }

    protected class MonthViewHolder {
        protected LinearLayout llBackground;
        protected TextView tvDay;
    }
}
