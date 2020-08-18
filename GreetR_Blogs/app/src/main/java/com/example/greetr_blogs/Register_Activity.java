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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {

    EditText registerEmail, registerPassword;
    Button registerBtn;
    TextView alreadyhaveAnAcoount;

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        registerBtn = findViewById(R.id.register_btn);
        alreadyhaveAnAcoount = findViewById(R.id.already_have_an_account);

        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);

        alreadyhaveAnAcoount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_Activity.this,MainActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString().trim();
                String password = registerPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    registerEmail.setText("Email is required");
                }else if(TextUtils.isEmpty(password)) {
                    registerPassword.setError("Password is reqiured");
                }else if (password.length()<6) {
                    Toast.makeText(Register_Activity.this, "password length must be greater than 6", Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(email, password);
                }
            }
        });
    }

    private void registerUser(String email, String password) {
        progressDialog.setTitle("please wait...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            startActivity(new Intent(Register_Activity.this,Home_Activity.class));
                            Toast.makeText(Register_Activity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(Register_Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Register_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}