package com.example.bluetoothutil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Yellow on 2017/9/18.
 */

public interface IBluetoothController {

    int UNSPPORT_BLUETOOTH = 0;

    int OPENSUCCESS = 1;

    int UNOPEN_BLUETOOTH = 2;


    int initBluetooth(Context context);

    void registerBroastReciver(Context context);

    void unRegisterBroastReciver(Context context);

    void startScan();

    void stopScan();

    void openBluetooth(Context context);

    void repleaseResourse();

    BluetoothAdapter getAdapter();

    void setCallback(BluetoothGattCallback callback);

    interface ConnectListener {

        void onConnectSuccess(BluetoothDevice device, int rssi, byte[] scanRecord);

        void onConnectFail();

        void onUnSupportBluetooth();
    }


    interface BluetoothListener {

        void OnBleDataComing(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic);

        void onServicesDiscovered(BluetoothGatt gatt, int status);

        void onDisConnect();
    }

    interface RegisterListener {
        void onRegisterSuccess(Context context, Intent intent);
    }

}
