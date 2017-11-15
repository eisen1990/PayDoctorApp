package com.linepayroll.paydoctor.ConstPack;

/**
 * Created by eisen on 2017-11-09.
 * 프로젝트 전역에서 사용될 상수(문자열) 값 정의
 */

public class ConstString {

    /**
     * Common String constant
     */
    public final static String NULL_STRING = "";

    /**
     * Loading Activity에 관련된 String constant
     */
    public final static String LOADING_MESSAGE_1_KR = "사원 정보를 읽어 오는 중입니다.";

    public final static String LOADING_COMPLETE_KR = "완료";
    public final static String LOADING_COMPLETE_EN = "Complete";


    /**
     * Login 관련 String
     */
    public final static String LOGIN_FAILED_KR = "로그인에 실패하였습니다.";


    /**
     * 출퇴근 Text
     */
    public final static String PUNCH_IN_TEXT_KR = "출근";
    public final static String PUNCH_IN_FAIL_KR = "출근 체크 실패";
    public final static String PUNCH_OUT_TEXT_KR = "퇴근";
    public final static String PUNCH_OUT_FAIL_KR = "퇴근 체크 실패";
    public final static String PUNCH_IN_OUT_FAIL_KR = "출/퇴근 체크 실패";



    /**
     * BLE Scanning 시, 사용될 Text
     */
    public final static String BLE_PERMISSION_DENY_KR = "출/퇴근 체크를 위한 블루투스를 사용할 수 없습니다.";

    public final static String TEST_BLE_MAC_ADDR = "BC:6A:29:AC:3C:E6";
}
