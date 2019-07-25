package com.hnhy.epapp.mvp.ui.activity.weather;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.model.bean.City;
import com.hnhy.epapp.mvp.ui.widget.ToolBar;
import com.hnhy.epapp.util.AssersUtil;
import com.hnhy.framework.frame.BaseActivity;
import com.hnhy.ui.adapter.CommonRecycleAdapter;
import com.hnhy.ui.adapter.CommonViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 城市列表
 */
public class ActivityCities extends BaseActivity {

    @BindView(R.id.toolbar)
    ToolBar mToolbar;
    @BindView(R.id.rcv_content)
    RecyclerView mRcvContent;

    private CommonRecycleAdapter<City> mAdapter;
    private List<City> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mRcvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDatas = AssersUtil.getCities(this);
        mRcvContent.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new CommonRecycleAdapter<City>(this, mDatas, R.layout.item_city) {
            @Override
            public void bindData(CommonViewHolder holder, City data, int position) {
                holder.setText(R.id.tv_content, data.id + "\n" + data.cityZh + "-" + data.cityEn);
            }
        };
        mRcvContent.setAdapter(mAdapter);
    }
}
