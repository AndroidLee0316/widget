package com.pasc.lib.widget.dialog.bottompicker.bean;
import java.util.List;

public class AreaItem {

    public String codeid;
    public String parentid;
    public String cityName;

    public List<SecondAreaItem> children;

    @Override
    public String toString() {
        return cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<SecondAreaItem> getChildren() {
        return children;
    }

    public void setChildren(List<SecondAreaItem> children) {
        this.children = children;
    }
}
