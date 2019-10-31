package com.hnhy.epapp.mvp.ui.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by guc on 2019/10/31.
 * 描述：通用ViewHolder
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {
    // 用来存放 View 以减少 findViewById 的次数
    //SparseArray 省内存
    private SparseArray<View> mViewSparseArray;

    public CommonViewHolder(@NonNull View itemView) {
        super(itemView);
        mViewSparseArray = new SparseArray<>();
    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = mViewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    <V> void setOnItemClickListener(OnItemClickListener onItemClickListener, V data) {
        if (onItemClickListener != null) {
            itemView.setOnClickListener((v) -> onItemClickListener.onItemClicked(v, data, getAdapterPosition()));
        }
    }

    <V> void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener, V data) {
        if (onItemLongClickListener != null) {
            itemView.setOnLongClickListener((View v) -> {
                onItemLongClickListener.onItemLongClicked(itemView, data, getAdapterPosition());
                return false;
            });
        }
    }

    //region  辅助方法
    public CommonViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonViewHolder setText(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setText(resId);
        return this;
    }

    public CommonViewHolder setTextBackground(int viewId, int resourceId) {
        TextView tv = getView(viewId);
        tv.setBackgroundResource(resourceId);
        return this;
    }

    public CommonViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public CommonViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public CommonViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        return this;
    }
    // endregion

    public interface OnItemClickListener<V> {
        void onItemClicked(View itemView, V data, int position);
    }

    public interface OnItemLongClickListener<V> {
        void onItemLongClicked(View itemView, V data, int position);
    }
}
