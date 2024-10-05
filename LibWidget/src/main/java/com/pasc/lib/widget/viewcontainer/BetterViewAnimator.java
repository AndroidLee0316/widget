package com.pasc.lib.widget.viewcontainer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

/**
 * Created by zhangxu678 on 2018/9/5.
 */
public class BetterViewAnimator extends ViewAnimator {
  public BetterViewAnimator(Context context, AttributeSet attrs) {
    super(context, attrs);
    //setAnimateFirstView(false);
  }

  protected void setDisplayedChildId(int id) {
    if (getDisplayedChildId() == id) {
      return;
    }
    for (int i = 0, count = getChildCount(); i < count; i++) {
      if (getChildAt(i).getId() == id) {
        setDisplayedChild(i);
        return;
      }
    }
    String name = getResources().getResourceEntryName(id);
    throw new IllegalArgumentException("No view with ID " + name);
  }

  public int getDisplayedChildId() {
    return getChildAt(getDisplayedChild()).getId();
  }
}
