package com.linepayroll.paydoctor.MetaInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.linepayroll.paydoctor.ConstPack.ConstURL;
import com.linepayroll.paydoctor.R;

public class LoginActivity extends AppCompatActivity {

    EditText CompanyIdForm;
    EditText UserIdForm;
    EditText UserPasswdForm;

    Button LoginBtn;

    String CompanyId;
    String UserId;
    String UserPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CompanyIdForm = (EditText) findViewById(R.id.CompanyIdForm);
        UserIdForm = (EditText) findViewById(R.id.UserIdForm);
        UserPasswdForm = (EditText) findViewById(R.id.PasswdForm);

        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        LoginBtn.setOnClickListener(BtnOnClickListener);

    }

    Button.OnClickListener BtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == R.id.LoginBtn) {
                CompanyId = CompanyIdForm.getText().toString();
                UserId = UserIdForm.getText().toString();
                UserPasswd = UserPasswdForm.getText().toString();
                new LoginTask(LoginActivity.this).execute(ConstURL.LOGIN_URL, CompanyId, UserId, UserPasswd);
            }
        }
    };

}
