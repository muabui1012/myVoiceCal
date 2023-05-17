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
import android.widget.TextView;
import android.widget.Toast;

import com.example.voicecal.Functions;

import java.util.List;
import java.util.Locale;
import java.io.*;


public class MainActivity extends AppCompatActivity {

    private ImageButton changeModeButton;
    ImageButton btn_Nghe;
    EditText edt_View;

    EditText edt_Result;
    TextView solutionTv, resultTv;

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
        solutionTv = findViewById(R.id.solution_tv);
        resultTv = findViewById(R.id.result_tv);
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
        textToSpeech("","Bấm vào biểu tượng micro ở giữa màn hình để đọc biểu thức và tôi sẽ tính giúp bạn. Bấm vào biểu tượng bàn phím ở phía dưới để hiện bàn phím");

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

                    if (text.equals("mở rộng") || text.equals("mở bàn phím") || text.equals("Mở rộng") || text.equals("Mở bàn phím")) {
                        openAdvanceMode();
                        return;
                    }

                    text = Functions.Std(text);

                    solutionTv.setText(text);
                    //edt_View.setText(Functions.cal(text));

                    String res = Functions.cal(text);

                    resultTv.setText(res);

                    if (!res.equals("Err")) {
                        textToSpeech("Kết quả là", resultTv.getText().toString());
                    } else {
                        textToSpeech("Tôi không hiểu, bạn vui lòng nhập lại", "");
                    }
                }
                break;
            }

        }
    }

    public void  textToSpeech(String message, String text){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if ( status != TextToSpeech.ERROR ){
                    textToSpeech.setLanguage( new Locale("vi_VN") );
                    textToSpeech.setSpeechRate((float) 1);

                    textToSpeech.speak(message + text , TextToSpeech.QUEUE_FLUSH , null);

                }
            }
        });
    }

    public void openAdvanceMode() {
        Intent intent = new Intent(this, AdvanceMode.class);
        startActivity(intent);
    }




}