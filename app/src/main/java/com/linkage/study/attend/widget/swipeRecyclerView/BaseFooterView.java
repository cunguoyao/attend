package com.linkage.study.attend.widget.swipeRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 */
public abstract class BaseFooterView extends FrameLayout implements FooterViewListener {

    public BaseFooterView(Context context) {
        super(context);
    }

    public BaseFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}