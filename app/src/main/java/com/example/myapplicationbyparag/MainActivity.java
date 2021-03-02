package com.example.myapplicationbyparag;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button send_button;
    ImageView voice_button;
    EditText input_text;

    private static final int RECOGNIZER_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_text = (EditText) findViewById(R.id.input_text);

        send_button = (Button) findViewById(R.id.send_button);
        send_button.setOnClickListener(v -> openNewActivity());

        voice_button = (ImageView) findViewById(R.id.voice_button);
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice to text");
        voice_button.setOnClickListener(v -> startActivityForResult(speechIntent,
                                                                    RECOGNIZER_RESULT));

    }

    public void openNewActivity(){
        Intent intent = new Intent(this, SubActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK) {
            assert data != null;
            ArrayList<String> recognizedText = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            input_text.setText(recognizedText.get(0));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
