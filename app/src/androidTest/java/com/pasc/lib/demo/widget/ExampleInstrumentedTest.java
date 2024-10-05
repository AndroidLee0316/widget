package com.pasc.lib.demo.widget;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;

import com.pasc.lib.demo.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.pasc.lib.demo", appContext.getPackageName());
    }

    @Test
    public void testResourceName() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        String resourceName = appContext.getResources().getResourceName(R.drawable.bg_close);
        assertEquals("com.pasc.lib.demo:drawable/bg_close", resourceName);

        String resourceEntryName = appContext.getResources().getResourceEntryName(R.drawable.bg_close);
        assertEquals("bg_close", resourceEntryName);

        String resourceTypeName = appContext.getResources().getResourceTypeName(R.drawable.bg_close);
        assertEquals("drawable", resourceTypeName);

        String resourcePackageName = appContext.getResources().getResourcePackageName(R.drawable.bg_close);
        assertEquals("com.pasc.lib.demo", resourcePackageName);
    }

    @Test
    public void testDrawable() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        float density = appContext.getResources().getDisplayMetrics().density;
        System.out.println("density=" + density);
        float densityDpi = appContext.getResources().getDisplayMetrics().densityDpi;
        System.out.println("densityDpi=" + densityDpi);

        List<String> drawableFolderNames = getDrawableFolderNames(densityDpi);
        for (String name : drawableFolderNames) {
            System.out.println("name=" + name);
        }
        assertEquals(6, drawableFolderNames.size());
    }

    public static List<String> getDrawableFolderNames(Context context) {
        float densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        return getDrawableFolderNames(densityDpi);
    }

    public static List<String> getDrawableFolderNames(float densityDpi) {
        List<String> drawableFolderNames = new ArrayList<String>();
        drawableFolderNames.add("drawable");
        if (densityDpi >= DisplayMetrics.DENSITY_LOW) {
            drawableFolderNames.add(0, "drawable-ldpi");
        }
        if (densityDpi >= DisplayMetrics.DENSITY_MEDIUM) {
            drawableFolderNames.add(0, "drawable-mdpi");
        }
        if (densityDpi >= DisplayMetrics.DENSITY_HIGH) {
            drawableFolderNames.add(0, "drawable-hdpi");
        }
        if (densityDpi >= DisplayMetrics.DENSITY_XHIGH) {
            drawableFolderNames.add(0, "drawable-xhdpi");
        }
        if (densityDpi >= DisplayMetrics.DENSITY_XXHIGH) {
            drawableFolderNames.add(0, "drawable-xxhdpi");
        }
        if (densityDpi >= DisplayMetrics.DENSITY_XXXHIGH) {
            drawableFolderNames.add(0, "drawable-xxxhdpi");
        }
        return drawableFolderNames;
    }

    public static int getThemeColor(Context context, @ColorRes int colorRes) {
        return 0;
    }

    // 从文件获取颜色值
    public static String getColorFromFile(File file, String colorName) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);

            String colorValue = getColorFromInputStream(inputStream, colorName);
            if (colorValue != null) {
                return colorValue;
            }
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
        return null;
    }

    // 从输入流获取颜色值
    @Nullable
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

    // 测试获取主题颜色
    @Test
    public void testGetColor() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        // 原生获取的颜色
        int color = appContext.getColor(R.color.pasc_success);

        InputStream inputStream = null;
        try {
            inputStream = appContext.getResources().getAssets().open("colors.xml");
            String colorStr = getColorFromInputStream(inputStream, "pasc_success");
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
    }
}
