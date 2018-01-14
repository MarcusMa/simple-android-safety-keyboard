package com.marcus.lib.simplesafetykeyboarddemo.keyboard;

import java.util.List;

/**
 * Created by marcus on 2018/1/14.
 */

public interface IPasswordFactory {

    void clear();

    void deleteOne();

    void addList(List<Integer> list);

    void addOne(int i);

    String getEncryptString();
}
