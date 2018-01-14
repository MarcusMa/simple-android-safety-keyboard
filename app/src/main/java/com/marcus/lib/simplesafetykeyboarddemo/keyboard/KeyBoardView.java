package com.marcus.lib.simplesafetykeyboarddemo.keyboard;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by marcus on 2018/1/14.
 */

public class KeyBoardView extends LinearLayout {

    private static final String TAG = KeyBoardView.class.getName();

    private Context mContext;
    private static final String DEFAULT_TITLE_TEXT = "Android Safety Board";
    private static final int DEFAULT_KEYS_COLUMN_NUM = 3;
    private static final int DEFAULT_PASSWORD_SIZE = 6;
    private static final String DEL_BTN_TEXT = "Del";
    private static final String OK_BTN_TEXT = "OK";

    private static final int DEL_BTN_ID = 100;
    private static final int OK_BTN_ID = 200;

    private LinearLayout mRootView;
    private RelativeLayout mTitleLayoutView;
    private TextView mTitleView;
    private GrapeGridView mKeysGrideView;

    private OnKeyBoardListener mOuterKeyBoardListener;
    private KeyBoardDataAdapter mDataAdapter;

    private int mRequiredPassWordSize = DEFAULT_PASSWORD_SIZE;
    private int mInputSize = 0;

    private PasswordManager mPasswordManager = PasswordManager.getInstance();

    private static List<Integer> DEFAULT_NUM_LIST = new ArrayList<>(10);

    static {
        for (int i = 0; i < 10; i++) {
            DEFAULT_NUM_LIST.add(i);
        }
    }

    public KeyBoardView(Context context) {
        super(context);
        mContext = context;
        mDataAdapter = new KeyBoardDataAdapter();
        initViews(context);
    }

    public KeyBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void initViews(Context context) {
        mRootView = new LinearLayout(context);
        mRootView.setOrientation(VERTICAL);

        //TODO add other settings
        mRootView.setBackgroundColor(Color.GRAY);

        mTitleLayoutView = new RelativeLayout(context);
        RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getScreenHeightPixels() / 16);
        mTitleLayoutView.setLayoutParams(titleLayoutParams);
        // mTitleLayoutView.setBackgroundColor(Color.GREEN);

        mTitleView = new TextView(context);
        mTitleView.setText(DEFAULT_TITLE_TEXT);
        mTitleView.setTextColor(Color.WHITE);
        mTitleView.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mTitleView.setLayoutParams(layoutParams);
        mTitleLayoutView.addView(mTitleView); // add title view to it's parent view.

        mRootView.addView(mTitleLayoutView);

        //TODO add other views

        mKeysGrideView = new GrapeGridView(context);
        mKeysGrideView.setHorizontalScrollBarEnabled(false);
        mKeysGrideView.setVerticalScrollBarEnabled(false);
        mKeysGrideView.setEnabled(false);
        mKeysGrideView.setNumColumns(DEFAULT_KEYS_COLUMN_NUM);
        mKeysGrideView.setVerticalSpacing(10);
        mKeysGrideView.setHorizontalSpacing(10);

        // set grideview's data.
        mKeysGrideView.setAdapter(mDataAdapter);
        mRootView.addView(mKeysGrideView);

        addView(mRootView);
        setGravity(Gravity.BOTTOM);
    }


    /////////////////////////////////////////
    /// Tools
    /////////////////////////////////////////
    private int getScreenHeightPixels() {
        int ret = 0;
        if (null != mContext) {
            ret = mContext.getResources().getDisplayMetrics().heightPixels;
        }
        return ret;
    }

    private int getScreenWidthPixels() {
        int ret = 0;
        if (null != mContext) {
            ret = mContext.getResources().getDisplayMetrics().widthPixels;
        }
        return ret;
    }

    public int getViewWidth() {
        return getScreenWidthPixels();
    }

    public int getViewHeight() {
        return getScreenHeightPixels() / 16 + 4 * getScreenHeightPixels() / 12 + 80;
    }

    public void setOnKeyBoardListener(OnKeyBoardListener listener) {
        mOuterKeyBoardListener = listener;
    }

    /////////////////////////////////////////
    /// Inner Class
    /////////////////////////////////////////

    private class GrapeGridView extends GridView {

        public GrapeGridView(Context context) {
            super(context);
        }

        public GrapeGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

    private class KeyBoardDataAdapter extends BaseAdapter {


        private KeyBoardDataAdapter() {
            super();
            reRandomKeysNum();
        }

        public void reRandomKeysNum() {
            Collections.shuffle(DEFAULT_NUM_LIST);
            Log.d(TAG,"Random NUM LIST: " + DEFAULT_NUM_LIST.toString());
        }

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public Object getItem(int position) {
            if (position == 9) {
                return DEL_BTN_TEXT;
            } else if (position == 10) {
                return String.valueOf(DEFAULT_NUM_LIST.get(9));
            } else if (position == 11) {
                return OK_BTN_TEXT;
            } else {
                return String.valueOf(DEFAULT_NUM_LIST.get(position));
            }
        }

        @Override
        public long getItemId(int position) {
            if (position == 9) {
                return DEL_BTN_ID;
            } else if (position == 10) {
                return DEFAULT_NUM_LIST.get(9);
            } else if (position == 11) {
                return OK_BTN_ID;
            } else {
                return DEFAULT_NUM_LIST.get(position);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int keyWidth = getScreenWidthPixels() / 3;
            int keyHeight = getScreenHeightPixels() / 12;
            long id = getItemId(position);

            TextView keyView = new TextView(mContext);
            AbsListView.LayoutParams keyViewLayoutParams = new AbsListView.LayoutParams(keyWidth,
                    keyHeight);
            keyView.setLayoutParams(keyViewLayoutParams);
            keyView.setTextColor(Color.RED);
            keyView.setBackgroundColor(Color.WHITE);
            keyView.setGravity(Gravity.CENTER);
            keyView.setTextSize(18);
            String keyTitle = (String) getItem(position);
            keyView.setText(keyTitle);
            keyView.setOnClickListener(mInnerKeyClickListener);
            return keyView;
        }
    }

    private final OnClickListener mInnerKeyClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == DEL_BTN_ID) {
                // delete button clicked
                mPasswordManager.deleteOne();
            } else if (id == OK_BTN_ID) {
                // ok button clicked
                if (mInputSize != mRequiredPassWordSize) {
                    Toast toast = Toast.makeText(mContext, "Your Input is less than Required.", Toast.LENGTH_SHORT);
                } else {
                    mOuterKeyBoardListener.onDone(mPasswordManager.getEncryptString());
                    hide();
                }
            } else {
                // keys button clicked
                mPasswordManager.addOne(v.getId());
                mInputSize++;
            }
            if (null != mInnerKeyClickListener) {
                mOuterKeyBoardListener.onChange(mInputSize);
            }
        }
    };

    public void show() {
        if (null != mDataAdapter) {
            mDataAdapter.reRandomKeysNum();
            mDataAdapter.notifyDataSetChanged();
        }
        else{
            mDataAdapter = new KeyBoardDataAdapter();
        }
        // mKeysGrideView.requestLayout();
        // mRootView.invalidate();

        if (null != mOuterKeyBoardListener) {
            mOuterKeyBoardListener.onShow();
        }
    };

    public void hide() {

        if (mContext instanceof KeyBoardActivity) {
            ((KeyBoardActivity) mContext).dismiss();
        }

        if (null != mOuterKeyBoardListener) {
            mOuterKeyBoardListener.onHide();
        }
    }

    ;
}

