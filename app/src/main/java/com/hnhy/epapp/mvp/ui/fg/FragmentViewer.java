package com.hnhy.epapp.mvp.ui.fg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hnhy.epapp.R;

/**
 * Created by guc on 2019/10/9.
 * 描述：
 */
public class FragmentViewer extends android.support.v4.app.Fragment {
    TextView mTvContent;
    private String mContent;

    public static FragmentViewer getInstance() {
        return getInstance(null);
    }

    public static FragmentViewer getInstance(String content) {
        Bundle bundle = new Bundle();
        if (content != null) {
            bundle.putString("content", content);
        }
        FragmentViewer viewer = new FragmentViewer();
        viewer.setArguments(bundle);
        return viewer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mContent = getArguments().getString("content");
        mContent = TextUtils.isEmpty(mContent) ? "暂无内容" : mContent;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_2, container, false);
        mTvContent = root.findViewById(R.id.tv_content);
        mTvContent.setText(mContent);
        return root;
    }

    public void setContent(String content) {
        mContent = content;
        mTvContent.setText(mContent);
    }
}
