package com.example.waholy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail,editTextPass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = (EditText) findViewById(R.id.edit_login_email);
        editTextPass = (EditText) findViewById(R.id.edit_login_pass);
        findViewById(R.id.Lbtn_regis).setOnClickListener(this);
        findViewById(R.id.Lbtn_login).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }
    public void userLogin(){
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
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(LoginActivity.this,ProfileActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Lbtn_regis:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.Lbtn_login:
                userLogin();
                break;
        }
    }
}
