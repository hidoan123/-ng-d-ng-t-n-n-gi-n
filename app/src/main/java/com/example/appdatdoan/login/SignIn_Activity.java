package com.example.appdatdoan.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdatdoan.MainActivity;
import com.example.appdatdoan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn_Activity extends AppCompatActivity {
    private LinearLayout layoutSignup;
    private EditText edtEmail, edtPassWord;
    private Button btnSignIn;
    private ProgressDialog progressDialog;
    private TextView tv_forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initUI();
        initListener();
    }
    //nhuwng su kien khi bam
    private void initListener() {
        //su kien khi bam vao nut dang ki
        layoutSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn_Activity.this, SignUp_Activity.class);
                startActivity(intent);
            }
        });
        // khi bam vao nut dang nhap
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }
        });
        // khi bam vao quen mat khau
        tv_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForgotPassWord();
            }
        });
    }

    // anh xa view
    private void initUI() {
        progressDialog = new ProgressDialog(this);
        edtEmail = findViewById(R.id.edt_email);
        edtPassWord = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.btn_signin);
        layoutSignup = findViewById(R.id.layout_signup);
        tv_forgotPassword = findViewById(R.id.tv_forgotpassword);
    }
    // xu li khi an vao nut dang nhap

    private void onClickSignIn() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassWord = edtPassWord.getText().toString().trim();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.signInWithEmailAndPassword(strEmail, strPassWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(SignIn_Activity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignIn_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
        // su kien khi quen mat khau
    private void onClickForgotPassWord() {

    }
}