package com.example.appdatdoan.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdatdoan.R;
import com.example.appdatdoan.model.DetailCartDatabase;
import com.example.appdatdoan.model.FeedBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FeedbackFragment extends Fragment {
    private ProgressDialog progressDialog;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtContent;
    private Button btnSendFeedBack;
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    FirebaseUser userFb = FirebaseAuth.getInstance().getCurrentUser();
    String EmailUser = userFb.getEmail();


    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        initUI(view);
        btnSendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSent();
            }
        });
        return view;
    }
    // khi an vao sent
    private void onClickSent() {
        String name = edtName.getText().toString();
        String content = edtContent.getText().toString();
        int idFeedBack = generateIDFeedBack();
        String time = dateFormat.format(currentDate);
        if(content == null)
        {
            new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.app_name))
                    .setMessage("You don't enter content!")
                    .setPositiveButton("OK", null).show();
        }
        else{
            FeedBack feedBack = new FeedBack(idFeedBack, name,EmailUser,content,time);
            createFeedback(feedBack);
        }


    }
    //create feedback
    private void createFeedback(FeedBack feedBack) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        progressDialog.show();
        DatabaseReference myRef = database.getReference("DataShop/FeedBack");
        int tt = generateIDFeedBack();
        String path = String.valueOf(tt);
        myRef.child(path).setValue(feedBack, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
                new AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.app_name))
                        .setMessage("Thank you for your comments about our restaurant")
                        .setPositiveButton("OK", null).show();
                setEdittext();
            }
        });
    }

    private void setEdittext() {
        edtName.setText("");
        edtContent.setText("");
    }

    // anh xa view
    private void initUI(View view) {
        progressDialog = new ProgressDialog(getContext());
        edtName = view.findViewById(R.id.edt_YourName);
        edtEmail = view.findViewById(R.id.edt_YourEmail);
        edtContent = view.findViewById(R.id.edt_Content);
        btnSendFeedBack = view.findViewById(R.id.btn_SentFeedBack);
        edtEmail.setText(EmailUser);
    }
    //get id feedback
    int generateIDFeedBack() {
        long timestamp = System.currentTimeMillis();
        int truncatedTimestamp = (int) (timestamp % Integer.MAX_VALUE);
        return truncatedTimestamp;
    }
}