package com.example.appdatdoan.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdatdoan.MainActivity;
import com.example.appdatdoan.R;
import com.example.appdatdoan.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp_Activity extends AppCompatActivity {
    private EditText edtEmail,edtPassword,edtUserName,edtPhoneNumber;
    private Button btnSignUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        initListener();
    }
    // anh xa view
    private void initUI() {
        edtPhoneNumber = findViewById(R.id.edt_PhoneNumber);
        edtUserName = findViewById(R.id.edt_Name);
        edtEmail = findViewById(R.id.edt_suemail);
        edtPassword = findViewById(R.id.edt_supassword);
        btnSignUp = findViewById(R.id.btn_sign_up);
        progressDialog = new ProgressDialog(this);
    }
    // xu li su kien click
    private void initListener() {

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });
    }
    // khi bam vao dang ki
    private void onClickSignUp() {
        String strUserName = edtUserName.getText().toString();
        String strPhoneNumber = edtPhoneNumber.getText().toString();
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        //check dieu kien
        if(strUserName == null || strEmail == null || strPhoneNumber == null || strPassword == null)
        {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_name))
                    .setMessage("You must enter enough information!")
                    .setPositiveButton("OK", null).show();
        }
        else{
            if(strPassword.length() < 6 )
            {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.app_name))
                        .setMessage("Password must be more than 6 characters")
                        .setPositiveButton("OK", null)
                        .show();
            }
            else{
                //dang khi bang auth
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    // signin success, create database user
                                    FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();
                                    String Id = userFb.getUid();
                                    User user1 = new User(Id,strUserName,strEmail,strPassword,strPhoneNumber);
                                    CreateUserDataBase(user1);
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(SignUp_Activity.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(SignUp_Activity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        }

    }
    //create data
    private void CreateUserDataBase(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DataShop/User");
        int tt = generateID();
        String path = String.valueOf(tt);
        myRef.child(path).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(SignUp_Activity.this, "You have successfully registered", Toast.LENGTH_SHORT).show();
            }
        });

    }
    int generateID() {
        long timestamp = System.currentTimeMillis();
        int truncatedTimestamp = (int) (timestamp % Integer.MAX_VALUE);
        return truncatedTimestamp;
    }

}