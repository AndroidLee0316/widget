package com.pasc.lib.widget.catalog.bean;

public class SubCatalogDataBean {

    private String subDirectoryID;
    private String subTitle;

    public SubCatalogDataBean(String mSubTItle){
        this.subTitle = mSubTItle;

    }

    public String getSubDirectoryID() {
        return subDirectoryID;
    }

    public void setSubDirectoryID(String subDirectoryID) {
        this.subDirectoryID = subDirectoryID;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
