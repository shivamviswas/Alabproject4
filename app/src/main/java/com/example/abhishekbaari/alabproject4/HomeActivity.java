package com.example.abhishekbaari.alabproject4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {
    LinearLayout attandance,marks,syllabus,fees;
    SessionManger sessionManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );

        attandance =findViewById( R.id.attendance );
        marks=findViewById( R.id.marks);
        syllabus=findViewById( R.id.syllabus );
        fees = findViewById( R.id.fees );
        sessionManger=new SessionManger(this);

        sessionManger.checkLogin();



/////////////////////////////////////////////////for action bar////////////////////////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        toolbar.setTitle( "Schoolian" );

//////////////////////////////////////////////////for click///////////////////////////////////////////////////////////////////////////

        attandance.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomeActivity.this,AttendanceActivity.class );
                startActivity( intent );
            }
        } );

        marks.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomeActivity.this,MarksActivity.class );
                startActivity( intent );
            }
        } );

        syllabus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( HomeActivity.this,SyllabusActivity.class );
                startActivity( intent );
            }
        } );

        fees.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HomeActivity.this,Fees.class );
                startActivity( intent );
            }
        } );
    }

}
