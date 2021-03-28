package com.example.myapplicationbyparag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SubActivity extends AppCompatActivity {

    Button confirm_button;
    EditText text_input;
    SharedPreferences data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Objects.requireNonNull(getSupportActionBar())
                .setDisplayHomeAsUpEnabled(true);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        final String[] toast_text = {"Cannot be blank!"};
        final Boolean[] is_valid_input = {Boolean.FALSE};
        final Boolean[] is_email = {Boolean.FALSE};
        final String[] emailOrNumber = new String[1];

        text_input = (EditText) findViewById(R.id.emailOrNumber);
        text_input.addTextChangedListener(new TextValidator(text_input) {
            @Override public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    toast_text[0] = "Cannot be blank!";
                    is_valid_input[0] = Boolean.FALSE;
                } else if (text.matches("[0-9]{10}")) {
                    toast_text[0] = "Sending message ...";
                    is_valid_input[0] = Boolean.TRUE;
                    is_email[0] = Boolean.FALSE;
                    emailOrNumber[0] = text;
                } else if (text.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}" +
                        "~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-" +
                        "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-" +
                        "\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                        "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                        "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|" +
                        "[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?" +
                        "[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b" +
                        "\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-" +
                        "\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")) {
                    toast_text[0] = "Sending email ...";
                    is_valid_input[0] = Boolean.TRUE;
                    is_email[0] = Boolean.TRUE;
                    emailOrNumber[0] = text;
                } else {
                    toast_text[0] = "Invalid input!!!";
                    is_valid_input[0] = Boolean.FALSE;
                }
            }
        });

        confirm_button = (Button) findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_valid_input[0]) {
                    displayToast(context, toast_text[0], duration,
                            is_email[0], emailOrNumber[0]);
                } else {
                    text_input.setError(toast_text[0]);
                }
            }
        });

    }

    public void displayToast(Context context, String text, int duration,
                             Boolean is_email, String recipient){
        Toast.makeText(context, text, duration).show();
        data = getSharedPreferences("DataStorage", Context.MODE_PRIVATE);
        String message = data.getString("message", "Enter message Here!");
        if (is_email) {
            try {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                        Uri.fromParts("email", recipient, null));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {recipient}); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Automated email");
                emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(emailIntent, "Send automated email ..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent messageIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.fromParts("sms", recipient, null));
            messageIntent.putExtra("sms_body", message);
            startActivity(messageIntent);
        }
    }


}