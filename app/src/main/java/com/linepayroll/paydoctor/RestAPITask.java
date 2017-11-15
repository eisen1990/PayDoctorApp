package com.linepayroll.paydoctor;

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
 * Created by eisen on 2017-11-13.
 *
 * FIXME : 해당 클래스는 RestAPI 호출을 위한 Sample Source Code이므로,
 * FIXME:  각각의 Activity들에서 Rest API 호출이 필요할 경우 아래 코드를 참고하여
 * FIXME : Parameter들에 맞게 작성할 것.
 * Rest API는 모두 JSON 방식으로 Return 하므로, AsyncTask<String, Void, JSONObject>의 JSONObject는 기본적으로 동일하다.
 * String 인자는 execute()의 Parameter로, 다수의 String type veriable이 올 수 있다. 그리고 execute()의 Parameter는
 * doInBackground(String... params)에 전달되며, execute(첫 번째, 두 번째, n번 째) Argument는 doInBackground의 params에서 params[0], params[1], params[n-1]
 *
 * Sample Code:
 *      ... source code ...
 *
 *      JSONObject ResultObject = new RestAPITask().execute(SOME_URL_STRING_VALUE);
 *
 *      ... source code ...
 */

public class RestAPITask extends AsyncTask<String, Void, JSONObject> {

    public RestAPITask() {
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
