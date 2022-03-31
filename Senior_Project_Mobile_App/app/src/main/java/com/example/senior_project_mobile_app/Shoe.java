package com.example.senior_project_mobile_app;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

public class Shoe implements Serializable {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothAdapter.LeScanCallback scanCallback;
    private int status;
    private final ArrayList<BluetoothDevice> scanResult;
    private int scanCount;
    private final ArrayList<String> toRequest;
    private final MainActivity myActivity;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic defaultCharacteristic;

    public Shoe(MainActivity myActivity) {
        scanCount = 0;
        scanResult = new ArrayList<>();
        toRequest = new ArrayList<>();
        this.myActivity = myActivity;
        bluetoothGatt = null;
    }

    public void initializeDevice() {
        if (initialize(myActivity)) {
            status = myActivity.getResources().getInteger(R.integer.STATE_INITIALIZED);
        }
    }

    public void searchNearbyDevices() throws InterruptedException {
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED)
            ;
        if (status != myActivity.getResources().getInteger(R.integer.STATE_INITIALIZED)) {
            return;
        }
        status = myActivity.getResources().getInteger(R.integer.STATE_SCANNING);
        bluetoothAdapter.startLeScan(scanCallback);

    }

    public ArrayList<String> getNearbyDeviceNames() {
        if (status == myActivity.getResources().getInteger(R.integer.STATE_SCAN_FINISHED)) {
            ArrayList<String> names = new ArrayList<>();
            for (BluetoothDevice i : scanResult) {
                if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
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

    private boolean initialize(@NonNull MainActivity myActivity) {
        this.scanResult.clear();
        if (scanCallback == null)
            scanCallback = new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
                    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                        ;
                    if (bluetoothDevice != null && bluetoothDevice.getName() != null && !deviceExists(bluetoothDevice)) {
                        scanResult.add(bluetoothDevice);
                    }
                    scanCount++;
                    if (scanCount == myActivity.getResources().getInteger(R.integer.MAX_SEARCH_COUNT)) {
                        scanCount = 0;
                        status = myActivity.getResources().getInteger(R.integer.STATE_SCAN_FINISHED);
                        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED)
                            ;
                        bluetoothAdapter.stopLeScan(scanCallback);
                        myActivity.onBluetoothSearchFinished(getNearbyDeviceNames());
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

        BluetoothManager btManager = (BluetoothManager) myActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = btManager.getAdapter();
        if (bluetoothAdapter == null) {
            status = myActivity.getResources().getInteger(R.integer.STATE_BLUETOOTH_NOT_SUPPORTED);
            return false;
        }
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
            toRequest.add(Manifest.permission.BLUETOOTH);
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)
            toRequest.add(Manifest.permission.BLUETOOTH_ADMIN);
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            toRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            toRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (!toRequest.isEmpty()) {
            status = myActivity.getResources().getInteger(R.integer.STATE_PERMISSION_REQUIRED);
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
            if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                ;
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    private boolean isFirstTime() {
        return true;
    }

    public void startReading() {
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            ;
        bluetoothGatt.discoverServices();
    }

    public boolean connectToDevice(String name) {
        BluetoothDevice toConnect = getDeviceByName(name);
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            ;
        bluetoothGatt = toConnect.connectGatt(myActivity, true, new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Shoe.this.status = myActivity.getResources().getInteger(R.integer.STATE_DISCONNECTED);
                    myActivity.onBluetoothDisconnected();
                } else if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Shoe.this.status = myActivity.getResources().getInteger(R.integer.STATE_CONNECTED);
                    myActivity.onBluetoothConnected();
                } else if (newState == BluetoothProfile.STATE_CONNECTING) {
                    Shoe.this.status = myActivity.getResources().getInteger(R.integer.STATE_CONNECTING);
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                for (BluetoothGattService service : bluetoothGatt.getServices()) {
                    if (service.getUuid().toString().contains("ffe0")) {
                        for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                            if (characteristic.getUuid().toString().contains("ffe1")) {
                                if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                                    ;
                                bluetoothGatt.readCharacteristic(characteristic);
                                defaultCharacteristic = characteristic;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    String dataFromCharacteristic = new String(characteristic.getValue(), StandardCharsets.UTF_8);
                    myActivity.onDataReceived(dataFromCharacteristic);
                    sendACK();
                }
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                    ;
                gatt.executeReliableWrite();
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                    ;
                gatt.readCharacteristic(characteristic);
            }

            @Override
            public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                super.onReliableWriteCompleted(gatt, status);
            }
        });
        return true;
    }

    public void startDataNotify() {
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            ;
        bluetoothGatt.setCharacteristicNotification(defaultCharacteristic, true);
        UUID CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        BluetoothGattDescriptor descriptor = defaultCharacteristic.getDescriptor(CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID);
        boolean enabled = descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        bluetoothGatt.writeDescriptor(descriptor);
    }

    public void stopDataNotify() {
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            ;
        bluetoothGatt.setCharacteristicNotification(defaultCharacteristic, false);
        UUID CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
        BluetoothGattDescriptor descriptor = defaultCharacteristic.getDescriptor(CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID);
        boolean enabled = descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        bluetoothGatt.writeDescriptor(descriptor);
    }

    private void sendACK() {
        defaultCharacteristic.setValue("1".getBytes(StandardCharsets.UTF_8));
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            ;
        bluetoothGatt.writeCharacteristic(defaultCharacteristic);
    }

    public String[] getRequiredPerms() {
        String[] perms = new String[toRequest.size()];
        for (int i = 0; i < perms.length; i++) {
            perms[i] = toRequest.get(i);
        }
        return (toRequest.isEmpty()) ? null : perms;
    }
}
