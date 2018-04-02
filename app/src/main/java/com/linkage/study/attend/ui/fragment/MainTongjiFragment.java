package com.linkage.study.attend.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkage.study.attend.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by cunguoyao on 2018/4/2.
 */
@ContentView(R.layout.fragment_main_tongji)
public class MainTongjiFragment extends BaseFragment {

    public static MainTongjiFragment create() {
        MainTongjiFragment f = new MainTongjiFragment();
        Bundle args = new Bundle();
        //args.putInt("type", type);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        return view;
    }
}
