package com.uatapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class StudentLoginActivity extends Activity implements View.OnClickListener {

    TextView tvRegister,tvLogin,tvCancel;
    EditText etMobileNumber,etPassword;
    DBHelper mDbHelper = null;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mSharedPreferences = getSharedPreferences("StudentPref",MODE_PRIVATE);
        mEditor            = mSharedPreferences.edit();

        mDbHelper = new DBHelper(this);
        tvRegister = findViewById(R.id.tvRegister);
        tvLogin = findViewById(R.id.tvLogin);
        tvCancel = findViewById(R.id.tvCancel);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etPassword = findViewById(R.id.etUserPassword);

        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tvRegister:

                Intent intent = new Intent(this,StudentRegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.tvLogin:

                String mobileNumber = etMobileNumber.getText().toString();
                String password = etPassword.getText().toString();

                if (mobileNumber.isEmpty()) {
                    Toast.makeText(this, "Please Enter MobileNumber ", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isLoggedIN = mDbHelper.loginValidation(mobileNumber, password);
                    if (isLoggedIN) {

                        mEditor.putString("MOBILE",mobileNumber);
                        mEditor.apply();

                        Intent loginIntent = new Intent(this, StudentDashBoard.class);
                        startActivity(loginIntent);
                    } else {
                        Toast.makeText(this, "MobileNumber & Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
            case R.id.tvCancel:

                etMobileNumber.setText("");
                etPassword.setText("");

                break;
        }
    }
}
