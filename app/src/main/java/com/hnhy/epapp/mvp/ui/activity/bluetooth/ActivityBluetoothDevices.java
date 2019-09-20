package com.hnhy.epapp.mvp.ui.activity.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hnhy.epapp.R;
import com.hnhy.epapp.mvp.ui.widget.ToolBar;
import com.hnhy.framework.frame.BaseActivity;
import com.hnhy.framework.logger.Logger;
import com.hnhy.ui.adapter.CommonRecycleAdapter;
import com.hnhy.ui.adapter.CommonViewHolder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guc on 2019/9/19.
 * 描述：蓝牙设备列表
 */
public class ActivityBluetoothDevices extends BaseActivity {
    private static final String TAG = "ActivityBluetoothDevice";
    @BindView(R.id.toolbar)
    ToolBar mToolbar;
    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.rcv_content)
    RecyclerView mRcvContent;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.rcv_founded)
    RecyclerView mRcvFounded;
    private BluetoothAdapter mBtAdapter;
    private List<BluetoothDevice> mPairdDevices;//已配对的设备
    private List<BluetoothDevice> mDevicesFounded;//已发现的设备
    private CommonRecycleAdapter<BluetoothDevice> mAdapterPaired;
    private CommonRecycleAdapter<BluetoothDevice> mAdapterFounded;
    private List<BluetoothSocket> mConnectedSockets;//已连接的连接
    private StateReceiver mReceiver;
    private boolean isDiscovering = false;


    //使用配置文件
    private BluetoothHeadset mBluetoothHeadset;
    private BluetoothDevice mDevice;
    // 设置监听（监听连接状态）
    private BluetoothProfile.ServiceListener mProfileListener = new BluetoothProfile.ServiceListener() {
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            switch (profile) {
                case BluetoothProfile.HEADSET:

                    Toast.makeText(mContext, "发现耳机", Toast.LENGTH_SHORT).show();
                    mBluetoothHeadset = (BluetoothHeadset) proxy;
                    Class btHeadsetCls = BluetoothHeadset.class;
                    try {
                        Method connect = btHeadsetCls.getMethod("connect", BluetoothDevice.class);
                        connect.setAccessible(true);
                        connect.invoke(mBluetoothHeadset, mDevice);
                    } catch (Exception e) {
                        Log.e(TAG, e + "");
                    }
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Toast.makeText(mContext, "设备未连接", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        public void onServiceDisconnected(int profile) {
            if (profile == BluetoothProfile.HEADSET) {
                mBluetoothHeadset = null;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_devices);
        ButterKnife.bind(this);
        initView();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);  //发现新设备
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);  //绑定状态改变
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);  //开始扫描
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);  //结束扫描
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);  //连接状态改变
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);  //蓝牙开关状态改变
        mReceiver = new StateReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void initView() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        mTvState.setText(stateTrans(mBtAdapter.getState()));
        Logger.e(TAG, mBtAdapter.getName() + " " + mBtAdapter.getAddress());
        initPairedDevices();
        initDevicesFounded();
    }

    /**
     * 初始化已发现的设备
     */
    private void initDevicesFounded() {
        mDevicesFounded = new ArrayList<>();
        mRcvFounded.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRcvFounded.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapterFounded = new CommonRecycleAdapter<BluetoothDevice>(this, mDevicesFounded, R.layout.item_device) {
            @Override
            public void bindData(CommonViewHolder holder, BluetoothDevice data, int position) {
                holder.setText(R.id.tv_content, data.getAddress() + "\n" + data.getName() + "--" + getTypeDescription(data.getType()));
                holder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        mDevice = data;
                        boolean bond = data.createBond();
                        if (bond) {
                            showToast("绑定成功");
                            // 建立与配置文件代理的连接
                            mBtAdapter.getProfileProxy(mContext, mProfileListener, BluetoothProfile.HEADSET);
                        } else {
                            showToast("绑定失败");
                        }
//                        new ConnectThread(data, UUID.fromString(UUIDUtils.getUUID(mContext))).start();

                        // 使用完毕后关闭
                        //mBtAdapter.closeProfileProxy(BluetoothProfile.HEADSET,mBluetoothHeadset);
                    }

                    @Override
                    public void onItemLongClickListener(int position) {

                    }
                });
            }
        };
        mRcvFounded.setAdapter(mAdapterFounded);
    }

    /**
     * 加载已配对设备
     */
    private void initPairedDevices() {
        mPairdDevices = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();//获取已配对设备
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                // 把名字和地址取出来添加到适配器中
                mPairdDevices.add(device);
            }
        }
        mRcvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRcvContent.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapterPaired = new CommonRecycleAdapter<BluetoothDevice>(this, mPairdDevices, R.layout.item_device) {
            @Override
            public void bindData(CommonViewHolder holder, BluetoothDevice data, int position) {
                holder.setText(R.id.tv_content, data.getAddress() + "\n" + data.getName() + " " + getConnState(data));
                holder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        mDevice = data;
                        if (data.getBluetoothClass().getMajorDeviceClass() != BluetoothClass.Device.Major.AUDIO_VIDEO) {
                            return;
                        }
                        // 建立与配置文件代理的连接
                        mBtAdapter.getProfileProxy(mContext, mProfileListener, BluetoothProfile.HEADSET);
                    }

                    @Override
                    public void onItemLongClickListener(int position) {

                    }
                });
            }
        };
        mRcvContent.setAdapter(mAdapterPaired);
    }

    @OnClick({R.id.btn_open, R.id.btn_close, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                switch (mBtAdapter.getState()) {
                    case BluetoothAdapter.STATE_OFF:
                        mBtAdapter.enable();
                        break;
                }
                break;
            case R.id.btn_close:
                switch (mBtAdapter.getState()) {
                    case BluetoothAdapter.STATE_ON:
                        mBtAdapter.disable();
                        break;
                }
                break;
            case R.id.btn_search:
                if (isDiscovering) {
                    isDiscovering = !mBtAdapter.cancelDiscovery();//停止搜索
                } else {
                    isDiscovering = mBtAdapter.startDiscovery();//开启设备搜索
                    if (isDiscovering) mDevicesFounded.clear();
                }
                setDiscoveryState();
                break;
        }
    }

    /**
     * 开启设备搜索
     */
    private void setDiscoveryState() {
        if (isDiscovering) {
            mBtnSearch.setText("停止搜索");
        } else {
            mBtnSearch.setText("搜索");
        }
    }

    private void manageConnectedSocket(BluetoothSocket mSocket) {
        if (mConnectedSockets == null) {
            mConnectedSockets = new ArrayList<>();
        }
        mConnectedSockets.add(mSocket);
    }

    private String stateTrans(int state) {
        String stateDesc = "未知状态";
        switch (state) {
            case BluetoothAdapter.STATE_ON:
                stateDesc = "已开启";
                break;
            case BluetoothAdapter.STATE_OFF:
                stateDesc = "已关闭";
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                stateDesc = "正在开启";
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                stateDesc = "正在关闭";
                break;
        }
        return stateDesc;
    }

    private String getConnState(BluetoothDevice device) {
//            return  mBtAdapter.getProfileConnectionState(device.getType()) == BluetoothHeadset.STATE_CONNECTED ?"已连接":"未连接";
        try {
            Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
            isConnectedMethod.setAccessible(true);
            boolean isConnected = (boolean) isConnectedMethod.invoke(device, (Object[]) null);
            return isConnected ? "已连接" : "未连接";
        } catch (Exception e) {
            return "未连接--异常";
        }
    }

    private String getTypeDescription(int type) {
        String typeDesc = "未知类型" + type;
        switch (type) {
            case BluetoothProfile.HEADSET:
                typeDesc = "音频设备";
                break;
            case BluetoothProfile.A2DP:
                typeDesc = "高质量音频设备";
                break;
        }
        return typeDesc;
    }

    private class StateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {//状态改变
                Logger.e("StateReceiver", stateTrans(mBtAdapter.getState()));
                mTvState.setText(stateTrans(mBtAdapter.getState()));
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {//发现新设备
                // 从 Intent 中获取发现的 BluetoothDevice
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Logger.e("StateReceiver_FOUND", device.getName() + " " + device.getAddress());
                if (mDevicesFounded.contains(device)) return;
                mAdapterFounded.addItem(device);
            } else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {//连接状态发生改变
                showToast("连接状态发生改变");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                showToast("当前状态" + getConnState(device));
                for (BluetoothDevice device1 : mPairdDevices) {
                    if (device1.getAddress().equals(device.getAddress())) {
                        device1 = device;
                        mAdapterPaired.notifyDataSetChanged();
                        return;
                    }
                }
            }
        }
    }

    /**
     * 连接作为客户端
     */
    private class ConnectThread extends Thread {
        private BluetoothDevice mDevice;
        private BluetoothSocket mSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            mDevice = device;
            // 这里的 UUID 需要和服务器的一致
            try {
                mSocket = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            // 关闭发现设备
            mBtAdapter.cancelDiscovery();
            try {
                mSocket.connect();
            } catch (IOException connectException) {
                try {
                    mSocket.close();
                } catch (IOException closeException) {
                    return;
                }
            }
            // 自定义方法
            manageConnectedSocket(mSocket);
        }

        public void cancle() {
            try {
                mSocket.close();
            } catch (IOException closeException) {
                closeException.printStackTrace();
            }
        }

    }

    /**
     * 连接作为服务端
     */
    private class AcceptThread extends Thread {
        private BluetoothServerSocket mServerSocket;

        public AcceptThread(String name, UUID uuid) {
            try {
                mServerSocket = mBtAdapter.listenUsingRfcommWithServiceRecord(name, uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            BluetoothSocket socket = null;
            while (true) {
                try {
                    socket = mServerSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (socket != null) {
                    // 自定义方法
                    manageConnectedSocket(socket);
                    break;
                }

            }
        }

        public void cancle() {
            try {
                mServerSocket.close();
            } catch (IOException closeException) {
                closeException.printStackTrace();
            }
        }

    }

}
