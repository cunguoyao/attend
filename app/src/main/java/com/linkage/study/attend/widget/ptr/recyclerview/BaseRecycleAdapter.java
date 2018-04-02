package com.linkage.study.attend.widget.ptr.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by cunguoyao on 2017/6/1.
 */

public abstract class BaseRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public abstract int onSpan(int position);
}
