package com.example.myapplicationbyparag;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SubActivity extends AppCompatActivity {

    Button confirm_button;
    EditText text_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        text_input = (EditText) findViewById(R.id.emailOrNumber);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        String toast_text = "Testing...";

        confirm_button = (Button) findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(v -> displayToast(context, toast_text, duration));

    }

    public void displayToast(Context context, String text, int duration){
        Toast.makeText(context, text, duration).show();
    }
}