package com.linepayroll.paydoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.linepayroll.paydoctor.AnnualLeavePack.CheckAnnualLeaveActivity;
import com.linepayroll.paydoctor.AttendancePack.AttendanceStatusActivity;
import com.linepayroll.paydoctor.PayStubPack.PayStubActivity;
import com.linepayroll.paydoctor.PrivacySettingPack.PrivacySettingActivity;
import com.linepayroll.paydoctor.RuleOfEmployPack.RuleOfEmploymentActivity;

/**
 * Created by eisen on 2017-11-09.
 * 사원용 Application의 메인 화면.
 * 넘어온 개인정보들을 기준으로 출근 및 퇴근 시간 체크 등의 Rest API를 호출한다.
 */

public class MainActivity extends AppCompatActivity {

    //사원용 App 로그인 화면 뒤에 나오는 오늘의 날짜를 표시할 TextView
    TextView MainDateView;

    //날짜 표시 TextView 양옆의 날짜 이동 버튼
    ImageButton PrevDateBtn;
    ImageButton NextDateBtn;

    //근태현황, 연차확인, 급여명세서, 취업규칙열람, 개인정보 변경 버튼들의 ID 값
    static final int[] BUTTONS_ID = {
            R.id.AttendanceStatus,
            R.id.CheckAnnualLeave,
            R.id.PayStub,
            R.id.RuleOfEmployment,
            R.id.PrivacySetting,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainDateView = (TextView) findViewById(R.id.MainDateView);
        PrevDateBtn = (ImageButton) findViewById(R.id.PrevDate);
        NextDateBtn = (ImageButton) findViewById(R.id.NextDate);

        for(int BtnIndex : BUTTONS_ID) {
            Button MenuBtn = (Button) findViewById(BtnIndex);
            MenuBtn.setOnClickListener(BtnClickListener);
        }

        /**
         *  오늘 날짜 표시 TextView 아래
         *  Todo:아래에 출근시간 / 퇴근시간 Fragment 추가 바람
         *  Todo:activity_main.xml 파일에서 Fragment 관련 디자인 삽입 바람.
         *       현재 ConstraintLayout안에 LinearLayout 추가해서 구현할 것
         */


    }

    Button.OnClickListener BtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**
             *  메인화면의 각 버튼들에 대한 클릭 핸들러.
             *  각각의 Activity로 이동할 때 Intent 인스턴스화(new) 한다.
             *  Todo:Intent에 putExtra 등으로 로그인 정보 함께 이동할 것.
             */

            int id = v.getId();
            Intent i;

            if(id == BUTTONS_ID[0]) {
                //Todo:근태현황 Activity로 이동
                i = new Intent(MainActivity.this, AttendanceStatusActivity.class);
                startActivity(i);

            } else if(id == BUTTONS_ID[1]) {
                //Todo:연차확인 Activity로 이동
                i = new Intent(MainActivity.this, CheckAnnualLeaveActivity.class);
                startActivity(i);

            } else if(id == BUTTONS_ID[2]) {
                //Todo:급여명세서 Activity로 이동
                i = new Intent(MainActivity.this, PayStubActivity.class);
                startActivity(i);

            } else if(id == BUTTONS_ID[3]) {
                //Todo:취업규칙열람 Activity로 이동
                i = new Intent(MainActivity.this, RuleOfEmploymentActivity.class);
                startActivity(i);

            } else if(id == BUTTONS_ID[4]) {
                //Todo:개인정보 변경 Activity로 이동
                i = new Intent(MainActivity.this, PrivacySettingActivity.class);
                startActivity(i);

            } else {
                //지정하지 않은 다른 ID의 버튼 클릭된 경우,
                Log.i("BUTTON LISTENER", "id : " + id);
            }
        }
    };
}
