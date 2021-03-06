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
import com.linepayroll.paydoctor.ConstPack.ConstURL;
import com.linepayroll.paydoctor.PayStubPack.PayStubActivity;
import com.linepayroll.paydoctor.PrivacySettingPack.PrivacySettingActivity;
import com.linepayroll.paydoctor.RuleOfEmployPack.RuleOfEmploymentActivity;
import com.linepayroll.paydoctor.Utils.TimeCompo;

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
    private int PunchInOutFlag = -1;
    private int ScanFlag = -1;

    //사용자 정보 변수
    private int USER_ID_CODE;
    private String CHECK_PUNCH_IN;
    private String CHECK_PUNCH_OUT;


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

        Intent intent = getIntent();
        USER_ID_CODE = intent.getExtras().getInt("USER_ID_CODE");
        CHECK_PUNCH_IN = intent.getExtras().getString("PUNCH_IN");
        CHECK_PUNCH_OUT = intent.getExtras().getString("PUNCH_OUT");


        /**
         * Main Activity 상단의 날짜 TextView와
         * 양 옆의 날짜 이동 Button
         */
        MainDateView = (TextView) findViewById(R.id.MainDateView);
        PrevDateBtn = (ImageButton) findViewById(R.id.PrevDate);
        NextDateBtn = (ImageButton) findViewById(R.id.NextDate);


        for (int BtnIndex : BUTTONS_ID) {
            Button MenuBtn = (Button) findViewById(BtnIndex);
            MenuBtn.setOnClickListener(BtnClickListener);
        }

        /**
         * Bluetooth BLE 탐색을 위한 설정
         * Permission 및 기타
         */

        BleHandler = new Handler();
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

        /**
         * 출퇴근 ImageButton
         */
        PunchInBtn = (ImageButton) findViewById(R.id.PunchInBtn);
        PunchOutBtn = (ImageButton) findViewById(R.id.PunchOutBtn);
        PunchInBtn.setOnClickListener(ImgBtnClickListener);
        PunchOutBtn.setOnClickListener(ImgBtnClickListener);

        if(CHECK_PUNCH_IN != null) PunchInBtn.setImageDrawable(getResources().getDrawable(R.drawable.easw_rev));
        if(CHECK_PUNCH_OUT != null) PunchOutBtn.setImageDrawable(getResources().getDrawable(R.drawable.easw_rev));


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

    Button.OnClickListener BtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /**
             *  메인화면의 각 버튼들에 대한 클릭 핸들러.
             *  각각의 Activity로 이동할 때 Intent 인스턴스화(new) 한다.
             *  Todo:Intent에 putExtra 등으로 로그인 정보 함께 이동할 것.
             */

            int id = v.getId();
            Intent intent;

            if (id == BUTTONS_ID[0]) {
                //Todo:근태현황 Activity로 이동
                intent = new Intent(MainActivity.this, AttendanceStatusActivity.class);
                intent.putExtra("USER_ID_CODE", USER_ID_CODE);
                startActivity(intent);

            } else if (id == BUTTONS_ID[1]) {
                //Todo:연차확인 Activity로 이동
                intent = new Intent(MainActivity.this, CheckAnnualLeaveActivity.class);
                intent.putExtra("USER_ID_CODE", USER_ID_CODE);
                startActivity(intent);

            } else if (id == BUTTONS_ID[2]) {
                //Todo:급여명세서 Activity로 이동
                intent = new Intent(MainActivity.this, PayStubActivity.class);
                intent.putExtra("USER_ID_CODE", USER_ID_CODE);
                startActivity(intent);

            } else if (id == BUTTONS_ID[3]) {
                //Todo:취업규칙열람 Activity로 이동
                intent = new Intent(MainActivity.this, RuleOfEmploymentActivity.class);
                startActivity(intent);

            } else if (id == BUTTONS_ID[4]) {
                //Todo:개인정보 변경 Activity로 이동
                intent = new Intent(MainActivity.this, PrivacySettingActivity.class);
                intent.putExtra("USER_ID_CODE", USER_ID_CODE);
                startActivity(intent);

            } else {
                //지정하지 않은 다른 ID의 버튼 클릭된 경우,
                Log.i("BUTTON LISTENER", "id : " + id);
            }
        }
    };

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
                scanLeDevice(true, ConstNumber.PUNCH_IN);
            } else if (id == R.id.PunchOutBtn) {
                //Todo: 퇴근 버튼 클릭 시
                scanLeDevice(true, ConstNumber.PUNCH_OUT);
            } else {
                //Todo: 에러 발생
                Log.d("MainActivity", "ImageButton 핸들러 에러");
            }
        }
    };

    private BluetoothAdapter.LeScanCallback BleScanCallBack = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (device.toString().compareTo(ConstString.TEST_BLE_MAC_ADDR) == 0 && ScanFlag == 0) {
                        //Bluetooth onLeScan은 stopLeScan이 실행되기 전까지 지속적으로 수행하므로, if로 한번 스캔 당 한번만 실행하도록 한다.
                        ScanFlag = 1;
                        /*
                        Toast.makeText(MainActivity.this,
                                ConstString.PUNCH_IN_TEXT_KR + " : " + device.toString() + ", " + (new Date()).toString(),
                                Toast.LENGTH_SHORT).show();
                        */

                        //postDelayed 이전에 BLE 탐색 완료.
                        if (PunchInOutFlag == ConstNumber.PUNCH_IN) {
                            //Todo: Punch In을 위한 Rest API 호출
                            new PunchInOutTask(MainActivity.this, PunchInBtn).execute(ConstURL.PUNCHIN_URL, String.valueOf(USER_ID_CODE), TimeCompo.getCurrentTime());

                        } else if (PunchInOutFlag == ConstNumber.PUNCH_OUT) {
                            //Todo: Punch Out을 위한 Rest API 호출
                            new PunchInOutTask(MainActivity.this, PunchOutBtn).execute(ConstURL.PUNCHOUT_URL, String.valueOf(USER_ID_CODE), TimeCompo.getCurrentTime());

                        } else {
                            //Todo: Error status
                            Log.d("MainActivity", "Punch In/Out 을 위한 수행이 아니다.");
                        }
                    }
                }
            });
        }
    };

    private void scanLeDevice(final boolean Enable, final int PunchFlag) {
        PunchInOutFlag = PunchFlag;
        ScanFlag = 0;

        if (mBluetoothAdapter == null) {
            Log.d("MainActivity", "Bluetooth 지원 안함");
            return;
        } else {
            /**
             * Bluetooth를 지원하고,
             * Bluetooth가 꺼져있다면, 환경설정에서 켜준다.
             */
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }
        }

        if (Enable) {
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

}
