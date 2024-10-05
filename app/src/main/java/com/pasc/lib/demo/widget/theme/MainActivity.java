package com.pasc.lib.demo.widget.theme;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.theme.SkinCompatManager;
import com.pasc.lib.widget.theme.utils.SkinPreference;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

@Route(path = "/Demo/Others/SkinCompatManager")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_theme);

        Button currentColorButton = findViewById(R.id.currentColorButton);
        currentColorButton.setTextColor(getResources().getColor(R.color.pasc_success));


        final Button changeThemeButton = findViewById(R.id.changeThemeButton);
        changeThemeButton.setOnClickListener(new View.OnClickListener() {

            int count = 0;

            @Override
            public void onClick(View v) {
                count++;
                String themeName = count % 2 == 1 ? "one" : "two";
                int color = getThemeColor(MainActivity.this, themeName, R.color.pasc_success);
                changeThemeButton.setTextColor(color);
                findViewById(R.id.colorView).setBackgroundColor(color);

                ImageView imageView = findViewById(R.id.imageView);
                Drawable drawable = getResources().getDrawable(R.drawable.ic_qq_zone);
                imageView.setImageDrawable(drawable);
            }
        });

        final RadioGroup themeRadioGroup = (RadioGroup) findViewById(R.id.themeRadioGroup);
        String skinName = SkinPreference.getInstance().getSkinName();
        if ("night".equals(skinName)) {
            themeRadioGroup.check(R.id.nightRadioButton);
        } else if ("demoTP.skin".equals(skinName)) {
            themeRadioGroup.check(R.id.redThemeRadioButton);
        } else {
            themeRadioGroup.check(R.id.defaultRadioButton);
        }

        themeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.defaultRadioButton) {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    Toast.makeText(MainActivity.this, "恢复默认主题", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.nightRadioButton) {
                    SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                    Toast.makeText(MainActivity.this, "切换到夜间模式", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.redThemeRadioButton) {
                    SkinCompatManager.getInstance().loadSkin("demoTP.skin", SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
                    Toast.makeText(MainActivity.this, "切换到红色主题", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static Drawable getThemeDrawable(Context context, String themeName, @DrawableRes int resDrawable) {
        InputStream inputStream = null;
        try {
            String fileName = "theme/" + themeName + "/res/values/theme_colors.xml";
            inputStream = context.getResources().getAssets().open(fileName);

            String resourceEntryName = context.getResources().getResourceEntryName(resDrawable);

            String colorStr = getColorFromInputStream(inputStream, resourceEntryName);
            System.out.println("colorStr=" + colorStr);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return context.getResources().getDrawable(resDrawable);
    }

    private static int getThemeColor(Context context, String themeName, @ColorRes int resColor) {
        InputStream inputStream = null;
        try {
            String fileName = "theme/" + themeName + "/res/values/theme_colors.xml";
            inputStream = context.getResources().getAssets().open(fileName);

            String resourceEntryName = context.getResources().getResourceEntryName(resColor);

            String colorStr = getColorFromInputStream(inputStream, resourceEntryName);
            System.out.println("colorStr=" + colorStr);

            return Color.parseColor(colorStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return context.getResources().getColor(resColor);
    }

    private static String getColorFromInputStream(InputStream inputStream, String colorName) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, "utf-8");

        int eventType = xmlPullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                String name = xmlPullParser.getName();
                if ("color".equals(name)) {
                    String colorNameFile = xmlPullParser.getAttributeValue(null, "name");
                    if (colorNameFile.equals(colorName)) {
                        String colorValue = xmlPullParser.nextText();
                        return colorValue;
                    }
                }
            }
            eventType = xmlPullParser.next();
        }
        return null;
    }
}
