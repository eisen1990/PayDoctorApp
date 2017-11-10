package com.linepayroll.paydoctor.PrivacySettingPack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linepayroll.paydoctor.R;

/**
 * Created by eisen on 2017-11-10.
 * 사원용 Application의 개인정보 변경 Activity.
 * 넘어온 개인정보들을 기준으로 비밀번호 변경, 사진 표시/변경 등을 수행한다.
 */

public class PrivacySettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_setting);
    }
}
