package com.example.bluetoothutil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2018/3/28.
 */

public class BluetoothConnectImp implements IBluetoothConnectController {

    private BluetoothGatt mBluetoothGatt;
    private IBluetoothController iBluetoothController;
    private IBluetoothController.BluetoothListener bluetoothListener;

    private BluetoothGattCallback gattCallback;

    public BluetoothConnectImp(IBluetoothController iBluetoothController) {
        this.iBluetoothController = iBluetoothController;
    }

    public void setBluetoothListener(IBluetoothController.BluetoothListener bluetoothListener) {
        this.bluetoothListener = bluetoothListener;
    }

    public void connect(BluetoothDevice device, Context context) {
        if (gattCallback == null) {
            gattCallback = new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, final int status, final int newState) {
                    super.onConnectionStateChange(gatt, status, newState);
                    mBluetoothGatt = gatt;
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        gatt.discoverServices();
                        return;
                    }
                    if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        disConnect();
                        if (bluetoothListener != null) {
                            bluetoothListener.onDisConnect();
                        }
                        return;
                    }
                }

                @Override
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                    super.onServicesDiscovered(gatt, status);
                    mBluetoothGatt = gatt;
                    if (bluetoothListener != null) {
                        bluetoothListener.onServicesDiscovered(gatt, status);
                    }
                }

                @Override
                public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                    super.onCharacteristicChanged(gatt, characteristic);
                    mBluetoothGatt = gatt;
                    if (bluetoothListener != null) {
                        bluetoothListener.OnBleDataComing(gatt, characteristic);
                    }
                }

            };
        }
        disConnect();
        BluetoothAdapter adapter = iBluetoothController.getAdapter();
        if (adapter != null) {
            BluetoothDevice remoteDevice = adapter.getRemoteDevice(device.getAddress());
            remoteDevice.connectGatt(context, false, gattCallback);
        }

    }

    public void disConnect() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
    }

    public void connect(BluetoothDevice device, Context context, BluetoothGattCallback gattCallback) {
        disConnect();
        this.gattCallback = gattCallback;
        BluetoothAdapter adapter = iBluetoothController.getAdapter();
        if (adapter != null) {
            BluetoothDevice remoteDevice = adapter.getRemoteDevice(device.getAddress());
            remoteDevice.connectGatt(context, false, gattCallback);
        }
    }

}
