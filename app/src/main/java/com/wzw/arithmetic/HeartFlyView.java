package com.wzw.arithmetic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by ziwen.wen on 2017/9/8.
 */
public class HeartFlyView extends RelativeLayout {

    int heartViewCount = 35;
    HeartImageView[] imageViews;

    public HeartFlyView(Context context) {
        super(context);
    }

    public HeartFlyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeartFlyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        int[] colors = {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.GRAY};
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imageViews = new HeartImageView[heartViewCount];
        for (int i = 0; i < heartViewCount; i++) {
            HeartImageView imageView = new HeartImageView(getContext());
            imageView.setColor(colors[new Random().nextInt(colors.length)]);
            imageView.setVisibility(INVISIBLE);
            addView(imageView, params);
            imageViews[i] = imageView;
        }

        super.onFinishInflate();
    }

    float x;
    float y;
    public void startAnimation() {
        setVisibility(VISIBLE);
        postDelayed(new Runnable() {
            @Override
            public void run() {
            Animator[] animators = new Animator[heartViewCount];
            for (int i = 0; i < heartViewCount; i ++) {
                animators[i] = createAnimation(imageViews[i], i * 80);
            }
            AnimatorSet finalAnimation = new AnimatorSet();
            finalAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    for (int i = 0; i < heartViewCount; i ++) {
                        imageViews[i].setX(x);
                        imageViews[i].setY(y);
                        imageViews[i].setVisibility(INVISIBLE);
                    }
                    setVisibility(GONE);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    x = imageViews[0].getX();
                    y = imageViews[0].getY();
                }
            });
            finalAnimation.playTogether(animators);
            finalAnimation.start();
            }
        }, 100);
    }

    private Animator createAnimation(final HeartImageView imageView, int delay) {

        ObjectAnimator noOpAnimator = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0.5f, 1f);
        noOpAnimator.setDuration(delay);
        noOpAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                imageView.setVisibility(VISIBLE);
            }
        });

//        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0.5f, 1f);
//        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0.5f, 1f);
//        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0.5f, 1f);
//        AnimatorSet enterAnimatorSet = new AnimatorSet();
//        enterAnimatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
//        enterAnimatorSet.setDuration(250);
//        enterAnimatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                imageView.setVisibility(VISIBLE);
//            }
//        });

        int width = getWidth();
        int height = getHeight();
        Context context = getContext();
        int[] randomArray = {0, 1};
        int point1x = 0;
        int point1y = 0;
        int point2x = 0;
        int point2y = 0;
        if (randomArray[new Random().nextInt(2)] == 0) {
            point1x = new Random().nextInt((width / 2 - dp2px(context, 50)));
        } else {
            point1x = new Random().nextInt((width / 2 - dp2px(context, 50))) + (width / 2 + dp2px(context, 50));
        }
        if (randomArray[new Random().nextInt(2)] == 0) {
            point2x = new Random().nextInt((width / 2 - dp2px(context, 50)));
        } else {
            point2x = new Random().nextInt((width / 2 - dp2px(context, 50))) + (width / 2 + dp2px(context, 50));
        }
        point1y = new Random().nextInt(height / 2 - dp2px(context, 50)) + (height / 2 + dp2px(context, 50));
        point2y = -new Random().nextInt(point1y) + point1y;
        int endX = new Random().nextInt(dp2px(context, 100)) + (width / 2 - dp2px(context, 100));
        int endY = -new Random().nextInt(point2y) + point2y;
        ValueAnimator translateAnimator = ValueAnimator.ofObject(new HeartEvaluator(new PointF(point1x, point1y), new PointF(point2x, point2y)), new PointF(width / 2 - imageView.getWidth() / 2, height - imageView.getHeight()), new PointF(endX, endY));
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });
        translateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                removeView(imageView);
            }
        });
        TimeInterpolator[] timeInterpolator = {new LinearInterpolator(), new AccelerateDecelerateInterpolator(), new DecelerateInterpolator(), new AccelerateInterpolator()};
        translateAnimator.setInterpolator(timeInterpolator[new Random().nextInt(timeInterpolator.length)]);
        ObjectAnimator translateAlphaAnimator = ObjectAnimator.ofFloat(imageView, View.ALPHA, 1f, 0f);
        translateAlphaAnimator.setInterpolator(new DecelerateInterpolator());
        AnimatorSet translateAnimatorSet = new AnimatorSet();
        translateAnimatorSet.playTogether(translateAnimator, translateAlphaAnimator);
        translateAnimatorSet.setDuration(1000);

        AnimatorSet allAnimator = new AnimatorSet();
        allAnimator.playSequentially(noOpAnimator, translateAnimatorSet);
        return allAnimator;
    }

    private int dp2px(Context context, int value) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

}
