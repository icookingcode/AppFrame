package com.hnhy.epapp.mvp.ui.activity.interact;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.ui.activity.ActivityFunctionSelect;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 接收文本和图片
 */
public class ActivityReceiver extends AppCompatActivity {

    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv_picture)
    ImageView mIvPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        ButterKnife.bind(this);
        // Get the intent that started this activity
        Intent intent = getIntent();
        Uri data = intent.getData();
        // Figure out what to do based on the intent type
        if (intent.getType().indexOf("image/") != -1) {
            // Handle intents with image data ...
            ClipData clipData = intent.getClipData();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("接收到：%d张图片\n%s", clipData.getItemCount(), clipData.getDescription()));
            for (int i = 0; i < clipData.getItemCount(); i++) {
                ClipData.Item item = clipData.getItemAt(i);
                stringBuilder.append("\n" + item.getText());
                Uri picUri = item.getUri();
                try {
                    InputStream imageStream = getContentResolver().openInputStream(picUri);
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    mIvPicture.setImageBitmap(yourSelectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            mTvContent.setText(stringBuilder.toString());
        } else if (intent.getType().equals("text/plain")) {
            // Handle intents with text ...
            String revStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            mTvContent.setText(revStr);
        }
    }

    @Override
    public void onBackPressed() {
        Intent result = new Intent(this, ActivityFunctionSelect.class);
        startActivity(result);
        finish();
    }
}
