package com.pasc.lib.widget.dialog;

import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (C) 2019 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2019/4/2
 */
public interface DialogFragmentInterface  {

    /**
     * Interface used to allow the creator of a dialog to run some code when the
     * dialog is canceled.
     * <p>
     * This will only be called when the dialog is canceled, if the creator
     * needs to know when it is dismissed in general, use
     * {@link DialogFragmentInterface.OnDismissListener}.
     */
    interface OnCancelListener<T extends DialogFragment> {
        /**
         * This method will be invoked when the dialog is canceled.
         *
         * @param dialogFragment the dialog that was canceled will be passed into the
         *                       method
         */
        void onCancel(T dialogFragment);
    }


    /**
     * Interface used to allow the creator of a dialog to run some code when the
     * dialog is dismissed.
     */
    interface OnDismissListener<T extends DialogFragment>  {
        /**
         * This method will be invoked when the dialog is dismissed.
         *
         * @param dialogFragment the dialog that was dismissed will be passed into the
         *                       method
         */
        void onDismiss(T dialogFragment);
    }

    /**
     * Interface used to allow the creator of a dialog to run some code when the
     * dialog is shown.
     */
    interface OnShowListener<T extends DialogFragment> extends Serializable {
        /**
         * This method will be invoked when the dialog is shown.
         *
         * @param dialogFragment the dialog that was shown will be passed into the
         *                       method
         */
        void onShow(T dialogFragment);
    }

    /**
     * Interface used to allow the creator of a dialog to run some code when an
     * item on the dialog is clicked.
     */
    interface OnClickListener<T extends DialogFragment>  {
        /**
         * This method will be invoked when a button in the dialog is clicked.
         *
         * @param dialogFragment the dialog that received the click
         * @param which          the button that was clicked the position
         *                       of the item clicked
         */
        void onClick(T dialogFragment, int which);
    }


    /**
     * Interface definition for a callback to be invoked when a key event is
     * dispatched to this dialog. The callback will be invoked before the key
     * event is given to the dialog.
     */
    interface OnKeyListener<T extends DialogFragment> {
        /**
         * Called when a key is dispatched to a dialog. This allows listeners to
         * get a chance to respond before the dialog.
         *
         * @param dialog  the dialog the key has been dispatched to
         * @param keyCode the code for the physical key that was pressed
         * @param event   the KeyEvent object containing full information about
         *                the event
         * @return {@code true} if the listener has consumed the event,
         * {@code false} otherwise
         */
        boolean onKey(T dialog, int keyCode, KeyEvent event);
    }


    /**
     * Interface used to allow the creator of a dialog to run some code when an
     * item in a multi-choice dialog is clicked.
     */
    interface OnMultiChoiceClickListener<T extends DialogFragment> {
        /**
         * This method will be invoked when an item in the dialog is clicked.
         *
         * @param dialogFragment the dialog where the selection was made
         * @param selectedPos    the position of all items in the list that was clicked
         */
        void onClick(T dialogFragment, List<Integer> selectedPos);
    }

    /**
     * Interface used to allow the creator of a dialog to run some code when an
     * item in a multi-choice dialog is clicked.
     */
    interface OnSingleChoiceClickListener<T extends DialogFragment> {
        /**
         * This method will be invoked when an item in the dialog is clicked.
         *
         * @param dialogFragment the dialog where the selection was made
         * @param position       the position of the item in the list that was clicked
         */
        void onClick(T dialogFragment, int position);
    }
}
