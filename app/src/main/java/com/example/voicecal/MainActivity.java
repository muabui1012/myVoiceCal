package com.example.voicecal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.voicecal.Functions;

import java.util.List;
import java.util.Locale;
import java.io.*;


public class MainActivity extends AppCompatActivity {

    private Button changeModeButton;
    ImageButton btn_Nghe;
    EditText edt_View;

    EditText edt_Result;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Context context = MainActivity.this;

    //Chuyển văn bản thàn giọng nói
    TextToSpeech textToSpeech;
    Button btn_NOI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Nghe = findViewById(R.id.img_btn_Nghe);
        edt_View = findViewById(R.id.edtView);
        edt_Result = findViewById(R.id.edtResult);
        //btn_NOI = findViewById(R.id.btn_noi);

        btn_Nghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speech_TO_TEXT();
            }
        });

        changeModeButton = findViewById(R.id.changeModeButton);
        changeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdvanceMode();
            }
        });


    }

    public void speech_TO_TEXT(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault() );

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Đang nghe  --__-- ");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn\\'t support speech input" ,
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {

                    List<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    text = Functions.Std(text);
                    edt_View.setText(text);
                    //edt_View.setText(Functions.cal(text));
                    String res = Functions.cal(text);

                    edt_Result.setText(res);

                    textToSpeech(edt_Result.getText().toString());

                }
                break;
            }

        }
    }

    public void  textToSpeech(String text){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if ( status != TextToSpeech.ERROR ){
                    textToSpeech.setLanguage( new Locale("vi_VN") );
                    textToSpeech.setSpeechRate((float) 1.5);
                    textToSpeech.speak(text , TextToSpeech.QUEUE_FLUSH , null);

                }
            }
        });
    }

    public void openAdvanceMode() {
        Intent intent = new Intent(this, AdvanceMode.class);
        startActivity(intent);
    }




}