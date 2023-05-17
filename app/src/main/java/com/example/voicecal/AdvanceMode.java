package com.example.voicecal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;


public class AdvanceMode extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv,solutionTv;
    MaterialButton buttonC,buttonBrackOpen,buttonBrackClose;
    MaterialButton buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonAC,buttonDot;

    ImageButton changeMode;
    ImageButton btn_Nghe;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_mode);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonC,R.id.button_c);
        assignId(buttonBrackOpen,R.id.button_open_bracket);
        assignId(buttonBrackClose,R.id.button_close_bracket);
        assignId(buttonDivide,R.id.button_divide);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(buttonPlus,R.id.button_plus);
        assignId(buttonMinus,R.id.button_minus);
        assignId(buttonEquals,R.id.button_equals);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
        assignId(buttonAC,R.id.button_ac);
        assignId(buttonDot,R.id.button_dot);

        changeMode = findViewById(R.id.changeMode);
        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        btn_Nghe = findViewById(R.id.img_btn_Nghe);
        btn_Nghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speech_TO_TEXT();
            }
        });

        textToSpeech("Đã chuyển sang chế độ mở rộng");


    }

    public void speech_TO_TEXT(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault() );

        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Đang nghe  --__-- ");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn\\'t support speech input" ,
                    Toast.LENGTH_SHORT).show();
        }
        //speech_TO_TEXT();
    }

    void assignId(MaterialButton btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        MaterialButton button =(MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if(buttonText.equals("=")){

            //dataToCalculate = "";
            String sol = solutionTv.getText().toString();
            sol = Functions.Std(sol);
            if (sol.equals("ERR")) {
                textToSpeech("Tôi không hiểu, vui lòng nhập lại.");
                solutionTv.setText("");
                resultTv.setText("");
                return;
            }
            sol = getResult(sol);
            resultTv.setText(sol);
            textToSpeech("Kết quả là " + resultTv.getText().toString());
            solutionTv.setText("");
            return;
        }
        if(buttonText.equals("C") && (!solutionTv.getText().toString().equals(""))){
            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
        }else{
            if (!buttonText.equals("C"))
                dataToCalculate = dataToCalculate+buttonText;
        }
        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("Err")){
            resultTv.setText(finalResult);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {

                    List<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    if (text.equals("Tắt bàn phím") || text.equals("tắt bàn phím")) {
                        openMain();
                        return;
                    }

                    text = Functions.Std(text);

                    String textbutton = "";
                    for (int i=0; i < text.length(); i++) {
                        String ch = String.valueOf(text.charAt(i));
                        textbutton = textbutton + speechToButton(ch);
                    }
                    String sol = solutionTv.getText().toString();
                    String finalsol = sol +textbutton;
                    solutionTv.setText(finalsol);


                    //String res = getResult(text);
                    //resultTv.setText(res);
//                    String res = getResult(text);
//
//                    if(!res.equals("Err")){
//                        resultTv.setText(res);
//                    } else {
//                        resultTv.setText("Syntax Error");
//                    }
//                    textToSpeech(resultTv.getText().toString());

                }
                break;
            }

        }
    }


    public void textToSpeech(String text){
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if ( status != TextToSpeech.ERROR ){
                    textToSpeech.setLanguage( new Locale("vi_VN") );
                    textToSpeech.setSpeechRate((float) 1);
                    textToSpeech.speak(text , TextToSpeech.QUEUE_FLUSH , null);

                }
            }
        });
    }

    String getResult(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            finalResult = rounddecimal(finalResult);
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }

    String rounddecimal(String data) {
        float n = Float.parseFloat(data);
        DecimalFormat df = new DecimalFormat("#.#####");
        df.format(n);
        return Float.toString(n);

    }

    String speechToButton(String text) {

        if (text.equals("C")) {
            String sol = solutionTv.getText().toString();
            if (sol.length() != 0) {
                sol = sol.substring(0,sol.length()-1);
                solutionTv.setText(sol);
            }
            return "";
        }
        if (text.equals("A")) {
            solutionTv.setText("");
            return "";
        }

        if (text.equals("=")) {
            String sol = solutionTv.getText().toString();
            sol = Functions.Std(sol);
            if (sol.equals("ERR")) {
                textToSpeech("Tôi không hiểu, vui lòng nhập lại.");
                solutionTv.setText("");
                resultTv.setText("");
                return "";
            }
            sol = getResult(sol);
            resultTv.setText(sol);
            textToSpeech("Kết quả là " + resultTv.getText().toString());
            solutionTv.setText("");
            return "";
        }


        if (text.equals("0")) return "0";
        if (text.equals("1")) return "1";
        if (text.equals("2")) return "2";
        if (text.equals("3")) return "3";
        if (text.equals("4")) return "4";
        if (text.equals("5")) return "5";
        if (text.equals("6")) return "6";
        if (text.equals("7")) return "7";
        if (text.equals("8")) return "8";
        if (text.equals("9")) return "9";
        if (text.equals("(")) return "(";
        if (text.equals(")")) return ")";
        if (text.equals(".")) return ".";
        if (text.equals("+")) return "+";
        if (text.equals("-")) return "-";
        if (text.equals("*")) return "*";
        if (text.equals("/")) return "/";



        return "";
    }


}