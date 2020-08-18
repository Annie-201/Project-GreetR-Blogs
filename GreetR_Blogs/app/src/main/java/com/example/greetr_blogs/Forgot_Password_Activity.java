package com.example.greetr_blogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password_Activity extends AppCompatActivity {

    EditText email;
    Button recoverPassword;
    FirebaseAuth auth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password_);

        email = findViewById(R.id.forgotEmail);
        recoverPassword = findViewById(R.id.forgotBtn);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Forgot Password");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        recoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etEmail = email.getText().toString();
                if(TextUtils.isEmpty(etEmail)) {
                    email.setError("Email is required");
                }else {
                    recoverPasswords(etEmail);
                }
            }
        });
    }

    private void recoverPasswords(String email) {

        pd.setMessage("please wait");
        pd.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pd.dismiss();

                    Toast.makeText(Forgot_Password_Activity.this, "email link is sent please check", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Forgot_Password_Activity.this, MainActivity.class));
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();

                Toast.makeText(Forgot_Password_Activity.this, ""+e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}