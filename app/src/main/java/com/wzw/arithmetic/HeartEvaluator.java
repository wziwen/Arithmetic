package com.wzw.arithmetic;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class HeartEvaluator implements TypeEvaluator<PointF> {
    //贝塞尔曲线参考点1
    PointF f1;
    //贝塞尔曲线参考点2
    PointF f2;
    public HeartEvaluator(PointF f1, PointF f2) {
        this.f1=f1;
        this.f2=f2;
    }
    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float leftTime=1f-fraction;
        PointF newPointF=new PointF();
        newPointF.x=startValue.x*leftTime*leftTime*leftTime
                +f1.x*3*leftTime*leftTime*fraction
                +f2.x*3*leftTime*fraction*fraction
                +endValue.x*fraction*fraction*fraction;
        newPointF.y=startValue.y*leftTime*leftTime*leftTime
                +f1.y*3*leftTime*leftTime*fraction
                +f2.y*3*leftTime*fraction*fraction
                +endValue.y*fraction*fraction*fraction;
        return newPointF;
    }
}

//作者：r17171709
//        链接：http://www.jianshu.com/p/9423ca99c303
//        來源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
