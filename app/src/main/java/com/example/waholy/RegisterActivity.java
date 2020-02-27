package com.example.waholy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail,editTextPass,editTextName,editTextPhone;
    private static String URL_REGIST = "http://10.51.94.183/mobile/register.php";
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
    }
    public void registerUser(){
        final String name = editTextName.getText().toString().trim();
        final String pass = editTextPass.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(RegisterActivity.this, "Register Sucessfull.", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(i);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error."+e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Register Error."+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",pass);
                params.put("name",name);
                params.put("phone",phone);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
