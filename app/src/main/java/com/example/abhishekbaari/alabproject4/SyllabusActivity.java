package com.example.abhishekbaari.alabproject4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abhishekbaari.alabproject4.adapter.AdapterForSyllabus;
import com.example.abhishekbaari.alabproject4.modal.Syllabus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyllabusActivity extends AppCompatActivity {
    SessionManger sessionManger;
    String sclid,clas;
    private List<Syllabus> lstAnime ;
    private RecyclerView recyclerView ;
    ProgressBar progressBar;
    private final String JSON_URL = "http://schoolian.website/android/sallybus.php" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_syllabus );
        sessionManger=new SessionManger(SyllabusActivity.this);
        HashMap<String, String> user=sessionManger.getUserDetail();
        String Esclid = user.get(sessionManger.SCL_ID);
        String Ecls = user.get(sessionManger.CLAS);
        String Sid= user.get(sessionManger.SID);
        sclid=Esclid;
        recyclerView=findViewById(R.id.recyclerviewSyllbus);
        progressBar=findViewById(R.id.progressSyllabus);
        lstAnime=new ArrayList<>();
        //sidi=Sid;
        clas=Ecls;
        showMarks(sclid,clas);
    }

    private void showMarks(final String sclid, final String clas) {

        // Toast.makeText(getActivity(),"method call",Toast.LENGTH_LONG).show();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    JSONObject jsonObject1=new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray= jsonObject1.getJSONArray("syllabus");

                    if(success.equals("1"))
                    {
                        // mobileArray= new String[jsonArray.length()];
                        for(int i=0 ; i<jsonArray.length();i++)
                        {

                            Syllabus syllabus=new Syllabus();
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            // mobileArray[i]=jsonObject.get("subject").toString();
                            syllabus.setSubject(jsonObject.getString("subject"));
                            syllabus.setSyllabusComplete(jsonObject.getString("syllabus"));
                            lstAnime.add(syllabus);
                        }
                        setuprecyclerview(lstAnime);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SyllabusActivity.this,"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SyllabusActivity.this,"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("school_id",sclid);
                param.put("clas",clas);
                return param;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SyllabusActivity.this);
        requestQueue.add(stringRequest);

    }
    private void setuprecyclerview(List<Syllabus> userPostsList) {


        AdapterForSyllabus adaptorRecycler = new AdapterForSyllabus(SyllabusActivity.this,userPostsList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(SyllabusActivity.this));
        recyclerView.setAdapter(adaptorRecycler);

    }
}
