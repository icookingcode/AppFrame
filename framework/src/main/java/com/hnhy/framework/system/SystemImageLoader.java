package com.hnhy.framework.system;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hnhy.framework.R;
import com.hnhy.framework.frame.BaseSystem;
import com.hnhy.framework.logger.Logger;

/**
 * Author: hardcattle
 * Time: 2018/3/26 上午9:57
 * Description:
 */

public class SystemImageLoader extends BaseSystem {
    private static final String TAG = "SystemImageLoader";

    @Override
    protected void init() {
    }

    @Override
    protected void destroy() {
    }

    //临时处理
    public void displayImage(Context context, ImageView imageView, int resId) {
        Glide.with(context).load(resId).into(imageView);
    }

    public void displayImage(Object tag, ImageView imageView, String url) {
        displayImage(tag, imageView, url, -1, -1);
    }

    public void displayImage(Object tag, ImageView imageView, String url, @DrawableRes int placeHolderId, @DrawableRes int errorId) {
        placeHolderId = placeHolderId == -1 ? R.drawable.ic_place_pic : placeHolderId;
        errorId = errorId == -1 ? R.drawable.ic_place_pic : errorId;
        RequestOptions options = new RequestOptions().placeholder(placeHolderId).error(errorId);
        if (tag instanceof FragmentActivity) {
            Glide.with((FragmentActivity) tag).load(url).apply(options).into(imageView);
        } else if (tag instanceof Activity) {
            Glide.with((Activity) tag).load(url).apply(options).into(imageView);
        } else if (tag instanceof Context) {
            Glide.with((Context) tag).load(url).apply(options).into(imageView);
        } else if (tag instanceof Fragment) {
            Glide.with((Fragment) tag).load(url).apply(options).into(imageView);
        } else if (tag instanceof android.support.v4.app.Fragment) {
            Glide.with((android.support.v4.app.Fragment) tag).load(url).apply(options).into(imageView);
        } else if (tag instanceof View) {
            Glide.with((View) tag).load(url).apply(options).into(imageView);
        } else {
            Logger.e(TAG, "the tag is error");
        }
    }
}
