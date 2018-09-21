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
import com.example.abhishekbaari.alabproject4.adapter.AdapterForAttendance;
import com.example.abhishekbaari.alabproject4.adapter.AdapterForSyllabus;
import com.example.abhishekbaari.alabproject4.modal.Attendance;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {
    SessionManger sessionManger;
    String sclid,clas;
    private List<Attendance> lstAnime ;
    private RecyclerView recyclerView ;
    ProgressBar progressBar;
    private final String JSON_URL = "http://schoolian.website/android/parent/attendance.php" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_attendance );
        sessionManger=new SessionManger(AttendanceActivity.this);
        HashMap<String, String> user=sessionManger.getUserDetail();
        String Esclid = user.get(sessionManger.SCL_ID);
        String Ecls = user.get(sessionManger.CLAS);
        String SSid= user.get(sessionManger.SID);
        sclid=Esclid;
        clas=SSid;
        recyclerView=findViewById(R.id.recyclerviewAtt);
        progressBar=findViewById(R.id.progressAtt);
        lstAnime=new ArrayList<>();
        //sidi=Sid;

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
                    JSONArray jsonArray= jsonObject1.getJSONArray("Att");

                    if(success.equals("1"))
                    {
                        // mobileArray= new String[jsonArray.length()];
                        for(int i=0 ; i<jsonArray.length();i++)
                        {

                            Attendance syllabus=new Attendance();
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            // mobileArray[i]=jsonObject.get("subject").toString();
                            syllabus.setDate(jsonObject.getString("date"));
                            syllabus.setPresentAbsent(jsonObject.getString("attendance"));
                            lstAnime.add(syllabus);
                        }
                        setuprecyclerview(lstAnime);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AttendanceActivity.this,"Error 1: "+e.toString(),Toast.LENGTH_LONG).show();
//                    button.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AttendanceActivity.this,"Error 2: "+error.toString(),Toast.LENGTH_LONG).show();
//                        button.setVisibility(View.VISIBLE);
//                        progressBar.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("school_id",sclid);
                param.put("sid",clas);
                return param;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AttendanceActivity.this);
        requestQueue.add(stringRequest);

    }
    private void setuprecyclerview(List<Attendance> userPostsList) {


        AdapterForAttendance adaptorRecycler = new AdapterForAttendance(AttendanceActivity.this,userPostsList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(AttendanceActivity.this));
        recyclerView.setAdapter(adaptorRecycler);

    }
}
