package com.example.waholy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private List<Post> lstView = new ArrayList<>();
    private RecyclerView listView ;
    private Menu action;
    private static String URL_POST = "https://waholyproj.000webhostapp.com/files/getPost.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        listView = (RecyclerView) findViewById(R.id.listPost);
        getAllPost();
        //bottom
        BottomNavigationView bottomNavigationView = findViewById(R.id.home);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.nav_home:
                        return true;
                    case R.id.nav_person:
                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
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

    public void getAllPost(){
        StringRequest stringRequest = new StringRequest(URL_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("post");
                            for (int i =0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                Post post = new Post();
                                post.setTopic(object.getString("topic"));
                                post.setJob(object.getString("job"));
                                post.setName(object.getString("name"));
                                lstView.add(post);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(HomeActivity.this,"Loading",Toast.LENGTH_SHORT).show();
                        setRvadapter(lstView);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeActivity.this, "Error.."+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setRvadapter(List<Post> lstView) {
        RvAdapter myAdapter = new RvAdapter(this,lstView) ;
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(myAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu,menu);
        action = menu;
        action.findItem(R.id.savepost).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.newpost:
                Intent intent = new Intent(HomeActivity.this,NewPostActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
