package com.pasc.lib.widget.catalog.bean;

import java.util.ArrayList;

public class CatalogDataBean {
    private String directoryID;
    private String title;
    private ArrayList<SubCatalogDataBean> subList;

    public String getDirectoryID() {
        return directoryID;
    }

    public void setDirectoryID(String directoryID) {
        this.directoryID = directoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<SubCatalogDataBean> getSubList() {
        return subList;
    }

    public void setSubList(ArrayList<SubCatalogDataBean> subList) {
        this.subList = subList;
    }
}
