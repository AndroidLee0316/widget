package com.pasc.lib.widget.theme.widget;




public abstract class SkinCompatHelper {
    protected static final String SYSTEM_ID_PREFIX = "1";
    public static final int INVALID_ID = 0;

    final static public int checkResourceId(int resId) {
        String hexResId = Integer.toHexString(resId);
        return hexResId.startsWith(SYSTEM_ID_PREFIX) ? INVALID_ID : resId;
    }

    abstract public void applySkin();
}
