package com.linepayroll.paydoctor.AttendancePack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.linepayroll.paydoctor.ConstPack.ConstNumber;
import com.linepayroll.paydoctor.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by eisen on 2017-11-09.
 * 사원용 Application의 근태현황 Activity.
 */

public class AttendanceStatusActivity extends AppCompatActivity {

    //FIXME: 추후에 ImageButton으로 교체할 것
    Button PrevMonthBtn;
    Button NextMonthBtn;

    TextView MonthTitle;

    GridView CalenderView;

    Calendar PrevMonthCalendar;
    Calendar ThisMonthCalendar;
    Calendar NextMonthCalendar;

    private AttendanceCalendarAdapter mCalendarAdapter;
    private ArrayList<AttendanceCalendarItem> DayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_status);

        PrevMonthBtn = (Button) findViewById(R.id.PrevMonth);
        NextMonthBtn = (Button) findViewById(R.id.NextMonth);
        PrevMonthBtn.setOnClickListener(ImgBtnClickListener);
        NextMonthBtn.setOnClickListener(ImgBtnClickListener);

        MonthTitle = (TextView) findViewById(R.id.AttendanceMonth);

        CalenderView = (GridView) findViewById(R.id.CalendarView);

        DayList = new ArrayList<AttendanceCalendarItem>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThisMonthCalendar = Calendar.getInstance();
        ThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(ThisMonthCalendar);
    }

    /**
     * 현재 달력 세팅
     *
     * @param calendar 달력에 보여지는 이번 달의 Calendar 객체
     */
    private void getCalendar(Calendar calendar) {
        int PrevMonthEndDay;
        int DayOfMonth;
        int ThisMonthLastDay;

        DayList.clear();

        //현재 요일과, 현재 월의 시작 날짜를 구한다.
        DayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        ThisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 이번 달 시작 일의 요일을 구한다. 시작 일이 일요일인 경우 인덱스를 1(일요일)에서 8(다음 주 일요일)로 바꾼다.
        if (DayOfMonth == ConstNumber.SUNDAY) {
            DayOfMonth += 7;
        }

        //지난 달의 마지막 일
        calendar.add(Calendar.MONTH, -1);
        PrevMonthEndDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //다시 이번 달을 기준으로
        calendar.add(Calendar.MONTH, 1);

        MonthTitle.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1));

        AttendanceCalendarItem Day;

        for (int i = (PrevMonthEndDay - (DayOfMonth - 2)); i <= PrevMonthEndDay; i++) {
            Day = new AttendanceCalendarItem();
            Day.setDay(Integer.toString(i));
            Day.setInMonth(false);

            DayList.add(Day);
        }

        for (int i = 1; i <= ThisMonthLastDay; i++) {
            //해당 월의 1일부터 마지막 일 까지 DayList에 넣는다.
            Day = new AttendanceCalendarItem();
            Day.setDay(Integer.toString(i));
            Day.setInMonth(true);

            DayList.add(Day);
        }

        initCalendarAdapter();
    }

    /**
     * 지난달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return PrevMonthCalendar
     */
    private Calendar getPrevMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, -1);
        return calendar;
    }

    /**
     * 다음달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return NextMonthCalendar
     */
    private Calendar getNextMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, +1);
        return calendar;
    }


    private void initCalendarAdapter() {
        mCalendarAdapter = new AttendanceCalendarAdapter(this, R.layout.calendar_item_bg, DayList);
        CalenderView.setAdapter(mCalendarAdapter);
    }

    ImageButton.OnClickListener ImgBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.PrevMonth) {
                //이전 달 호출
                ThisMonthCalendar = getPrevMonth(ThisMonthCalendar);
                getCalendar(ThisMonthCalendar);
            } else if (id == R.id.NextMonth) {
                //다음 달 호출
                ThisMonthCalendar = getNextMonth(ThisMonthCalendar);
                getCalendar(ThisMonthCalendar);
            } else {
                //잘못 선택된 달력

            }
        }
    };
}
