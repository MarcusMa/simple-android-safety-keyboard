package com.marcus.lib.simplesafetykeyboarddemo.keyboard;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

public class KeyBoardActivity extends Activity {

    private static final String TAG = KeyBoardView.class.getName();

    private FrameLayout mRootView;
    private KeyBoardView mKeyBoardView;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_keyboard);
        mRootView = new FrameLayout(this);
        FrameLayout.LayoutParams rootLayoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mRootView.setLayoutParams(rootLayoutParams);
        mRootView.setBackgroundColor(Color.TRANSPARENT);
        setContentView(mRootView);

        // init keyboard view
        mKeyBoardView = new KeyBoardView(this);
        mKeyBoardView.setOnKeyBoardListener(new OnKeyBoardListener() {

            @Override
            public void onShow() {
                Log.d(TAG, "onShow");
            }

            @Override
            public void onHide() {
                Log.d(TAG, "onHide");
            }

            @Override
            public void onChange(int inputSize) {
                Log.d(TAG, "onChange:" + inputSize);
            }

            @Override
            public void onDone(String encryptStr) {
                Log.d(TAG, "onDone:" + encryptStr);
            }
        });
//        mRootView.addView(mKeyBoardView);
        // init popup window
        mPopupWindow = new PopupWindow(mKeyBoardView,
                mKeyBoardView.getViewWidth(),mKeyBoardView.getViewHeight());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
        mPopupWindow.setOnDismissListener(mDismissListener);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mPopupWindow.setFocusable(true);

    }

    private PopupWindow.OnDismissListener mDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            mPopupWindow.setOnDismissListener(null);
            mPopupWindow = null;
            if (null != mKeyBoardView) {
                mKeyBoardView.setOnKeyBoardListener(null);
                mKeyBoardView = null;
            }
            finish();
        }
    };

    public void dismiss() {
        if (null != mPopupWindow) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (null != mKeyBoardView) {
            mKeyBoardView.show();
        }
        if (null != mPopupWindow) {
            mPopupWindow.showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);
            mPopupWindow.update();
        }
    }
}
