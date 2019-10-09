package com.hnhy.epapp.mvp.ui.activity.vpn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hnhy.epapp.R;
import com.hnhy.framework.frame.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityVpnDemo extends BaseActivity {
    private static final int VPN_REQUEST_CODE = 0x0F;
    @BindView(R.id.tv_show)
    TextView mTvShow;
    private boolean waitingForVPNStart;
    private BroadcastReceiver vpnStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MyVpnService.BROADCAST_VPN_STATE.equals(intent.getAction())) {
                if (intent.getBooleanExtra("running", false))
                    waitingForVPNStart = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpn_demo);
        ButterKnife.bind(this);
        waitingForVPNStart = false;
        LocalBroadcastManager.getInstance(this).registerReceiver(vpnStateReceiver,
                new IntentFilter(MyVpnService.BROADCAST_VPN_STATE));
    }

    @OnClick({R.id.btn_open, R.id.btn_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                startVPN();
                break;
            case R.id.btn_close:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VPN_REQUEST_CODE && resultCode == RESULT_OK) {
            waitingForVPNStart = true;
            startService(new Intent(this, MyVpnService.class));
            enableButton(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableButton(!waitingForVPNStart && !MyVpnService.isRunning());
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(vpnStateReceiver);
        super.onDestroy();
    }

    private void startVPN() {
        Intent vpnIntent = MyVpnService.prepare(this);
        if (vpnIntent != null)
            startActivityForResult(vpnIntent, VPN_REQUEST_CODE);
        else
            onActivityResult(VPN_REQUEST_CODE, RESULT_OK, null);
    }

    private void enableButton(boolean enable) {
        final Button vpnButton = findViewById(R.id.btn_open);
        if (enable) {
            vpnButton.setEnabled(true);
            vpnButton.setText(R.string.start_vpn);
        } else {
            vpnButton.setEnabled(false);
            vpnButton.setText(R.string.stop_vpn);
        }
    }
}
