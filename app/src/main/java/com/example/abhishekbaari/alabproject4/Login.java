package com.example.abhishekbaari.alabproject4;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
        public ProgressBar progressBar;
        public Button login;
        public EditText mobile,pass;
        public TextInputLayout mobile_hint,pass_hint;
        public SessionManger sessionManger;
        private final String JsonUrl="http://schoolian.website/android/parent/parentLogin.php";
    int versionCode = BuildConfig.VERSION_CODE;
    private final String uplod="http://schoolian.website/android/getAppUpdate.php";
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar=findViewById(R.id.progressLogin);
        login=findViewById(R.id.btn_login);
        mobile=findViewById(R.id.loginMobile);
        pass=findViewById(R.id.loginPass);
        mobile_hint=findViewById(R.id.email_hint);
        pass_hint=findViewById(R.id.pass_hint);
        sessionManger=new SessionManger(this);

        updateApp();

    }

    public void onLogin(View view) {
        String mMobile=mobile.getText().toString().trim();
        String mPass=pass.getText().toString().trim();
        if(mMobile.isEmpty() || mPass.isEmpty()||mMobile.equals(" ")||mPass.equals(" "))
        {
            mobile_hint.setError("Please Enter mobile and password");
        }
        else {
            loginData(mMobile,mPass);
        }
    }

    private void loginData(final String mMobile, final String mPass) {
progressBar.setVisibility(View.VISIBLE);
login.setVisibility(View.GONE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, JsonUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success.equals("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("pName").trim();
                            String email = object.getString("email").trim();
                            String pid = object.getString("pid").trim();
                            String sid = object.getString("sid").trim();
                            String photo = object.getString("profile").trim();
                            String scl_id = object.getString("sclId").trim();
                            String clas = object.getString("clas").trim();
                            String phone = object.getString("pMobile").trim();
                            String pass = object.getString("pass").trim();
                            sessionManger.createSession(name,sid,pid,photo,scl_id,clas,phone,email,pass);
                            Intent intent=new Intent(Login.this,ForProfile.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else if (success.equals("2")){
                       mobile_hint.setError("Invalid Mobile");
                    }
                    else if (success.equals("3")){
                        mobile_hint.setError("Wrong Password");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, ""+e, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                }


            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, ""+error, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> pram=new HashMap<>();

                pram.put("phone",mMobile);
                pram.put("pass",mPass);
                return pram;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }




    public void forgetpassword(View view) {
    }

    public void createNew(View view) {
        Intent intent =new Intent(Login.this,MainActivity.class);
        startActivity(intent);
    }

    public void updateApp(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uplod,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("update");


                            //  Toast.makeText(Spash_Screen.this, "Success!"+response, Toast.LENGTH_SHORT).show();
                            int code=0;
                            String link="",msg="";
                            if (success.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    code = object.getInt("version_code");
                                    link = object.getString("updatelink").trim();
                                    msg = object.getString("msg").trim();
                                }

                                Checkupdate(code,link,msg);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(Login.this, "Try Again!"+e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Login.this, "Try Again!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("getupdate","1");
                params.put("app","parent");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void Checkupdate(int cod, final String link,final String msg) {
        if(versionCode<cod)
        {
            builder = new AlertDialog.Builder(Login.this);

            builder.setTitle("Update!");



            builder.setMessage(msg);
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Login.this, "Updating...", Toast.LENGTH_SHORT).show();
                    String url = "http://"+link;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            builder.setCancelable(false);
            final AlertDialog alertDialog= builder.create();
            builder.show();
            Login.this.setFinishOnTouchOutside(false);


        }


    }


}
