package com.linepayroll.paydoctor.ConstPack;

/**
 * Created by eisen on 2017-11-10.
 * 프로젝트 전역에서 사용될 상수(URL) 값 정의
 */

public class ConstURL {

    /**
     * Testing Rest API 서버
     * EASW 연구원 심철 개인 서버 사용
     */
    public final static String TEST_BASE_URL = "http://203.255.92.139:8080/PayDoctorAPI/rest";

    public final static String TEST_COMMON_URL = TEST_BASE_URL + "/Common";
    public final static String TEST_LOGIN_URL = TEST_COMMON_URL + "/Login";


    public final static String TEST_WORKER_URL = TEST_BASE_URL + "/Worker";
    public final static String TEST_PUNCHIN_URL = TEST_WORKER_URL + "/PunchIn";
    public final static String TEST_PUNCHOUT_URL = TEST_WORKER_URL + "/PunchOut";


    /**
     * 실제 Service 단계에서 Hosting 받을 URL
     */
    public final static String BASE_URL = "http://203.255.92.139:8080/PayDoctorAPI/rest";

    public final static String COMMON_URL = BASE_URL + "/Common";
    public final static String LOGIN_URL = COMMON_URL + "/Login";


    public final static String WORKER_URL = BASE_URL + "/Worker";
    public final static String PUNCHIN_URL = WORKER_URL + "/PunchIn";
    public final static String PUNCHOUT_URL = WORKER_URL + "/PunchOut";
    public final static String IS_PUNCHINOUT_URL = WORKER_URL + "/isPunchInOut";
    public final static String ATTENDANCE_STATUS = WORKER_URL + "/getAttendanceStatus";


}
