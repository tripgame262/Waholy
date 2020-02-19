package com.example.waholy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DashboardActivity extends AppCompatActivity {

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
    }
}
