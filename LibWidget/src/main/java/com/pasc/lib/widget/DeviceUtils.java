package com.pasc.lib.widget;

public class DeviceUtils {

    public static boolean isMui5(){
        boolean isMui5 = false;
        String product = android.os.Build.PRODUCT;
        if(product.equals("meizu_15_CN")){
            isMui5 =true;
        }
        return isMui5;
    }

}
