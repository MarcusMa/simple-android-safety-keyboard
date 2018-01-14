package com.marcus.lib.simplesafetykeyboarddemo.keyboard;

/**
 * Created by marcus on 2018/1/14.
 */

public interface OnKeyBoardListener {

    void onShow();

    void onHide();

    void onChange(int inputSize);

    void onDone(String encryptStr);
}
