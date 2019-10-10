package com.hnhy.epapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.ui.fg.FragmentList;
import com.hnhy.epapp.mvp.ui.fg.FragmentViewer;
import com.hnhy.epapp.mvp.ui.fg.MyDialogFragment;
import com.hnhy.framework.frame.BaseActivity;
import com.hnhy.framework.util.FrameworkUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityFragmentDemo extends BaseActivity implements FragmentList.OnTitleSelectedListener {
    private static final String TAG = "ActivityFragmentDemo";
    FragmentList mFgList;
    FragmentViewer mFgViewer;
    private FragmentManager mFgManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_demo);
        ButterKnife.bind(this);
        mFgManager = getSupportFragmentManager();
        mFgList = (FragmentList) mFgManager.findFragmentById(R.id.fg_list);
        mFgViewer = (FragmentViewer) mFgManager.findFragmentById(R.id.fg_viewer);
        addFragmentToViewGroup();
    }

    private void addFragmentToViewGroup() {
        FragmentTransaction fragmentTransaction = mFgManager.beginTransaction();
        FragmentViewer viewer = FragmentViewer.getInstance("这是内容一");
        fragmentTransaction.add(R.id.fragment_container, viewer);
        fragmentTransaction.commit();

        fragmentTransaction = mFgManager.beginTransaction();
        FragmentViewer viewer2 = FragmentViewer.getInstance("这是内容二");
        fragmentTransaction.add(R.id.fragment_container, viewer2);
        fragmentTransaction.addToBackStack(null);//按返回键回退 fragment
        fragmentTransaction.commit();
    }

    @Override
    public void onTitleSelected(String title) {
        FrameworkUtils.Toast.showShort("您点击了" + title);
        mFgViewer.setContent("这是 " + title + " 的内容");
    }

    @OnClick(R.id.btn_show_dialog)
    public void onViewClicked() {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(mFgManager, TAG);
    }
}
