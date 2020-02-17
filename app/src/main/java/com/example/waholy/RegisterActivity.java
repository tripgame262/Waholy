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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail,editTextPass,editTextName,editTextPhone;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USER = "user";
    private static final String TAG = "RegisterActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = (EditText) findViewById(R.id.edit_regis_email);
        editTextPass = (EditText) findViewById(R.id.edit_regis_pass);
        editTextName = (EditText) findViewById(R.id.edit_regis_name);
        editTextPhone = (EditText) findViewById(R.id.edit_regis_phone);
        findViewById(R.id.Rbtn_regis).setOnClickListener(this);
        findViewById(R.id.have_id).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
    }
    public void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();
        String fullname = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
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
        user = new User(email,password,fullname,phone);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User Register Sucessfull", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    UpdateUI(user);

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
    public void UpdateUI(FirebaseUser currentUser){
        String keyID = mDatabase.push().getKey();
        mDatabase.child(keyID).setValue(user);
        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(i);
    }
}
