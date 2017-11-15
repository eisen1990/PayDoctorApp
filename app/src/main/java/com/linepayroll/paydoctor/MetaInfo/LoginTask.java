package com.linepayroll.paydoctor.MetaInfo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.linepayroll.paydoctor.ConstPack.ConstNumber;
import com.linepayroll.paydoctor.ConstPack.ConstString;
import com.linepayroll.paydoctor.ConstPack.StatusCode;

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
 * Created by eisen on 2017-11-15.
 * LoginActivity에서 수행하는 Login Task
 */

public class LoginTask extends AsyncTask<String, Void, JSONObject> {

    Context Parent;

    public LoginTask(Context context) {
        this.Parent = context;
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
                //Todo: intent 넘기기
                Integer USER_ID_CODE = BODY.getInt("USER_ID_CODE");

                Intent intent = new Intent(Parent, LoadingActivity.class);
                intent.putExtra("USER_ID_CODE", USER_ID_CODE);
                Parent.startActivity(intent);
            } else {
                //Todo: Login 실패
                Toast.makeText(Parent, ConstString.LOGIN_FAILED_KR, Toast.LENGTH_SHORT).show();
                Log.d("LoginTask", STATUS_CODE+"");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject result = null;

        try {
            /**
             * params[0] : URL,
             * params[1] : CompanyId,
             * params[2] : UserId,
             * params[3] : UserPasswd
             */
            String url = params[0];
            String Body = "COMPANY_PID=" + params[1] +
                          "&USER_ID=" + params[2] +
                          "&USER_PASSWD=" + params[3];

            Log.d("doInBackground", Body);

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
