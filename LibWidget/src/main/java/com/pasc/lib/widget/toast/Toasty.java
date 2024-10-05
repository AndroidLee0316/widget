package com.pasc.lib.widget.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pasc.lib.widget.DensityUtils;
import com.pasc.lib.widget.R;

@SuppressLint("InflateParams")
public class Toasty {

    @ColorInt
    private static int DEFAULT_TEXT_COLOR = Color.parseColor("#deffffff");

    @ColorInt
    private static int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#de333333");

    private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    private static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    private static int textSize = 17; // in SP

    private static boolean tintIcon = true;

    Context context; // 上下文
    CharSequence message; // 消息
    boolean withIcon = false; // 是否带上图标
    int duration = Toast.LENGTH_LONG; // 消息停留时长
    int color = DEFAULT_BACKGROUND_COLOR; // 默认颜色
    boolean shouldTint = true; // 是否着色背景
    int gravity = Gravity.CENTER; // 位置
    float alpha = 0.87f; // 不透明度

    int iconRes = R.drawable.ic_toast_info; // 图标资源
    Drawable icon; // 图标对象

    private Toasty(Context context) {
        this.context = context;
    }

    public static Toasty init(Context context) {
        return new Toasty(context);
    }

    public Toasty setMessage(CharSequence msg) {
        this.message = msg;
        return this;
    }

    /**
     * 设置不透明度，0~1，0是完全透明，1是不透明.
     *
     * @param alpha 不透明度.
     * @return Toasty对象.
     */
    public Toasty setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public void show() {
        if (context == null) return;
        if (TextUtils.isEmpty(message)) return;

        if (icon == null) {
            icon = ToastyUtils.getDrawable(context, iconRes);
        }
        custom(context, message, icon, color, duration, withIcon, shouldTint, gravity, alpha).show();
    }

    private Toast custom(Context context, CharSequence message, Drawable icon, int tintColor, int duration, boolean withIcon, boolean shouldTint, int gravity, float alpha) {
        final Toast currentToast = Toast.makeText(context, "", duration);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.toast_layout, null);
        toastLayout.setAlpha(0.87f);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        LinearLayout toastRootLiner = toastLayout.findViewById(R.id.toast_root);

        Drawable drawableFrame;

        if (shouldTint)
            drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
        else
            drawableFrame = ToastyUtils.getDrawable(context, R.drawable.bg_toast);
        ToastyUtils.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            if (tintIcon)
                icon = ToastyUtils.tintIcon(icon, DEFAULT_TEXT_COLOR);
            toastIcon.setImageDrawable(icon);
            toastRootLiner.setPadding(DensityUtils.dp2px(24), DensityUtils.dp2px(10),
                    DensityUtils.dp2px(24), DensityUtils.dp2px(10));
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setText(message);
        toastTextView.setTextColor(DEFAULT_TEXT_COLOR);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(textSize);

        currentToast.setView(toastLayout);

        int offsetValue = 0;
        if (gravity == Gravity.BOTTOM || gravity == Gravity.TOP) {
            offsetValue = 64;
        } else {
            offsetValue = 0;
        }
        int yOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, offsetValue, context.getResources().getDisplayMetrics());
        currentToast.setGravity(gravity, 0, yOffset);
        return currentToast;
    }

    public Toasty onTop() {
        return setGravity(Gravity.TOP);
    }

    public Toasty onBottom() {
        return setGravity(Gravity.BOTTOM);
    }

    public Toasty onCenter() {
        return setGravity(Gravity.CENTER);
    }

    private Toasty setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public Toasty withIcon(boolean withIcon) {
        this.withIcon = withIcon;
        return this;
    }

    public Toasty stayShort() {
        return setDuration(Toast.LENGTH_SHORT);
    }

    public Toasty stayLong() {
        return setDuration(Toast.LENGTH_LONG);
    }

    private Toasty setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public Toasty setIconRes(int iconRes) {
        this.iconRes = iconRes;
        return withIcon(true);
    }

    public Toasty renderSuccess() {
        return setIconRes(R.drawable.ic_toast_success).setColor(context.getResources().getColor(R.color.pasc_success));
    }

    public Toasty renderInfo() {
        return setIconRes(R.drawable.ic_toast_info).setColor(context.getResources().getColor(R.color.pasc_clickable));
    }

    public Toasty renderError() {
        return setIconRes(R.drawable.ic_toast_error).setColor(context.getResources().getColor(R.color.pasc_error));
    }

    public Toasty renderWarn() {
        return setIconRes(R.drawable.ic_toast_warn).setColor(context.getResources().getColor(R.color.pasc_warning));
    }

    public Toasty setColor(int color) {
        this.color = color;
        return this;
    }

    public Toasty setIcon(Drawable icon) {
        this.icon = icon;
        return withIcon(true);
    }

    public Toasty withSuccessIcon() {
        return setIconRes(R.drawable.ic_toast_success);
    }

    public Toasty withErrorIcon() {
        return setIconRes(R.drawable.ic_toast_error);
    }
}