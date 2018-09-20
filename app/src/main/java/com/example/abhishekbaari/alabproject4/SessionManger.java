package com.example.abhishekbaari.alabproject4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by wikav-pc on 9/16/2018.
 */

public class SessionManger {

    public static SharedPreferences sharedPref;
    SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editorForResponse;
    public SharedPreferences.Editor editor;
    public Context context;
    int Private=0;

    private static final String PRIF="LOGIN";
    private static final String LOGIN="IS_LOGIN";
    public static final String NAME="NAME";
    public  static final String SID="SID";
    public  static final String PID="PID";
    public  static final String PHOTO="PHOTO";
    public  static final String SCL_ID="SCL_ID";
    public  static final String CLAS="CLAS";
    public  static final String MOBILE="MOBILE";
    public  static final String EMAIL ="EMAIL";
    public  static final String PASS="PASS";



    private static final String PREFERENCE_NAME = "APP_PREFERENCE";


    public SessionManger(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PRIF,Private);
        editor = sharedPreferences.edit();


    }

    public static void putString(Context context, String key, String value) {
        sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editorForResponse = sharedPref.edit();
        editorForResponse.putString(key, value);
        editorForResponse.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }



    public void createSession(String name, String sid,String pid,String photo,String scl_id, String classs, String mobile,String email,String pass)
    {
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
         editor.putString(SID,sid);
         editor.putString(PID,pid);
        editor.putString(PASS,pass);
         editor.putString(PHOTO,photo);
        editor.putString(SCL_ID,scl_id);
        editor.putString(CLAS,classs);
        editor.putString(MOBILE,mobile);
        editor.putString(EMAIL,email);
        editor.apply();
    }



    public boolean isLoging()
    {
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin()
    {
        if(!isLoging())
        {
            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);
            ((ForProfile)context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user =new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(SID,sharedPreferences.getString(SID,null));
        user.put(PHOTO,sharedPreferences.getString(PHOTO,null));
        user.put(SCL_ID,sharedPreferences.getString(SCL_ID,null));
        user.put(CLAS,sharedPreferences.getString(CLAS,null));
        user.put(MOBILE,sharedPreferences.getString(MOBILE,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(PASS,sharedPreferences.getString(PASS,null));
        return user;
    }

    public void logOut()
    {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
        ((ForProfile)context).finish();
    }


}
