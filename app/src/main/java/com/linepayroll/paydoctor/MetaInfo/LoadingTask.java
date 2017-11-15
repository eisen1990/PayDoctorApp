package com.linepayroll.paydoctor.MetaInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linepayroll.paydoctor.ConstPack.ConstNumber;
import com.linepayroll.paydoctor.ConstPack.ConstString;
import com.linepayroll.paydoctor.ConstPack.StatusCode;
import com.linepayroll.paydoctor.MainActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by eisen on 2017-11-10.
 * LoadingActivity에서 수행하는 Task
 * Login한 계정 정보를 통해 사용자 정보를 받아온다.
 */

public class LoadingTask extends AsyncTask<String, Integer, JSONObject> {
    /**
     * 1번 Parameter Void : excute() 실행 시 doInBackground로 넘겨줄 파라미터
     * 2번 Parameter Integer : publishProgress()로 넘겨서 onProgressUpdate로 넘겨줄 파라미터
     * 3번 Parameter Void : doInBackground() 종료 시 리턴될 타입, onPostExecute()의 파라미터
     */


    /**
     * 해당 Task를 실행한 부모 context를 받아와서
     * UI를 Handling한다.
     */
    private Context Parent;
    private ProgressBar Loading;
    private TextView LoadingMessage;

    private int USER_ID_CODE;

    public LoadingTask(Context context, ProgressBar Loading, TextView LoadingMessage) {
        //Todo Some initialize

        this.Parent = context;
        this.Loading = Loading;
        this.LoadingMessage = LoadingMessage;
    }

    @Override
    protected void onPreExecute() {
        //로딩 시작 전
        super.onPreExecute();
        Loading.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        //로딩 완료
        super.onPostExecute(jsonObject);
        Loading.setVisibility(View.GONE);
        LoadingMessage.setText(ConstString.LOADING_COMPLETE_EN);

        Intent intent = new Intent(Parent, MainActivity.class);
        intent.putExtra("USER_ID_CODE", USER_ID_CODE);

        try {
            JSONObject HEAD = jsonObject.getJSONObject("HEAD");
            JSONObject BODY = jsonObject.getJSONObject("BODY");

            Integer STATUS_CODE = HEAD.getInt("STATUS_CODE");

            if ((int) STATUS_CODE == StatusCode.SUCCESS_CODE) {
                String PunchIn = BODY.getString("PUNCH_IN");
                String PunchOut = BODY.getString("PUNCH_OUT");
                if (!(PunchIn == null || PunchIn.length() == 0 || PunchIn.equals("null"))) {
                    intent.putExtra("PUNCH_IN", PunchIn);
                }
                if (!(PunchOut == null || PunchOut.length() == 0 || PunchOut.equals("null"))) {
                    intent.putExtra("PUNCH_OUT", PunchOut);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * intent에 User에 대한 정보를 실어서
         * 다음 Acitivity에 전달할 것
         */
        Parent.startActivity(intent);
        ((Activity) Parent).finish();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        //로딩 중 프로그레스바 갱신
        super.onProgressUpdate(progress);
        //Log.d("onProgressUpdate", "progress : " + progress[0]);
        Loading.setProgress(progress[0]);

        if (progress[0] % ConstNumber.TEST_HASH_KEY == 0) {
            LoadingMessage.setText(ConstString.LOADING_MESSAGE_1_KR);
        } else {
            LoadingMessage.setText(ConstString.NULL_STRING);
        }
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        //로딩 중 데이터 송수신 또는 적재
        USER_ID_CODE = Integer.parseInt(params[1]);

        /**
         * FIXME: Rest API 호출하여 사용자 정보 받아오도록 수정
         * 아래는 현재 Loading Testing..
         */

        //publicshProgress()로 넘기면 onProgressUpdate 실행됨됨
        for (int i = 0; i < ConstNumber.TEST_MAX_LOOP_COUNT; i++)
            publishProgress(new Integer(i));

        JSONObject result = null;

        try {
            String url = params[0];
            String Body = "USER_ID_CODE=" + params[1];

            URL URLObj = new URL(url);


            HttpURLConnection Conn = (HttpURLConnection) URLObj.openConnection();

            Conn.setReadTimeout(100000);
            Conn.setConnectTimeout(15000);

            /**
             * 1. Todo:POST Method 설정
             * 2. Todo:Accept-Charset 설정
             * 3. Todo:Content-Type 설정
             */
            Conn.setRequestMethod("POST");
            Conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            Conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            Conn.setDoInput(true);
            Conn.setDoOutput(true);

            OutputStream OutStream = Conn.getOutputStream();
            OutStream.write(Body.getBytes("utf-8"));

            InputStreamReader InputStream = new InputStreamReader(Conn.getInputStream(), "UTF-8");
            BufferedReader Reader = new BufferedReader(InputStream);
            StringBuilder Builder = new StringBuilder();
            String ResultStr;

            while ((ResultStr = Reader.readLine()) != null) {
                Builder.append(ResultStr + "\n");
            }

            result = new JSONObject(Builder.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        } finally {
            //Todo finally..
        }

        return result;
    }
}
