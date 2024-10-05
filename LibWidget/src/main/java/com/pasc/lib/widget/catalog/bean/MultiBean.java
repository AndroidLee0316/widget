package com.pasc.lib.widget.catalog.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chendaixi947 on 2019/4/25
 *
 * @since 1.0
 */
public class MultiBean implements Serializable {
    private String leftName;
    private List<CharSequence> rightDatas;

    public MultiBean(String leftName, List<CharSequence> rightDatas) {
        this.leftName = leftName;
        this.rightDatas = rightDatas;
    }

    public String getLeftName() {
        return leftName;
    }

    public List<CharSequence> getRightDatas() {
        return rightDatas;
    }

}
