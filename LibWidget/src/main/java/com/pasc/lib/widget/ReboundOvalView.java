package com.pasc.lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目中没用到这个类
 */
@Deprecated
public class ReboundOvalView extends View {
    private Paint paint = null;
    private Context context;
    private int width;
    private int height;
    private int arcWidth;

    public ReboundOvalView(Context context) {
        super(context);
        init(context);
    }

    public ReboundOvalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ReboundOvalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        width = 300;
        height = 300;
        arcWidth = 200;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        RectF rect = new RectF(width - DensityUtils.dp2px(41), 0, width,
                DensityUtils.dp2px(111));
        paint.setColor(Color.parseColor("#f0f5ff"));
        canvas.drawRect(rect, paint);

//        Path path = new Path();
//        RectF rectf = new RectF(0, 0, width*2-SizeUtils.dp2px(41),
//                SizeUtils.dp2px(111));
//        path.arcTo(rectf, 90, 180);
//        paint.setColor(Color.parseColor("#f0f5ff"));
//        canvas.drawPath(path, paint);

        Path path = new Path();
        //PascLog.i("ReboundOvalView", "widthdp2px" + width + ", " + DensityUtils.dp2px(41) * 2);
        int move = width - DensityUtils.dp2px(41) > 0 ? width - DensityUtils.dp2px(41) : width - DensityUtils.dp2px(41);
        path.moveTo(move, 0);
        path.rQuadTo(-width + move / 2, DensityUtils.dp2px(111) / 2, 0, DensityUtils.dp2px(111));
        paint.setColor(Color.parseColor("#f0f5ff"));
        canvas.drawPath(path, paint);

        paint.setTextSize(DensityUtils.dp2px(12));
        paint.setColor(Color.parseColor("#6a6a6a"));
//        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
//        int baseline = (int)(rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
//        canvas.translate(getHeight(), 0);
//        canvas.rotate(90);
//        canvas.clipRect(0, 0, getWidth(), getHeight(), android.graphics.Region.Op.REPLACE);
//        canvas.drawText("查看更多", rect.centerX(), baseline, paint);
        String str;
        if (width > DensityUtils.dp2px(61)) {
            str = "松开查看";
        } else {
            str = "查看更多";
        }
        canvas.drawPosText(str, new float[]{
                width - DensityUtils.dp2px(24), DensityUtils.dp2px(35),    // 第一个字符位置
                width - DensityUtils.dp2px(24), DensityUtils.dp2px(50),    // 第二个字符位置
                width - DensityUtils.dp2px(24), DensityUtils.dp2px(65),    // ...
                width - DensityUtils.dp2px(24), DensityUtils.dp2px(80)
        }, paint);

    }

    public void setWidth(int width) {
        this.width = width;
    }

}
