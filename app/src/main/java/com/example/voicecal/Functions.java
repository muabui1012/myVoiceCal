package com.example.voicecal;

public class Functions {

    public static int cal(String exp) {

        return 0;
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

        return change;
    }


}
