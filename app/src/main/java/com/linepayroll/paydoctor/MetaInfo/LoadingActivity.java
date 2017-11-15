package com.linepayroll.paydoctor.MetaInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linepayroll.paydoctor.ConstPack.ConstURL;
import com.linepayroll.paydoctor.R;


/**
 * Created by eisen on 2017-11-09.
 * Application 실행 후 로그인 뒤 개인정보 로딩 Activity.
 * doInBackground에서 Rest API 호출 후 정보를 MainActivity로 넘긴다.
 */

public class LoadingActivity extends AppCompatActivity {

    ProgressBar Loading;    //로딩 화면 원형 프로그레스바
    TextView LoadingMessage;

    private int USER_ID_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Intent intent = getIntent();
        USER_ID_CODE = intent.getExtras().getInt("USER_ID_CODE");

        Loading = (ProgressBar) findViewById(R.id.LoadingProgressBar);
        Loading.setVisibility(View.GONE);

        LoadingMessage = (TextView) findViewById(R.id.LoadingText);

        /**
         * RestAPI Package의 LoadingTask를 통해 API Connection
         */
        new LoadingTask(this, Loading, LoadingMessage).execute(ConstURL.IS_PUNCHINOUT_URL, String.valueOf(USER_ID_CODE));
    }
}
