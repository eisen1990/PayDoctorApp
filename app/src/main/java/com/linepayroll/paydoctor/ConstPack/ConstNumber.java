package com.linepayroll.paydoctor.ConstPack;

/**
 * Created by eisen on 2017-11-09.
 * 프로젝트 전역에서 사용될 상수(숫자) 값 정의
 */

public class ConstNumber {

    /**
     *  강제로 Task를 Delay 시키거나,
     *  Loop가 필요한 Data를 Test할 때 사용.
     */
    public final static int TEST_MAX_LOOP_COUNT =  10000;
    public final static int TEST_HASH_KEY = 14;

    /**
     * 출퇴근 상태
     */
    public final static int PUNCH_IN = 0;
    public final static int PUNCH_OUT = 1;

    /**
     * BLE Sacnning 관련
     */
    public final static int SCAN_PERIOD = 10000;
    public final static int PERMISSIONS_REQUEST_READ_CONTACTS = 0;

    /**
     * 달력을 위한 상수
     */
    public final static int SUNDAY        = 1;
    public final static int MONDAY        = 2;
    public final static int TUESDAY       = 3;
    public final static int WEDNSESDAY    = 4;
    public final static int THURSDAY      = 5;
    public final static int FRIDAY        = 6;
    public final static int SATURDAY      = 7;

    public final static int MAX_MONTH_CELL_NUM = 42;



}
