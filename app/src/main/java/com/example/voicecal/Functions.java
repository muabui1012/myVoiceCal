package com.example.voicecal;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Functions {

    public static String cal(String exp) {
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable,exp,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }

    }

    public static String Std(String exp) {
        String change = exp;
        change = change.replace("chia","/");
        change = change.replace("nhân","*");
        change = change.replace("x","*");
        change = change.replace("cộng","+");
        change = change.replace("trừ","-");
        change = change.replace(".","");
        change = change.replace(",",".");
        change = change.replace(" ","");


        return change;
    }


}
