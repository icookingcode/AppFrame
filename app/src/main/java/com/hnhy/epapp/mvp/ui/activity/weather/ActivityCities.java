package com.hnhy.epapp.mvp.ui.activity.weather;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.model.bean.City;
import com.hnhy.epapp.mvp.ui.widget.ToolBar;
import com.hnhy.epapp.util.AssersUtil;
import com.hnhy.framework.frame.BaseActivity;
import com.hnhy.ui.adapter.CommonRecycleAdapter;
import com.hnhy.ui.adapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 城市列表
 */
public class ActivityCities extends BaseActivity {

    @BindView(R.id.toolbar)
    ToolBar mToolbar;
    @BindView(R.id.rcv_content)
    RecyclerView mRcvContent;
    @BindView(R.id.et_filter)
    EditText mEtFilter;
    private String mFilter;

    private CommonRecycleAdapter<City> mAdapter;
    private List<City> mDatas;
    private List<City> mDatasTemp;

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
                holder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        ActivityWeatherReport.jump(mContext, data);
                    }

                    @Override
                    public void onItemLongClickListener(int position) {

                    }
                });
            }
        };
        mRcvContent.setAdapter(mAdapter);
        mEtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterCity();
            }
        });
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        filterCity();
    }

    private void filterCity() {
        mFilter = mEtFilter.getText().toString().trim();
        if (mFilter.isEmpty()) {
            mAdapter.update(mDatas);
        } else {
            mDatasTemp = getFilterDatas(mFilter);
            mAdapter.update(mDatasTemp);
        }
    }

    private List<City> getFilterDatas(String filter) {
        List<City> temp = new ArrayList<>();
        for (City city : mDatas) {
            if (city.cityZh.contains(filter) || city.cityEn.contains(filter)) {
                temp.add(city);
            }
        }
        return temp;
    }
}
