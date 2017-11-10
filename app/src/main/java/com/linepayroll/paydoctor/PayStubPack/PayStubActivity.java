package com.linepayroll.paydoctor.PayStubPack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linepayroll.paydoctor.R;

/**
 * Created by eisen on 2017-11-09.
 * 사원용 Application의 급여명세서 Activity.
 */

public class PayStubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_stub);
    }
}
