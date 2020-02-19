package com.example.waholy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    private static String PREF_NAME = "LOGIN";
    private static String LOGIN = "IS_LOGIN";
    private static String NAME = "NAME";
    private static String EMAIL = "EAMIL";

    public Context context;
    int PRIVATE_MODE=0;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }
    public void createSession(String name,String Email){
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(EMAIL,Email);
        editor.apply();
    }
    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public void checkLogin(){
        if(!this.isLogin()){
            Intent i = new Intent(context,LoginActivity.class);
            context.startActivity(i);
            ((BoardActivity) context).finish();
        }
    }
    public HashMap<String,String>  getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        return user;
    }
    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context,LoginActivity.class);
        context.startActivity(i);
        ((BoardActivity) context).finish();
    }
}
