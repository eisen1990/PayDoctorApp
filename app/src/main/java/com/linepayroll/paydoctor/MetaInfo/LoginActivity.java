package com.linepayroll.paydoctor.MetaInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.linepayroll.paydoctor.R;

public class LoginActivity extends AppCompatActivity {

    EditText CompanyIdForm;
    EditText UserIdForm;
    EditText PasswdForm;

    String CompanyId;
    String UserId;
    String Passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CompanyIdForm = (EditText) findViewById(R.id.CompanyIdForm);
        UserIdForm = (EditText) findViewById(R.id.UserIdForm);
        PasswdForm = (EditText) findViewById(R.id.PasswdForm);

        CompanyId = CompanyIdForm.getText().toString();
        UserId = UserIdForm.getText().toString();
        Passwd = PasswdForm.getText().toString();



    }
}
