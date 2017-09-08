package com.wzw.arithmetic;

import java.util.Random;

/**
 * Created by ziwen.wen on 2017/9/8.
 */
public class QuestionGenerator {
    // 加减乘除
    public static int TYPE_PLUS = 0;
    public static int TYPE_SUB = 1;
    public static int TYPE_MULTIPLY = 2;
    public static int TYPE_DIVIDE = 3;

    /**
     * 生成包括的范围四则算法
     */
    int[] types;
    /**
     * 算法最大值(控制10以内, 100以内)
     */
    int MAX_VALUE = 100;
    Random random = new Random();

    public QuestionGenerator(int maxValue, int... types) {
        this.MAX_VALUE = maxValue;
        this.types = types;
    }

    public ArithmeticItem generate() {
        // 生成算术
        int x = random.nextInt(MAX_VALUE);
        int y = random.nextInt(MAX_VALUE);

        // 生成四则运算符
        int type = types[random.nextInt(types.length)];

        int result;
        // 根据最大值调整
        // 加
        if (TYPE_PLUS == type) {
            result = x + y;
            // 超过最大值时, 把结果倒转一下
            if (result > MAX_VALUE) {
                result = Math.max(x, y);
                x = Math.min(x, y);
                y = result - x;
            }
            return new ArithmeticItem(x, y, type, result);
        }

        // 减
        if (TYPE_SUB == type) {
            x = Math.max(x, y);
            y = Math.min(x, y);
            result = x - y;
            return new ArithmeticItem(x, y, type, result);
        }

        // 乘
        if (TYPE_MULTIPLY == type) {
            int max = (int) Math.sqrt(MAX_VALUE);
            x = random.nextInt(max);
            y = random.nextInt(max);
            result = x * y;
            return new ArithmeticItem(x, y, type, result);
        }

        // 除
        if (TYPE_DIVIDE == type) {
            // 除 计算和 乘 一样, 只是最后结果倒转一下
            int max = (int) Math.sqrt(MAX_VALUE);
            x = random.nextInt(max);
            y = random.nextInt(max);
            // 除法不能为0
            if (x == 0) {
                x = 1;
            }
            if (y == 0) {
                y = 1;
            }
            result = x * y;

            return new ArithmeticItem(result, y, type, x);
        }

        throw new NullPointerException();
    }

    public static void main(String[] args) {
        QuestionGenerator questionGenerator = new QuestionGenerator(10, 0, 1, 2,3);
        for (int i = 0; i < 10; i ++) {
            ArithmeticItem item = questionGenerator.generate();
            System.out.println(item.toCalculateString() + item.result);
        }

        System.out.print("\n\n");
        questionGenerator = new QuestionGenerator(100, 0, 1, 2,3);
        for (int i = 0; i < 10; i ++) {
            ArithmeticItem item = questionGenerator.generate();
            System.out.println(item.toCalculateString() + item.result);
        }

        System.out.print("\n\n");
        questionGenerator = new QuestionGenerator(1000, 0, 1, 2,3);
        for (int i = 0; i < 10; i ++) {
            ArithmeticItem item = questionGenerator.generate();
            System.out.println(item.toCalculateString() + item.result);
        }
    }

}
