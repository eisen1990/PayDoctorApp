package com.linepayroll.paydoctor.MetaInfo;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by eisen on 2017-11-15.
 * LoginActivity에서 수행하는 Login Task
 */

public class LoginTask extends AsyncTask<String, Void, JSONObject> {

    public LoginTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return null;
    }
}
