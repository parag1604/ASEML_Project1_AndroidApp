package com.example.myapplicationbyparag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    Button confirm_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Context context = getApplicationContext();
        CharSequence sms_text = "Sending message...";
        CharSequence email_text = "Sending email...";
        CharSequence error_text = "Not a valid email or phone number";
        int duration = Toast.LENGTH_SHORT;

        confirm_button = (Button) findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayToast(context, error_text, duration);
            }
        });

    }

    public void displayToast(Context context, CharSequence text, int duration){
        Toast.makeText(context, text, duration).show();
    }
}