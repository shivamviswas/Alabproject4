package com.example.abhishekbaari.alabproject4;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abhishekbaari.alabproject4.adapter.AdapterForMarks;
import com.example.abhishekbaari.alabproject4.modal.Marks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarksActivity extends AppCompatActivity {
    private final String JSON_URL = "http://schoolian.website/android/Marks.php" ;


    private List<Marks> lstAnime ;
    private RecyclerView recyclerView ;
    SessionManger sessionManger;
    String sclid,clas,sidi;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_marks );

        sessionManger=new SessionManger(MarksActivity.this);
        HashMap<String, String> user=sessionManger.getUserDetail();
        String Esclid = user.get(sessionManger.SCL_ID);
        String Sid= user.get(sessionManger.SID);
        sclid=Esclid;
        sidi=Sid;

        final String sub="All Subject";
         showMarks(sclid,sidi,sub);





        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewMarks);
        lstAnime = new ArrayList<>();
        progressBar= (ProgressBar) findViewById(R.id.progressMarks);

    }
    private void showMarks(final String sclid, final String sidi,final String sub) {

        // Toast.makeText(getActivity(),"method call",Toast.LENGTH_LONG).show();
       // progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                  //  progressBar.setVisibility(View.GONE);
                    JSONObject jsonObject1=new JSONObject(response);
                    String success = jsonObject1.getString("success");
                    JSONArray jsonArray= jsonObject1.getJSONArray("marks");

                    if(success.equals("1"))
                    {
                        for(int i=0 ; i<jsonArray.length();i++)
                        {
                            //Toast.makeText(getActivity(),"work",Toast.LENGTH_LONG).show();
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            Marks anime = new Marks() ;
                            anime.setStName(jsonObject.getString("name"));
                            anime.setStSubject(jsonObject.getString("subject"));
                            anime.setObtmarks(jsonObject.getString("obmarks"));
                            anime.setExamName(jsonObject.getString("examname"));
                            anime.setTotalmarks(jsonObject.getString("totalmarks"));

                            // lstAnime = new ArrayList<teacher_list>();
                            lstAnime.add(anime);

                        }
                        setuprecyclerview(lstAnime);
                    }
                    else {
                        Toast.makeText(MarksActivity.this, "No Exam Available of this Subject", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MarksActivity.this,"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MarksActivity.this,"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("school_id",sclid);
                param.put("sid",sidi);
                param.put("sub",sub);
                return param;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MarksActivity.this);
        requestQueue.add(stringRequest);

    }

    private void setuprecyclerview(List<Marks> userPostsList) {


        AdapterForMarks adaptorRecycler = new  AdapterForMarks(MarksActivity.this,userPostsList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(MarksActivity.this));
        recyclerView.setAdapter(adaptorRecycler);

    }
}
