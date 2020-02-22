package com.example.waholy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
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

public class NewPostActivity extends AppCompatActivity {
    private Menu action;
    private SessionManager sessionManager;
    private String getID;
    private EditText Etopic,Edetails;
    private static String URL_NEWPOST = "https://waholyproj.000webhostapp.com/files/submitPost.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        init();
        //Check session
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        //getID
        HashMap<String,String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
    }
    public void init(){
        Etopic = (EditText) findViewById(R.id.edit_topic);
        Edetails = (EditText) findViewById(R.id.edit_details);
    }
    public void savePost(){
        final String topic = Etopic.getText().toString().trim();
        final String details = Edetails.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NEWPOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")){
                                Toast.makeText(NewPostActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(NewPostActivity.this,HomeActivity.class);
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(NewPostActivity.this, "Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewPostActivity.this, "Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("topic",topic);
                params.put("detail",details);
                params.put("id",getID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu,menu);
        action = menu;
        action.findItem(R.id.newpost).setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.savepost:
                savePost();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
