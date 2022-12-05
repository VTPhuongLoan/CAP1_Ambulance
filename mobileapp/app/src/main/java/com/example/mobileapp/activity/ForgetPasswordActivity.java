package com.example.mobileapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.R;
import com.example.mobileapp.api.ForgotPassAPI;
import com.example.mobileapp.dto.AccountDTO;
import com.example.mobileapp.itf.ForgotPassInterface;

import java.util.List;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgotPassInterface {

    Button btnSubmit;
    EditText inputUsername;
    TextView btnLogin, textMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
        click();
    }

    private void click() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputUsername.getText().toString();
                if (validator()) {
                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setEmail(email);

                    ForgotPassAPI forgotPassAPI = new ForgotPassAPI(ForgetPasswordActivity.this);
                    forgotPassAPI.forgotPass(accountDTO);
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter required fields!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intentlogin = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intentlogin);
            }
        });
    }

    private void initView() {
        inputUsername = findViewById(R.id.inputUsername);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnLogin = findViewById(R.id.btnLogin);

        textMsg = findViewById(R.id.textMsg);
    }

    @Override
    public void onSuccess() {
        finish();
        Intent intent =  new Intent(getApplicationContext(), CompleteForgotPassActivity.class);
        startActivity(intent);
    }

    @Override
    public void onError(List<String> errors) {
        String msg = "";
        if (errors != null) {
            for (String s : errors) {
                msg += getStringResourceByName(s) + "\n";
            }
        }
        textMsg.setText(msg);
    }

    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

    private boolean validator() {
        String email = inputUsername.getText().toString();

        if (email.length() == 0) {
            return false;
        }

        return true;
    }

}