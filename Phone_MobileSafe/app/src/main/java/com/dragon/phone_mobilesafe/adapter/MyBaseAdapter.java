package com.dragon.phone_mobilesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by pc on 2016-08-11.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> lists;
    public Context mcontext;

    public MyBaseAdapter() {
    }

    public MyBaseAdapter(List<T> lists, Context mcontext) {
        this.lists = lists;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
