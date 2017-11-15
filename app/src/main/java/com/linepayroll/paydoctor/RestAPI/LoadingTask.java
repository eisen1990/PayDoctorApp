package com.linepayroll.paydoctor.RestAPI;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linepayroll.paydoctor.ConstPack.ConstNumber;
import com.linepayroll.paydoctor.ConstPack.ConstString;
import com.linepayroll.paydoctor.MainActivity;

/**
 * Created by eisen on 2017-11-10.
 * LoadingActivity에서 수행하는 Task
 * Login한 계정 정보를 통해 사용자 정보를 받아온다.
 */

public class LoadingTask extends AsyncTask<Void, Integer, Void> {
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
    protected void onPostExecute(Void aVoid) {
        //로딩 완료
        super.onPostExecute(aVoid);
        Loading.setVisibility(View.GONE);
        LoadingMessage.setText(ConstString.LOADING_COMPLETE_EN);
        Intent intent = new Intent(Parent, MainActivity.class);

        /**
         * intent에 User에 대한 정보를 실어서
         * 다음 Acitivity에 전달할 것
         */
        Parent.startActivity(intent);

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        //로딩 중 프로그레스바 갱신
        super.onProgressUpdate(progress);
        Log.d("onProgressUpdate", "progress : " + progress[0]);
        Loading.setProgress(progress[0]);

        if(progress[0] % ConstNumber.TEST_HASH_KEY == 0) {
            LoadingMessage.setText(ConstString.LOADING_MESSAGE_1_KR);
        } else {
            LoadingMessage.setText(ConstString.NULL_STRING);
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        //로딩 중 데이터 송수신 또는 적재

        /**
         * FIXME: Rest API 호출하여 사용자 정보 받아오도록 수정
         * 아래는 현재 Loading Testing..
         */

        //publicshProgress()로 넘기면 onProgressUpdate 실행됨됨
        for(int i = 0; i < ConstNumber.TEST_MAX_LOOP_COUNT ; i++)
            publishProgress(new Integer(i));

        return null;
    }
}
