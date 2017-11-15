package com.linepayroll.paydoctor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.linepayroll.paydoctor.ConstPack.ConstString;
import com.linepayroll.paydoctor.ConstPack.StatusCode;
import com.linepayroll.paydoctor.MetaInfo.LoadingActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by eisen on 2017-11-13.
 * Main Activity의 출퇴근 관련 Task
 */

public class PunchInOutTask extends AsyncTask<String, Void, JSONObject> {

    private Context Parent;
    private ImageButton ImgBtn;

    public PunchInOutTask(Context context, ImageButton ImgBtn) {
        this.Parent = context;
        this.ImgBtn = ImgBtn;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        try {
            JSONObject HEAD = jsonObject.getJSONObject("HEAD");
            JSONObject BODY = jsonObject.getJSONObject("BODY");
            Integer STATUS_CODE = HEAD.getInt("STATUS_CODE");

            if((int)STATUS_CODE == StatusCode.SUCCESS_CODE) {
                //Todo: 출근 혹은 퇴근 성공했을 경우, Image 바꾸기로 우선 작업진행.
                ImgBtn.setImageDrawable(Parent.getResources().getDrawable(R.drawable.easw_rev));

            } else {
                //Todo: 출퇴근 실패
                Toast.makeText(Parent, ConstString.PUNCH_IN_OUT_FAIL_KR, Toast.LENGTH_SHORT).show();
                Log.d("PunchIn", STATUS_CODE+"");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        JSONObject result = null;

        try {
            String url = params[0];
            String Body = "USER_ID_CODE=" + params[1] +
                          "&PUNCH_DATE=" + params[2];

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
