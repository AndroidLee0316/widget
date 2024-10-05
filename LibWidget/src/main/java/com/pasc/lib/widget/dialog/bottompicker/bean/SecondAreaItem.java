package com.pasc.lib.widget.dialog.bottompicker.bean;

import java.util.List;


public class SecondAreaItem  {

    public String codeid;
    public String parentid;
    public String cityName;

    public List<ThirdAreaItem> children;

    @Override
    public String toString() {
        return cityName;
    }

    public List<ThirdAreaItem> getChildren() {
        return children;
    }

    public void setChildren(List<ThirdAreaItem> children) {
        this.children = children;
    }
}
