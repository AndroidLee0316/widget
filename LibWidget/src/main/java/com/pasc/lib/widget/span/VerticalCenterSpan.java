package com.pasc.lib.widget.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;

import com.pasc.lib.widget.DensityUtils;

/**
 * Created by huanglihou519 on 2018/3/5.
 */

public class VerticalCenterSpan extends ReplacementSpan {
    private int fontSizeSp;    //字体大小sp

    public VerticalCenterSpan(int fontSizeSp) {
        this.fontSizeSp = fontSizeSp;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fm) {
        text = text.subSequence(start, end);
        Paint p = getCustomTextPaint(paint);
        return (int) p.measureText(text.toString());
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        text = text.subSequence(start, end);
        Paint p = getCustomTextPaint(paint);
        Paint.FontMetricsInt fm = p.getFontMetricsInt();
        canvas.drawText(text.toString(), x,
                y - ((y + fm.descent + y + fm.ascent) / 2 - (bottom + top) / 2),
                p);    //此处重新计算y坐标，使字体居中
    }

    private TextPaint getCustomTextPaint(Paint srcPaint) {
        TextPaint paint = new TextPaint(srcPaint);
        paint.setTextSize(DensityUtils.sp2px(fontSizeSp));   //设定字体大小, sp转换为px
        return paint;
    }
}
