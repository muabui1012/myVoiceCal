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
        change = change.replace("Chia","/");
        change = change.replace("nhân","*");
        change = change.replace("Nhân","*");
        change = change.replace("cộng","+");
        change = change.replace("Cộng","+");
        change = change.replace("trừ","-");
        change = change.replace("Trừ","-");
        change = change.replace(".","");
        change = change.replace(",",".");
        change = change.replace("phẩy",".");
        change = change.replace("Phẩy",".");
        change = change.replace(" ","");
        change = change.replace("một","1");
        change = change.replace("hai","2");
        change = change.replace("ba","3");
        change = change.replace("bốn","4");
        change = change.replace("năm","5");
        change = change.replace("sáu","6");
        change = change.replace("bảy","7");
        change = change.replace("tám","8");
        change = change.replace("chín","9");
        change = change.replace("Một","1");
        change = change.replace("Hai","2");
        change = change.replace("Ba","3");
        change = change.replace("Bốn","4");
        change = change.replace("Năm","5");
        change = change.replace("Sáu","6");
        change = change.replace("Bảy","7");
        change = change.replace("Tám","8");
        change = change.replace("Chín","9");
        change = change.replace("Mở ngoặc","(");
        change = change.replace("mở ngoặc","(");
        change = change.replace("Đóng ngoặc",")");
        change = change.replace("đóng ngoặc",")");
        change = change.replace("Bằng","=");
        change = change.replace("bằng","=");
        change = change.replace("c","C");
        change = change.replace("Ac","A");
        change = change.replace("Xóa","C");
        change = change.replace("xóa","C");
        change = change.replace("Xóa hết","A");
        change = change.replace("xóa hết","A");
        change = change.replace("x","*");




        return change;
    }


}
