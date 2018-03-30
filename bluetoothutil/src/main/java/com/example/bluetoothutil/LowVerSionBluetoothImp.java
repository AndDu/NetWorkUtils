package com.example.bluetoothutil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.os.Build;

/**
 * Created by Yellow on 2017/9/19.
 */

public class LowVerSionBluetoothImp extends BaseBlueToothImp {

    private BluetoothAdapter.LeScanCallback leScanCallback;

    public LowVerSionBluetoothImp(BluetoothAdapter.LeScanCallback leScanCallback) {
        this.leScanCallback = leScanCallback;
    }

    @Override
    public void startScan() {
        stopScan();
        if (adapter != null && leScanCallback != null) {
            adapter.startLeScan(leScanCallback);
        }
    }

    @Override
    public void repleaseResourse() {
        super.repleaseResourse();
        if (adapter != null && leScanCallback != null) {
            adapter.stopLeScan(leScanCallback);
        }
    }

    @Override
    public void stopScan() {
        if (adapter != null) {
            adapter.stopLeScan(leScanCallback);
        }
    }
}
