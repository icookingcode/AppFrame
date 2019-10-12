package com.hnhy.epapp.mvp.ui.activity.databind;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hnhy.epapp.R;
import com.hnhy.epapp.databinding.ItemCityBindingBinding;
import com.hnhy.epapp.mvp.model.bean.City;
import com.hnhy.ui.adapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guc on 2019/10/12.
 * 描述：使用数据绑定加载RecyclerView数据
 */
public class AdapterCityDataBinding extends RecyclerView.Adapter {
    private List<City> cities;
    private Context context;

    public AdapterCityDataBinding(List<City> cities, Context context) {
        if (this.cities == null) {
            this.cities = new ArrayList<>();
        }
        this.cities = cities;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemCityBindingBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_city_binding, parent, false);
        return new CommonViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ItemCityBindingBinding binding = DataBindingUtil.getBinding(viewHolder.itemView);
        binding.setCity(cities.get(position));
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
