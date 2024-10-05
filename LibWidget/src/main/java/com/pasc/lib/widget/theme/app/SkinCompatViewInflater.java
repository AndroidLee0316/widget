package com.pasc.lib.widget.theme.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.pasc.lib.widget.theme.SkinCompatManager;
import com.pasc.lib.widget.theme.widget.SkinCompatAutoCompleteTextView;
import com.pasc.lib.widget.theme.widget.SkinCompatButton;
import com.pasc.lib.widget.theme.widget.SkinCompatCheckBox;
import com.pasc.lib.widget.theme.widget.SkinCompatCheckedTextView;
import com.pasc.lib.widget.theme.widget.SkinCompatEditText;
import com.pasc.lib.widget.theme.widget.SkinCompatFrameLayout;
import com.pasc.lib.widget.theme.widget.SkinCompatImageButton;
import com.pasc.lib.widget.theme.widget.SkinCompatImageView;
import com.pasc.lib.widget.theme.widget.SkinCompatLinearLayout;
import com.pasc.lib.widget.theme.widget.SkinCompatMultiAutoCompleteTextView;
import com.pasc.lib.widget.theme.widget.SkinCompatProgressBar;
import com.pasc.lib.widget.theme.widget.SkinCompatRadioButton;
import com.pasc.lib.widget.theme.widget.SkinCompatRadioGroup;
import com.pasc.lib.widget.theme.widget.SkinCompatRatingBar;
import com.pasc.lib.widget.theme.widget.SkinCompatRelativeLayout;
import com.pasc.lib.widget.theme.widget.SkinCompatScrollView;
import com.pasc.lib.widget.theme.widget.SkinCompatSeekBar;
import com.pasc.lib.widget.theme.widget.SkinCompatSpinner;
import com.pasc.lib.widget.theme.widget.SkinCompatTextView;
import com.pasc.lib.widget.theme.widget.SkinCompatToolbar;
import com.pasc.lib.widget.theme.widget.SkinCompatView;



public class SkinCompatViewInflater {
    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = new int[]{android.R.attr.onClick};

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    private static final String LOG_TAG = "SkinCompatViewInflater";

    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new ArrayMap<>();

    private final Object[] mConstructorArgs = new Object[2];

    public final View createView(View parent, final String name, @NonNull Context context,
                                 @NonNull AttributeSet attrs, boolean inheritContext,
                                 boolean readAndroidTheme, boolean readAppTheme, boolean wrapContext) {
        final Context originalContext = context;



        if (inheritContext && parent != null) {
            context = parent.getContext();
        }
        if (readAndroidTheme || readAppTheme) {

            context = themifyContext(context, attrs, readAndroidTheme, readAppTheme);
        }
        if (wrapContext) {
            context = TintContextWrapper.wrap(context);
        }

        View view = createViewFromHackInflater(context, name, attrs);


        if (view == null) {
            view = createViewFromFV(context, name, attrs);
        }

        if (view == null) {
            view = createViewFromV7(context, name, attrs);
        }

        if (view == null) {
            view = createViewFromInflater(context, name, attrs);
        }

        if (view == null) {
            view = createViewFromTag(context, name, attrs);
        }

        if (view != null) {

            checkOnClickListener(view, attrs);
        }

        return view;
    }

    private View createViewFromHackInflater(Context context, String name, AttributeSet attrs) {
        View view = null;
        for (SkinLayoutInflater inflater : SkinCompatManager.getInstance().getHookInflaters()) {
            view = inflater.createView(context, name, attrs);
            if (view == null) {
                continue;
            } else {
                break;
            }
        }
        return view;
    }

    private View createViewFromFV(Context context, String name, AttributeSet attrs) {
        View view = null;
        if (name.contains(".")) {
            return null;
        }
        switch (name) {
            case "View":
                view = new SkinCompatView(context, attrs);
                break;
            case "LinearLayout":
                view = new SkinCompatLinearLayout(context, attrs);
                break;
            case "RelativeLayout":
                view = new SkinCompatRelativeLayout(context, attrs);
                break;
            case "FrameLayout":
                view = new SkinCompatFrameLayout(context, attrs);
                break;
            case "TextView":
                view = new SkinCompatTextView(context, attrs);
                break;
            case "ImageView":
                view = new SkinCompatImageView(context, attrs);
                break;
            case "Button":
                view = new SkinCompatButton(context, attrs);
                break;
            case "EditText":
                view = new SkinCompatEditText(context, attrs);
                break;
            case "Spinner":
                view = new SkinCompatSpinner(context, attrs);
                break;
            case "ImageButton":
                view = new SkinCompatImageButton(context, attrs);
                break;
            case "CheckBox":
                view = new SkinCompatCheckBox(context, attrs);
                break;
            case "RadioButton":
                view = new SkinCompatRadioButton(context, attrs);
                break;
            case "RadioGroup":
                view = new SkinCompatRadioGroup(context, attrs);
                break;
            case "CheckedTextView":
                view = new SkinCompatCheckedTextView(context, attrs);
                break;
            case "AutoCompleteTextView":
                view = new SkinCompatAutoCompleteTextView(context, attrs);
                break;
            case "MultiAutoCompleteTextView":
                view = new SkinCompatMultiAutoCompleteTextView(context, attrs);
                break;
            case "RatingBar":
                view = new SkinCompatRatingBar(context, attrs);
                break;
            case "SeekBar":
                view = new SkinCompatSeekBar(context, attrs);
                break;
            case "ProgressBar":
                view = new SkinCompatProgressBar(context, attrs);
                break;
            case "ScrollView":
                view = new SkinCompatScrollView(context, attrs);
                break;
        }
        return view;
    }

    private View createViewFromV7(Context context, String name, AttributeSet attrs) {
        View view = null;
        switch (name) {
            case "android.support.v7.widget.Toolbar":
                view = new SkinCompatToolbar(context, attrs);
                break;
        }
        return view;
    }

    private View createViewFromInflater(Context context, String name, AttributeSet attrs) {
        View view = null;
        for (SkinLayoutInflater inflater : SkinCompatManager.getInstance().getInflaters()) {
            view = inflater.createView(context, name, attrs);
            if (view == null) {
                continue;
            } else {
                break;
            }
        }
        return view;
    }

    public View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createView(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {


            return null;
        } finally {

            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }


    private void checkOnClickListener(View view, AttributeSet attrs) {
        final Context context = view.getContext();

        if (!(context instanceof ContextWrapper) ||
                (Build.VERSION.SDK_INT >= 15 && !ViewCompat.hasOnClickListeners(view))) {



            return;
        }

        final TypedArray a = context.obtainStyledAttributes(attrs, sOnClickAttrs);
        final String handlerName = a.getString(0);
        if (handlerName != null) {
            view.setOnClickListener(new DeclaredOnClickListener(view, handlerName));
        }
        a.recycle();
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {

                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {


            return null;
        }
    }


    private static Context themifyContext(Context context, AttributeSet attrs,
                                          boolean useAndroidTheme, boolean useAppTheme) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.View, 0, 0);
        int themeId = 0;
        if (useAndroidTheme) {

            themeId = a.getResourceId(R.styleable.View_android_theme, 0);
        }
        if (useAppTheme && themeId == 0) {

            themeId = a.getResourceId(R.styleable.View_theme, 0);

            if (themeId != 0) {
                Log.i(LOG_TAG, "app:theme is now deprecated. "
                        + "Please move to using android:theme instead.");
            }
        }
        a.recycle();

        if (themeId != 0 && (!(context instanceof ContextThemeWrapper)
                || ((ContextThemeWrapper) context).getThemeResId() != themeId)) {


            context = new ContextThemeWrapper(context, themeId);
        }
        return context;
    }


    private static class DeclaredOnClickListener implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;

        private Method mResolvedMethod;
        private Context mResolvedContext;

        public DeclaredOnClickListener(@NonNull View hostView, @NonNull String methodName) {
            mHostView = hostView;
            mMethodName = methodName;
        }

        @Override
        public void onClick(@NonNull View v) {
            if (mResolvedMethod == null) {
                resolveMethod(mHostView.getContext(), mMethodName);
            }

            try {
                mResolvedMethod.invoke(mResolvedContext, v);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(
                        "Could not execute method for android:onClick", e);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context context, @NonNull String name) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        final Method method = context.getClass().getMethod(mMethodName, View.class);
                        if (method != null) {
                            mResolvedMethod = method;
                            mResolvedContext = context;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {

                }

                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {

                    context = null;
                }
            }

            final int id = mHostView.getId();
            final String idText = id == View.NO_ID ? "" : " with id '"
                    + mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + mMethodName
                    + "(View) in a parent or ancestor Context for android:onClick "
                    + "attribute defined on view " + mHostView.getClass() + idText);
        }
    }
}
