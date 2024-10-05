package com.pasc.lib.widget.catalog;

import com.pasc.lib.widget.catalog.bean.CatalogDataBean;

import java.util.ArrayList;

public class CatalogController {


    private int currentSelectTextColor;
    private int currentSelectbackground;
    private ArrayList<CatalogDataBean> directoryDataList;

    public int getCurrentSelectTextColor() {
        return currentSelectTextColor;
    }

    public void setCurrentSelectTextColor(int currentSelectTextColor) {
        this.currentSelectTextColor = currentSelectTextColor;
    }

    public int getCurrentSelectbackground() {
        return currentSelectbackground;
    }

    public void setCurrentSelectbackground(int currentSelectbackground) {
        this.currentSelectbackground = currentSelectbackground;
    }

    public ArrayList<CatalogDataBean> getDirectoryDataList() {
        return directoryDataList;
    }

    public void setDirectoryDataList(ArrayList<CatalogDataBean> directoryDataList) {
        this.directoryDataList = directoryDataList;
    }

    public static class ControllerParams{

        public int currentSelectTextColor;
        public int currentSelectbackground;
        public ArrayList<CatalogDataBean> directoryDataList;

        public void apply(CatalogController controller) {

            controller.setCurrentSelectTextColor(currentSelectTextColor);
            controller.setCurrentSelectbackground(currentSelectbackground);
            controller.setDirectoryDataList(directoryDataList);


        }
    }
}
