package com.example.waholy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail,editTextPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = (EditText) findViewById(R.id.edit_regis_email);
        editTextPass = (EditText) findViewById(R.id.edit_regis_pass);
        findViewById(R.id.Rbtn_regis).setOnClickListener(this);
        findViewById(R.id.have_id).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }
    public void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPass.setError("Password lenght more than 6");
            editTextPass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Plase Enter a email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPass.setError("Password is required");
            editTextPass.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User Register Sucessfull", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(RegisterActivity.this, "You are already register ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Rbtn_regis:
                registerUser();
                break;
            case R.id.have_id:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }

    }
}
