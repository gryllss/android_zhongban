package com.example.s.x5x5x5x55x;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.nio.file.FileVisitOption;

public class ContactMeActivity extends AppCompatActivity {

    private ImageButton mBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_me);

        mBack = (ImageButton) findViewById(R.id.btnBack1);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
