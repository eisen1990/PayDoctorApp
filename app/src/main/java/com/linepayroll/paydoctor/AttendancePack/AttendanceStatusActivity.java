package com.linepayroll.paydoctor.AttendancePack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linepayroll.paydoctor.R;

/**
 * Created by eisen on 2017-11-09.
 * 사원용 Application의 근태현황 Activity.
 */

public class AttendanceStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_status);
    }
}