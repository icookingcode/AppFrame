package com.hnhy.epapp.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by guc on 2019/10/31.
 * 描述：通用适配器封装
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    private List<T> mDatas;
    private Context mCxt;
    private int mLayoutId;
    private LayoutInflater mInflater;
    private CommonViewHolder.OnItemLongClickListener onItemLongClickListener;
    private CommonViewHolder.OnItemClickListener onItemClickListener;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mCxt = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
        mInflater = LayoutInflater.from(mCxt);
    }

    //T 限制点击返回的类型与Adapter泛型一致
    public void setOnItemClickListener(CommonViewHolder.OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(CommonViewHolder.OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(mLayoutId, viewGroup, false);
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder viewHolder, int i) {
        bindData(viewHolder, getItem(i), i);
        viewHolder.setOnItemClickListener(onItemClickListener, getItem(i));
        viewHolder.setOnItemLongClickListener(onItemLongClickListener, getItem(i));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    public void update(List<T> dataList) {
        this.mDatas = dataList;
        notifyDataSetChanged();
    }

    public void addList(List<T> dataList) {
        this.mDatas.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addItem(T data) {
        this.mDatas.add(data);
        notifyDataSetChanged();
    }

    public abstract void bindData(CommonViewHolder holder, T data, int position);

}
