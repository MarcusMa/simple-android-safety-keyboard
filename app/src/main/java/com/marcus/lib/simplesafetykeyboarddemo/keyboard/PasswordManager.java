package com.marcus.lib.simplesafetykeyboarddemo.keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 2018/1/14.
 */

public class PasswordManager implements IPasswordFactory {
    private static PasswordManager instance;

    private List<Integer> mInputList;

    public static PasswordManager getInstance() {
        if (instance == null) {
            instance = new PasswordManager();
        }
        return instance;
    }

    private PasswordManager() {
        clear();
    }

    @Override
    public void clear() {
        if (null != mInputList) {
            mInputList.clear();
        }
        mInputList = new ArrayList<>();
    }

    @Override
    public void deleteOne() {
        if (mInputList.size() <= 0) {
            return;
        }
        mInputList.remove(mInputList.size() - 1);
    }

    @Override
    public void addList(List<Integer> list) {
        mInputList.addAll(list);
    }

    @Override
    public void addOne(int i) {
        mInputList.add(i);
    }

    @Override
    public String getEncryptString() {
        //FIXME
        return mInputList.toString();
    }
}
