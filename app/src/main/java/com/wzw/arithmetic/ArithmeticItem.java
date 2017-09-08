package com.wzw.arithmetic;

import static com.wzw.arithmetic.NumberText.Lang.ChineseSimplified;

/**
 * Created by ziwen.wen on 2017/9/8.
 */
public class ArithmeticItem {
    int x;
    int y;
    int type;
    int result;

    String chineseResult;

    public ArithmeticItem(int x, int y, int type, int result) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.result = result;
        chineseResult = NumberText.getInstance(NumberText.Lang.ChineseSimplified).getText(result);

    }

    public static String[] TYPES = {"＋", "－", "×", "÷"};

    public String toCalculateString() {
        return x + TYPES[type] + y + "=";
    }


}
