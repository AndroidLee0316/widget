package com.pasc.lib.widget;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.pasc.lib.widget.dialognt.LoadingDialog;
import com.pasc.lib.widget.dialognt.PublicDialog;

/**
 * 继承android.support.v4.app.Fragment的Fragment基类。
 * Created by chenruihan410 on 2018/07/16.
 */
@Deprecated
public class PascFragment extends Fragment {

    private LoadingDialog mLoadingDialog; // 加载中对话框
    private PublicDialog mPublicDialog; // 二次确认对话框
    private Toast mToast; // 吐司信息

    /**
     * 显示加载中对话框.
     *
     * @param msg 加载中的信息.
     */
    protected void showLoadingDialog(CharSequence msg) {
        boolean hasContent = !TextUtils.isEmpty(msg);
        if (mLoadingDialog == null) {
            if (hasContent) {
                mLoadingDialog = new LoadingDialog(getActivity(), msg.toString());
            } else {
                mLoadingDialog = new LoadingDialog(getActivity());
            }
        } else {
            if (hasContent) {
                mLoadingDialog.setContent(msg.toString());
            }
        }
        mLoadingDialog.setHasContent(hasContent);
        mLoadingDialog.show();
    }

    /**
     * 关闭加载中对话框.
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismissLoadingDialog();
        super.onDestroy();
    }

    /**
     * 显示二次确认对话框.
     *
     * @param msg 二次确认的信息.
     */
    protected void showConfirmDialog(CharSequence msg, PublicDialog.Callback callback) {
        if (mPublicDialog == null) {
            mPublicDialog = PublicDialog.getDialog(getActivity());
        }
        mPublicDialog.setCallback(callback).setHint(msg.toString()).show();
    }

    /**
     * 显示提示信息.
     *
     * @param msg 消息.
     */
    protected void showMessage(CharSequence msg) {
        dismissMessage();
        mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        mToast.show();
    }

    /**
     * 关闭提示信息.
     */
    protected void dismissMessage() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
