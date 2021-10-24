package com.example.onsitetask_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static TextView msgtv,timetv;
    public static Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msgtv =findViewById(R.id.msg);
        timetv=findViewById(R.id.timer);
        start=findViewById(R.id.start);
        FrameLayout frameLayout=findViewById(R.id.frameLayout);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}