package com.example.waholy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends AppCompatActivity {
    private Menu action;
    private SessionManager sessionManager;
    private String getID;
    private EditText Etopic,Edetails,Ejob,Eamount;
    private static String URL_NEWPOST = "http://192.168.100.126/mobile/submitPost.php";
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
        BottomNavigationView bottomNavigationView = findViewById(R.id.new_post);
        bottomNavigationView.setSelectedItemId(R.id.nav_Post);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.nav_home:
                        startActivity(new Intent(NewPostActivity.this,HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_Post:
                        return true;
                    case R.id.nav_person:
                        startActivity(new Intent(NewPostActivity.this,ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewPostActivity.this);
                        builder.setTitle("Are you sure?");
                        builder
                                .setMessage("Are you sure to logout?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sessionManager.logout();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return true;
                }
                return false;
            }
        });
    }
    public void init(){
        Etopic = (EditText) findViewById(R.id.edit_topic);
        Edetails = (EditText) findViewById(R.id.edit_details);
        Ejob = (EditText) findViewById(R.id.edit_job);
        Eamount = (EditText) findViewById(R.id.edit_amount);
    }
    public void savePost(){
        final String topic = Etopic.getText().toString().trim();
        final String details = Edetails.getText().toString().trim();
        final String job = Ejob.getText().toString().trim();
        final String amount = Eamount.getText().toString().trim();
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
                params.put("job",job);
                params.put("amount",amount);
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
        action.findItem(R.id.search_tag).setVisible(false);
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
