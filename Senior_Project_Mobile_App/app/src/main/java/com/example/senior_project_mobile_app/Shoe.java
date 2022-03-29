package com.example.senior_project_mobile_app;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.Serializable;
import java.util.ArrayList;

public class Shoe implements Serializable {
    private final Context myContext;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothAdapter.LeScanCallback scanCallback;
    private int status;
    private final ArrayList<BluetoothDevice> scanResult;
    private int scanCount;
    private final ArrayList<String> toRequest;
    private final MainActivity myActivity;
    private BluetoothGatt bluetoothGatt;

    public Shoe(Context myContext) {
        this.myContext = myContext;
        scanCount = 0;
        scanResult = new ArrayList<>();
        toRequest = new ArrayList<>();
        myActivity = null;
        bluetoothGatt = null;
    }

    public Shoe(Context myContext, MainActivity myActivity) {
        this.myContext = myContext;
        scanCount = 0;
        scanResult = new ArrayList<>();
        toRequest = new ArrayList<>();
        this.myActivity = myActivity;
        bluetoothGatt = null;
    }

    public void initializeDevice() {
        if (initialize(myContext)) {
            status = myContext.getResources().getInteger(R.integer.STATE_INITIALIZED);
        }
    }

    public void searchNearbyDevices() throws InterruptedException {
        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED)
            ;
        if (status != myContext.getResources().getInteger(R.integer.STATE_INITIALIZED)) {
            return;
        }
        status = myContext.getResources().getInteger(R.integer.STATE_SCANNING);
        bluetoothAdapter.startLeScan(scanCallback);

    }

    public ArrayList<String> getNearbyDeviceNames() {
        if (status == myContext.getResources().getInteger(R.integer.STATE_SCAN_FINISHED)) {
            ArrayList<String> names = new ArrayList<>();
            for (BluetoothDevice i : scanResult) {
                if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                    ;
                names.add(i.getName());
            }
            return names;
        }
        return null;
    }

    public int getStatus() {
        return this.status;
    }

    private boolean initialize(@NonNull Context myContext) {
        this.scanResult.clear();
        if (scanCallback == null)
            scanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
                    if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                        ;
                    if (bluetoothDevice != null && bluetoothDevice.getName() != null && !deviceExists(bluetoothDevice)) {
                        scanResult.add(bluetoothDevice);
                    }
                    scanCount++;
                    if (scanCount == myContext.getResources().getInteger(R.integer.MAX_SEARCH_COUNT)) {
                        scanCount = 0;
                        status = myContext.getResources().getInteger(R.integer.STATE_SCAN_FINISHED);
                        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED)
                            ;
                        bluetoothAdapter.stopLeScan(scanCallback);
                        if (myActivity != null) {
                            //myActivity.showDevices(getNearbyDeviceNames());
                        }
                    }
                }

                private boolean deviceExists(BluetoothDevice device) {
                    for (BluetoothDevice i : scanResult) {
                        if (i.getAddress().equals(device.getAddress())) {
                            return true;
                        }
                    }
                    return false;
                }
            };

        BluetoothManager btManager = (BluetoothManager) myContext.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = btManager.getAdapter();
        if (bluetoothAdapter == null) {
            status = myContext.getResources().getInteger(R.integer.STATE_BLUETOOTH_NOT_SUPPORTED);
            return false;
        }
        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
            toRequest.add(Manifest.permission.BLUETOOTH);
        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)
            toRequest.add(Manifest.permission.BLUETOOTH_ADMIN);
        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            toRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            toRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (!toRequest.isEmpty()) {
            status = myContext.getResources().getInteger(R.integer.STATE_PERMISSION_REQUIRED);
            return false;
        }
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            return false;
        }
        return true;
    }

    private BluetoothDevice getDeviceByName(String name) {
        for (BluetoothDevice i : this.scanResult) {
            if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                ;
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    public boolean connectToDevice(String name) {
        BluetoothDevice toConnect = getDeviceByName(name);
        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED);
        bluetoothGatt = toConnect.connectGatt(myContext, false, new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicRead(gatt, characteristic, status);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicChanged(gatt, characteristic);
            }

            @Override
            public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                super.onReliableWriteCompleted(gatt, status);
            }
        });
        return true;
    }
    public String[] getRequiredPerms() {
        String[] perms=new String[toRequest.size()];
        for(int i=0;i<perms.length;i++){
            perms[i]= toRequest.get(i);
        }
        return (toRequest.isEmpty()) ? null : perms;
    }
}
