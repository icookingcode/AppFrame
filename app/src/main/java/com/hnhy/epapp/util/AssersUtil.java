package com.hnhy.epapp.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hnhy.epapp.mvp.model.bean.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by guc on 2019/7/25.
 * 描述：资源文件读取
 */
public class AssersUtil {
    static Gson gson = new Gson();
    private static String FILE_NAME_CITY = "city.json";

    public static List<City> getCities(Context context) {
        String jsonData = getStringFromAssets(context, FILE_NAME_CITY);
        return gson.fromJson(jsonData, new TypeToken<List<City>>() {
        }.getType());
    }

    private static String getStringFromAssets(Context context, String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(filename)));
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 读取assets目录下图片资源
     *
     * @param context  c
     * @param fileName 文件名
     * @return bitmap对象
     */
    public static Bitmap getBitmapFromAssets(Context context, String fileName) {
        Bitmap bitmap = null;
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);//filename是assets目录下的图片名
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
