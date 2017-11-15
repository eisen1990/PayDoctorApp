package com.linepayroll.paydoctor.AttendancePack;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by eisen on 2017-11-15.
 *
 *
 * Sample Code:
 *      ... source code ...
 *
 *      JSONObject ResultObject = new RestAPITask().execute(SOME_URL_STRING_VALUE);
 *
 *      ... source code ...
 */

public class AttendanceStatusAPITask extends AsyncTask<String, Void, JSONObject> {

    public AttendanceStatusAPITask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject aJSONObject) {
        super.onPostExecute(aJSONObject);
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        JSONObject result = null;

        try {
            String url = params[0];

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

            StringBuffer Buffer = new StringBuffer();
            OutputStreamWriter OutStream = new OutputStreamWriter(Conn.getOutputStream(), "UTF-8");
            PrintWriter Writer = new PrintWriter(OutStream);
            Writer.write(Buffer.toString());
            Writer.flush();

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
