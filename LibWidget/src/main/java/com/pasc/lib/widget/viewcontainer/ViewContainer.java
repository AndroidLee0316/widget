package com.pasc.lib.widget.viewcontainer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.pasc.lib.widget.R;

/**
 * loading，error,empty的view切换功能。
 * Created by zhangxu678 on 2018/9/5.
 */
public class ViewContainer extends BetterViewAnimator {
  TextView emptyMessageView;
  ImageView emptyImageView;
  TextView errorMessageView;
  ImageView errorImageView;
  ImageView loadingImageView;
  TextView loadingTextView;
  ImageView networkErrorImageView;
  TextView networkErrorTextView;
  Button btnErrorReload;
  TextView addMessageView;
  ImageView addImageView;
  Button addBtn;
  View emptyView;
  View errorView;
  View loadingView;
  View blankView;
  View networkErrorView;
  View addView;

  /** 重新加载回调 **/
  private OnReloadCallback onReloadCallback = OnReloadCallback.NONE;
  private OnAddCallback onAddCallBack = OnAddCallback.NONE;

  public ViewContainer(Context context, AttributeSet attrs) {
    super(context, attrs);
    inflate(context, R.layout.widget_view_container, this);
    initView();
  }

  private void initView() {
    emptyMessageView = findViewById(R.id.temp_empty_message);
    emptyImageView = findViewById(R.id.temp_empty_image);
    errorImageView = findViewById(R.id.temp_error_image);
    errorMessageView = findViewById(R.id.temp_error_message);
    loadingImageView = findViewById(R.id.temp_loading_image);
    loadingTextView = findViewById(R.id.temp_loading_message);
    networkErrorImageView = findViewById(R.id.temp_network_error_image);
    networkErrorTextView = findViewById(R.id.temp_network_error_message);
    btnErrorReload = findViewById(R.id.temp_btn_error_reload);
    emptyView = findViewById(R.id.temp_empty);
    errorView = findViewById(R.id.temp_error);
    loadingView = findViewById(R.id.temp_loading);
    blankView = findViewById(R.id.temp_blank);
    networkErrorView = findViewById(R.id.temp_network_error);

    addView = findViewById(R.id.temp_add);
    addImageView = findViewById(R.id.temp_add_image);
    addMessageView = findViewById(R.id.temp_add_message);
    addBtn = findViewById(R.id.temp_add_button);

    errorView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onReloadCallback.reload();
      }
    });
    btnErrorReload.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onReloadCallback.reload();
      }
    });
    addBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        onAddCallBack.add();
      }
    });
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
  }

  /**
   * 返回重载回调
   */
  public OnReloadCallback getOnReloadCallback() {
    return onReloadCallback;
  }

  /**
   * 设置重载回调
   *
   * @param onReloadCallback 重载回调
   */
  public void setOnReloadCallback(@NonNull OnReloadCallback onReloadCallback) {
    this.onReloadCallback = onReloadCallback;
  }

  public void setOnAddCallback(@NonNull OnAddCallback onAddCallBack) {
    this.onAddCallBack = onAddCallBack;
  }
  public OnAddCallback getOnAddCallback() {
    return onAddCallBack;
  }
  public void setEmptyMessage(@NonNull CharSequence emptyMessage) {
    emptyMessageView.setText(emptyMessage);
  }

  public void setEmptyMessage(@StringRes int emptyMessageResId) {
    emptyMessageView.setText(emptyMessageResId);
  }

  public void setEmptyImage(@NonNull Drawable drawable) {
    emptyImageView.setImageDrawable(drawable);
  }

  public void setEmptyImage(@DrawableRes int emptyDrawableResId) {
    emptyImageView.setImageResource(emptyDrawableResId);
  }

  public void setErrorMessage(@NonNull CharSequence errorMessage) {
    errorMessageView.setText(errorMessage);
  }

  public void setErrorMessage(@StringRes int errorMessageResId) {
    errorMessageView.setText(errorMessageResId);
  }
  public void setErrorBtnMessage(@NonNull CharSequence errorBtnMessage){
    btnErrorReload.setText(errorBtnMessage);
  }
  public void setErrorBtnMessage(@StringRes int errorBtnMessageResId){
    btnErrorReload.setText(errorBtnMessageResId);
  }
  public void setErrorBtnBg(@DrawableRes int errorBtnBg){
    btnErrorReload.setBackgroundResource(errorBtnBg);
  }
  public void setErrorBtnBg(@NonNull Drawable errorBtnBg){
    btnErrorReload.setBackground(errorBtnBg);
  }
  public void setErrorBtnColor(@ColorInt int color){
    btnErrorReload.setTextColor(color);
  }
  public void setLoadingImage(@DrawableRes int emptyDrawableResId) {
    loadingImageView.setImageResource(emptyDrawableResId);
  }

  public void setLoadingMessage(@NonNull CharSequence errorMessage) {
    loadingTextView.setText(errorMessage);
  }

  public void setLoadingMessage(@StringRes int loadingMessageResId) {
    loadingTextView.setText(loadingMessageResId);
  }

  public void setNetworkErrorMessage(@NonNull CharSequence emptyMessage) {
    networkErrorTextView.setText(emptyMessage);
  }

  public void setNetworkErrorMessage(@StringRes int emptyMessageResId) {
    networkErrorTextView.setText(emptyMessageResId);
  }

  public void setNetworkErrorImage(@NonNull Drawable drawable) {
    networkErrorImageView.setImageDrawable(drawable);
  }

  public void setAddImage(@DrawableRes int emptyDrawableResId) {
    addImageView.setImageResource(emptyDrawableResId);
  }

  public void setAddImage(@NonNull Drawable drawable) {
    addImageView.setImageDrawable(drawable);
  }
  public void setAddMessage(@NonNull CharSequence addMessage) {
    addMessageView.setText(addMessage);
  }

  public void setAddMessage(@StringRes int addMessageResId) {
    addMessageView.setText(addMessageResId);
  }

  public void setAddBtnMessage(@NonNull CharSequence addBtnMessage){
    addBtn.setText(addBtnMessage);
  }
  public void setAddBtnMessage(@StringRes int addBtnMessageResId){
    addBtn.setText(addBtnMessageResId);
  }
  public void setAddBtnBg(@DrawableRes int addBtnBg){
    addBtn.setBackgroundResource(addBtnBg);
  }
  public void setAddBtnBg(@NonNull Drawable addBtnBg){
    addBtn.setBackground(addBtnBg);
  }
  public void setAddBtnTxtColor(@ColorInt int color){
    addBtn.setTextColor(color);
  }
  public void setNetworkErrorImage(@DrawableRes int emptyDrawableResId) {
    networkErrorImageView.setImageResource(emptyDrawableResId);
  }
  public interface OnReloadCallback {
    // 无意义的重载回调
    OnReloadCallback NONE = new OnReloadCallback() {
      @Override public void reload() {
      }
    };

    void reload();
  }

  public interface OnAddCallback {
    OnAddCallback NONE = new OnAddCallback() {
      @Override public void add() {
      }
    };

    void add();
  }

  public void showLoading() {
    setDisplayedChildId(R.id.temp_loading);
  }

  public void showEmpty() {
    setDisplayedChildId(R.id.temp_empty);
  }

  public void showError() {
    setDisplayedChildId(R.id.temp_error);
  }

  public void showBlank() {
    setDisplayedChildId(R.id.temp_blank);
  }



  public void showNetworkError() {
    setDisplayedChildId(R.id.temp_network_error);
  }

  public void showAddLayout() {
    setDisplayedChildId(R.id.temp_add);
  }


  /**
   * @param id viewcontent各自layout添加的内容id
   */
  public void showContent(@IdRes int id) {
    setDisplayedChildId(id);
  }

  /**
   * @hide
   */
  @Override protected final void setDisplayedChildId(int id) {
    super.setDisplayedChildId(id);
  }
}
