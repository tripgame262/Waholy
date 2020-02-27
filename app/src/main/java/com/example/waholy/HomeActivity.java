package com.example.waholy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
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
    private RvAdapter rvAdapter;
    private Menu action;
    private static String URL_POST = "http://10.51.94.183/mobile/getPost.php";
    private static String URL_SEARCH = "http://10.51.94.183/mobile/search.php";
    EditText Esearch;
    Button btn_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(this);
        listView = (RecyclerView) findViewById(R.id.listPost);
        //getPost
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
                    case R.id.nav_Post:
                        startActivity(new Intent(HomeActivity.this,NewPostActivity.class));
                        overridePendingTransition(0,0);
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
    public void search(){
        final String search = Esearch.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("post");
                            if(success.equals("1")){
                                for(int i=0; i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Post post = new Post();
                                    post.setTopic(object.getString("topic"));
                                    post.setJob(object.getString("job"));
                                    post.setName(object.getString("name"));
                                    post.setAmount(object.getString("amount"));
                                    lstView.add(post);
                                    Toast.makeText(HomeActivity.this, "Test", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(HomeActivity.this, "Error.."+e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeActivity.this, "Error.."+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("search",search);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                                post.setAmount(object.getString("amount"));
                                post.setDetails(object.getString("details"));
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
        rvAdapter = new RvAdapter(this,lstView) ;
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(rvAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu,menu);
        action = menu;
        action.findItem(R.id.newpost).setVisible(false);
        action.findItem(R.id.savepost).setVisible(false);
        MenuItem item = menu.findItem(R.id.search_tag);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                rvAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rvAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}
