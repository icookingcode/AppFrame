package com.hnhy.epapp.mvp.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hnhy.epapp.R;
import com.hnhy.framework.util.FrameworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 与其他应用交互
 */
public class ActivityInteract extends AppCompatActivity {
    private static final int PICK_CONTACT_REQUEST = 1;
    @BindView(R.id.btn_pick_contact)
    Button mBtnPickContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact);
        ButterKnife.bind(this);
    }


    /**
     * @param intent 待启动的intent
     * @return true 存在 ；false 不存在
     */
    private boolean isIntentSafe(Intent intent) {
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        return isIntentSafe;
    }

    @OnClick({R.id.btn_open_url, R.id.btn_share, R.id.btn_pick_contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_url:
                Uri webpage = Uri.parse("http://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (isIntentSafe(intent)) {
                    startActivity(intent);
                }
                break;
            case R.id.btn_share:
                Intent intentSend = new Intent(Intent.ACTION_SEND);
                String title = "分享";
                intentSend.setType("text/plain");
                intentSend.putExtra(Intent.EXTRA_TEXT, "你好啊 大神");
                Intent chooser = Intent.createChooser(intentSend, title);
                if (intentSend.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
                break;
            case R.id.btn_pick_contact:
                pickContact();
                break;
        }
    }

    /**
     * 选择联系人
     */
    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                FrameworkUtils.Toast.showShort("选择了联系人");
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column1 = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                String name = cursor.getString(column1);
                cursor.close();
                mBtnPickContact.setText("已选择：" + name + number);
            } else {
                FrameworkUtils.Toast.showShort("取消选择");
            }
        }
    }
}
