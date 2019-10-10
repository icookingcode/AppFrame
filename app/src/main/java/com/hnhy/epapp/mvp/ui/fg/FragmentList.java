package com.hnhy.epapp.mvp.ui.fg;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hnhy.epapp.R;
import com.hnhy.framework.util.FrameworkUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by guc on 2019/10/9.
 * 描述：
 */
public class FragmentList extends Fragment {
    @BindView(R.id.tv_header_1)
    TextView mTvHeader1;
    @BindView(R.id.tv_header_2)
    TextView mTvHeader2;
    @BindView(R.id.tv_header_3)
    TextView mTvHeader3;
    @BindView(R.id.tv_header_4)
    TextView mTvHeader4;
    @BindView(R.id.tv_header_5)
    TextView mTvHeader5;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private OnTitleSelectedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnTitleSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnTitleSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_1, container);
        unbinder = ButterKnife.bind(this, root);
        registerForContextMenu(mTvHeader1);
        initView();
        return root;
    }

    private void initView() {
        mRefreshLayout.setOnRefreshListener((refreshLayout) -> {
            refreshLayout.finishRefresh(2000);
        });
        mRefreshLayout.setOnLoadMoreListener((refreshLayout) -> {
            refreshLayout.finishLoadMore(2000);
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("操作");//设置标题
        menu.add(0, 1, Menu.NONE, "已读");
        menu.add(0, 2, Menu.NONE, "收藏");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                FrameworkUtils.Toast.showShort(item.getTitle().toString());
                break;
            case 2:
                FrameworkUtils.Toast.showShort("已" + item.getTitle().toString());
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_header_1, R.id.tv_header_2, R.id.tv_header_3, R.id.tv_header_4, R.id.tv_header_5})
    public void onViewClicked(View view) {
        if (listener == null) return;
        listener.onTitleSelected(((TextView) view).getText().toString());
        switch (view.getId()) {
            case R.id.tv_header_1:
                break;
            case R.id.tv_header_2:
                break;
            case R.id.tv_header_3:
                break;
            case R.id.tv_header_4:
                break;
            case R.id.tv_header_5:
                break;
        }
    }

    public interface OnTitleSelectedListener {
        void onTitleSelected(String title);
    }
}
