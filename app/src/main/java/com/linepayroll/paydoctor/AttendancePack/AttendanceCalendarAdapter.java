package com.linepayroll.paydoctor.AttendancePack;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

        //캐시된 View 없을 경우 새로 생성하고 Viewholder 세팅
        if(convertView == null) {
            convertView = Inflater.inflate(Layout, null);

            if(position % 7 == 6) {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP() + getRestCellWidthDP(), getCellHeightDP()));
            } else {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP(), getCellHeightDP()));
            }

            ViewHolder = new MonthViewHolder();

            ViewHolder.llBackground = (LinearLayout) convertView.findViewById(R.id.CalendarCell);
            ViewHolder.tvDay = (TextView) convertView.findViewById(R.id.DayCellTv);

            // ViewHolder를 세팅한다.
            convertView.setTag(ViewHolder);

        } else {
            ViewHolder = (MonthViewHolder) convertView.getTag();
        }

        if(item != null) {
            ViewHolder.tvDay.setText(item.getDay());
            if(item.isInMonth()) {
                if (position % 7 == 0) {
                    ViewHolder.tvDay.setTextColor(Color.RED);
                } else if (position % 7 == 6) {
                    ViewHolder.tvDay.setTextColor(Color.BLUE);
                } else {
                    ViewHolder.tvDay.setTextColor(Color.BLACK);
                }
            } else {
                ViewHolder.tvDay.setTextColor(Color.GRAY);
            }
        }

        return convertView;
    }

    protected class MonthViewHolder {
        protected LinearLayout llBackground;
        protected TextView tvDay;
    }

    private int getCellWidthDP()
    {
//      int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellWidth = 480/7;

        return cellWidth;
    }

    private int getRestCellWidthDP()
    {
//      int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellWidth = 480%7;

        return cellWidth;
    }

    private int getCellHeightDP()
    {
//      int height = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellHeight = 480/6;

        return cellHeight;
    }

}
