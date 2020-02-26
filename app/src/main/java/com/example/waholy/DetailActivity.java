package com.example.waholy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    TextView mTopic,mAmount,mTag,mName,mDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        ActionBar actionBar = getSupportActionBar();
        Intent i = getIntent();
        mTopic.setText(getIntent().getStringExtra("iTopic"));
        mAmount.setText(getIntent().getStringExtra("iAmount"));
        mTag.setText(getIntent().getStringExtra("iTag"));
        mDetail.setText(getIntent().getStringExtra("iDetail"));
        actionBar.setTitle(getIntent().getStringExtra("iTag"));




    }
    public void init(){
        mTopic = (TextView) findViewById(R.id.dTopic);
        mAmount =(TextView) findViewById(R.id.dAmount);
        mTag =(TextView) findViewById(R.id.dTag);
        mDetail =(TextView) findViewById(R.id.dDetail);
    }
}
