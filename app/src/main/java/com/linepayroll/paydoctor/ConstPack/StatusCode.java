package com.linepayroll.paydoctor.ConstPack;

/**
 * Created by eisen on 2017-11-15.
 */

public class StatusCode {


    /**
     * REST API
     * STATUS CODE
     */
    public final static int SUCCESS_CODE = 101;
    public final static String SUCCESS_MSG = "SUCCESS";

    public final static int PARAMS_ERR_CODE = 102;
    public final static String PARAMS_ERR_MSG = "PARAMETER ERROR";


    public final static int LOGIN_SUCCESS_CODE = 201;
    public final static String LOGIN_SUCCESS_MSG = "LOGIN SUCCESS";

    public final static int PASSWD_ERR_CODE = 202;
    public final static String PASSWD_ERR_MSG = "PASSWD ERROR";

    public final static int USER_DO_NOT_EXIST_CODE = 203;
    public final static String USER_DO_NOT_EXIST_MSG = "THE USER DOES NOT EXIST";

    public final static int ALREADY_PUNCHIN_CODE = 301;
    public final static String ALREADY_PUNCHIN_MSG = "ALREADY PUNCH IN TODAY";

    public final static int ALREADY_PUNCHOUT_CODE = 302;
    public final static String ALREADY_PUNCHOUT_MSG = "ALREADY PUNCH OUT TODAY";



    public final static int DB_EXCEPTION_CODE = 901;
    public final static String DB_EXCEPTION_MSG = "DATABASE EXCEPTION";

}
