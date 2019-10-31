package com.hnhy.epapp.mvp.ui.activity.layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.model.bean.City;
import com.hnhy.epapp.mvp.ui.adapter.CommonAdapter;
import com.hnhy.epapp.mvp.ui.adapter.CommonViewHolder;
import com.hnhy.epapp.util.AssersUtil;
import com.hnhy.framework.util.FrameworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityLayout extends AppCompatActivity implements CommonViewHolder.OnItemClickListener<City>, CommonViewHolder.OnItemLongClickListener<City> {

    @BindView(R.id.rcv)
    RecyclerView mRcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<City> mCities = AssersUtil.getCities(this);
        CommonAdapter<City> mAdapter = new CommonAdapter<City>(this, mCities, R.layout.item_city) {
            @Override
            public void bindData(CommonViewHolder holder, City data, int position) {
                holder.setText(R.id.tv_content, data.id + "\n" + data.cityZh + "-" + data.cityEn);
            }
        };
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        mRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRcv.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(View itemView, City data, int position) {
        FrameworkUtils.Toast.showShort("点击了" + data.cityZh);
    }

    @Override
    public void onItemLongClicked(View itemView, City data, int position) {
        FrameworkUtils.Toast.showShort("长按了" + data.cityZh);
    }
}
