package com.wzw.arithmetic;

/**
 * Created by ziwen.wen on 2017/9/8.
 */
public class ArithmeticItem {
    int x;
    int y;
    int type;
    int result;

    public ArithmeticItem(int x, int y, int type, int result) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.result = result;
    }

    public static String[] TYPES = {"＋", "－", "×", "÷"};

    public String toCalculateString() {
        return x + TYPES[type] + y + "=";
    }


}
