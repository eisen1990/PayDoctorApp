package com.linepayroll.paydoctor;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.linepayroll.paydoctor.AnnualLeavePack.CheckAnnualLeaveActivity;
import com.linepayroll.paydoctor.AttendancePack.AttendanceStatusActivity;
import com.linepayroll.paydoctor.ConstPack.ConstNumber;
import com.linepayroll.paydoctor.ConstPack.ConstString;
import com.linepayroll.paydoctor.PayStubPack.PayStubActivity;
import com.linepayroll.paydoctor.PrivacySettingPack.PrivacySettingActivity;
import com.linepayroll.paydoctor.RuleOfEmployPack.RuleOfEmploymentActivity;

import java.util.Date;

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

    //출/퇴근 표시 ImageButton
    ImageButton PunchInBtn;
    ImageButton PunchOutBtn;

    //Bluetooth를 위한 클래스
    private BluetoothAdapter mBluetoothAdapter;
    private Handler BleHandler;

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

        /**
         * Main Activity 상단의 날짜 TextView와
         * 양 옆의 날짜 이동 Button
         */
        MainDateView = (TextView) findViewById(R.id.MainDateView);
        PrevDateBtn = (ImageButton) findViewById(R.id.PrevDate);
        NextDateBtn = (ImageButton) findViewById(R.id.NextDate);


        /**
         * 출퇴근 ImageButton
         */
        PunchInBtn = (ImageButton) findViewById(R.id.PunchInBtn);
        PunchOutBtn = (ImageButton) findViewById(R.id.PunchOutBtn);


        for (int BtnIndex : BUTTONS_ID) {
            Button MenuBtn = (Button) findViewById(BtnIndex);
            MenuBtn.setOnClickListener(BtnClickListener);
        }

        int PermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (PermissionCheck != PackageManager.PERMISSION_GRANTED) {
            /**
             * Bluetooth 권한이 없을 경우,
             * 권한 부여 요청
             */
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ConstNumber.PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {
            /**
             * Bluetooth 권한이 있을 경우,
             * Nothing...
             */
        }

        final BluetoothManager mBluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, ConstString.BLE_PERMISSION_DENY_KR, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        PunchInBtn.setOnClickListener(ImgBtnClickListener);
        PunchOutBtn.setOnClickListener(ImgBtnClickListener);


        /**
         *  오늘 날짜 표시 TextView 아래
         *  Todo:아래에 출근시간 / 퇴근시간 Fragment 추가 바람
         *  Todo:activity_main.xml 파일에서 Fragment 관련 디자인 삽입 바람.
         *       현재 ConstraintLayout안에 LinearLayout 추가해서 구현할 것
         */


    }

    @Override
    public void onRequestPermissionsResult(int RequestCode, @NonNull String[] permissions, @NonNull int[] GrantResults) {

        /**
         * ActivityCompat.requestPermissions 호출 뒤에
         * 권한허가 결과 가져오기
         */

        switch (RequestCode) {
            case ConstNumber.PERMISSIONS_REQUEST_READ_CONTACTS:
                if (GrantResults.length > 0
                        && GrantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한 허가
                } else {
                    //권한 거부
                    finish();
                }
                return;
        }
    }

    ImageButton.OnClickListener ImgBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**
             * 출/퇴근 ImageButton 에 대한 핸들러.
             * BLE Scanning을 시작한다.
             */

            int id = v.getId();

            if (id == R.id.PunchInBtn) {
                //Todo: 출근 버튼 클릭 시
                scanLeDevice(true);
            } else if(id == R.id.PunchOutBtn) {
                //Todo: 퇴근 버튼 클릭 시

            } else {
                //Todo: 에러 발생
                Log.d("MainActivity", "ImageButton 핸들러 에러");
            }
        }
    };

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

            if (id == BUTTONS_ID[0]) {
                //Todo:근태현황 Activity로 이동
                i = new Intent(MainActivity.this, AttendanceStatusActivity.class);
                startActivity(i);

            } else if (id == BUTTONS_ID[1]) {
                //Todo:연차확인 Activity로 이동
                i = new Intent(MainActivity.this, CheckAnnualLeaveActivity.class);
                startActivity(i);

            } else if (id == BUTTONS_ID[2]) {
                //Todo:급여명세서 Activity로 이동
                i = new Intent(MainActivity.this, PayStubActivity.class);
                startActivity(i);

            } else if (id == BUTTONS_ID[3]) {
                //Todo:취업규칙열람 Activity로 이동
                i = new Intent(MainActivity.this, RuleOfEmploymentActivity.class);
                startActivity(i);

            } else if (id == BUTTONS_ID[4]) {
                //Todo:개인정보 변경 Activity로 이동
                i = new Intent(MainActivity.this, PrivacySettingActivity.class);
                startActivity(i);

            } else {
                //지정하지 않은 다른 ID의 버튼 클릭된 경우,
                Log.i("BUTTON LISTENER", "id : " + id);
            }
        }
    };

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            BleHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothAdapter.stopLeScan(BleScanCallBack);
                    invalidateOptionsMenu();
                }
            }, ConstNumber.SCAN_PERIOD);

            mBluetoothAdapter.startLeScan(BleScanCallBack);
        } else {
            mBluetoothAdapter.stopLeScan(BleScanCallBack);
        }
        invalidateOptionsMenu();
    }

    private BluetoothAdapter.LeScanCallback BleScanCallBack = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(device.toString().compareTo(ConstString.TEST_BLE_MAC_ADDR) == 0) {
                        Toast.makeText(MainActivity.this,
                                ConstString.PUNCH_IN_KR + " : " + device.toString() + ", " + (new Date()).toString(),
                                Toast.LENGTH_SHORT).show();
                        //Todo:postDelayed 이전에 BLE 탐색 완료.
                    }
                }
            });
        }
    };
}
