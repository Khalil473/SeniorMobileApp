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
import android.os.Handler;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

public class Shoe {
  private BluetoothAdapter bluetoothAdapter;
  private final BluetoothAdapter.LeScanCallback scanCallback;
  private int status;
  private final ArrayList<BluetoothDevice> scanResult;
  private final ArrayList<String> toRequest;
  private final MainActivity myActivity;
  private BluetoothGatt bluetoothGatt;
  private final BluetoothGattCallback bluetoothGattCallback;
  private BluetoothGattCharacteristic defaultCharacteristic;
  private OnBluetoothConnectedListener bluetoothConnectedListener;
  private OnDataReceivedListener dataReceivedListener;
  private OnBluetoothSearchFinishedListener bluetoothSearchFinishedListener;
  private OnBluetoothDisconnectedListener bluetoothDisconnectedListener;
  private OnHistoryReadFinishedListener historyReadFinishedListener;
  private OnWriteFinishedListener writeFinishedListener;
  public static final int STATE_BLUETOOTH_NOT_SUPPORTED = 3;
  public static final int STATE_CONNECTED = 6;
  public static final int STATE_CONNECTING = 7;
  public static final int STATE_DISCONNECTED = 5;
  public static final int STATE_INITIALIZED = 0;
  public static final int STATE_PERMISSION_REQUIRED = 4;
  public static final int STATE_SCANNING = 1;
  public static final int STATE_SCAN_FINISHED = 2;
  public static final int STATE_BLUETOOTH_DISABLED = 8;
  public static final int STATE_READING_HISTORY = 9;
  public static final int STATE_SENDING_ACK = 10;
  public static final int STATE_SENDING_DATA = 11;
  public final ArrayList<Integer> historyData;

  public Shoe(MainActivity myActivity) {
    scanResult = new ArrayList<>();
    toRequest = new ArrayList<>();
    this.myActivity = myActivity;
    bluetoothGatt = null;
    historyData = new ArrayList<>();
    scanCallback =
        new BluetoothAdapter.LeScanCallback() {
          @Override
          public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            if (ActivityCompat.checkSelfPermission(
                    myActivity, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED)
              ;
            if (bluetoothDevice != null
                && bluetoothDevice.getName() != null
                && !deviceExists(bluetoothDevice)) {
              scanResult.add(bluetoothDevice);
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
    bluetoothGattCallback =
        new BluetoothGattCallback() {
          @Override
          public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
              Shoe.this.status = STATE_DISCONNECTED;
              myActivity.runOnUiThread(
                  new Runnable() {
                    @Override
                    public void run() {
                      bluetoothDisconnectedListener.bluetoothDisconnected();
                    }
                  });
            } else if (newState == BluetoothProfile.STATE_CONNECTED) {
              Shoe.this.status = STATE_CONNECTED;
              myActivity.runOnUiThread(
                  new Runnable() {
                    @Override
                    public void run() {
                      bluetoothConnectedListener.bluetoothConnectedListener();
                    }
                  });
            } else if (newState == BluetoothProfile.STATE_CONNECTING) {
              Shoe.this.status = STATE_CONNECTING;
            }
          }

          @Override
          public void onServicesDiscovered(BluetoothGatt gatt, int status) { // TODO: Redo the code.
            if (ActivityCompat.checkSelfPermission(
                    myActivity, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED)
              ;
            for (BluetoothGattService service : bluetoothGatt.getServices()) {
              if (service.getUuid().toString().contains("ffe0")) {
                for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                  if (characteristic.getUuid().toString().contains("ffe1")) {
                    defaultCharacteristic = characteristic;
                  }
                }
              }
            }
          }

          @Override
          public void onCharacteristicRead(
              BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
              String dataFromCharacteristic =
                  new String(characteristic.getValue(), StandardCharsets.UTF_8);
              myActivity.runOnUiThread(
                  new Runnable() {
                    @Override
                    public void run() {
                      dataReceivedListener.dataReceivedListener(dataFromCharacteristic);
                    }
                  });

              sendACK();
            }
          }

          @Override
          public void onCharacteristicWrite(
              BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (ActivityCompat.checkSelfPermission(
                    myActivity, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED)
              ;
            gatt.executeReliableWrite();
          }

          @Override
          public void onCharacteristicChanged(
              BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (ActivityCompat.checkSelfPermission(
                    myActivity, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED)
              ;
            gatt.readCharacteristic(characteristic);
          }

          @Override
          public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
            if (writeFinishedListener != null)
              myActivity.runOnUiThread(
                  new Runnable() {
                    @Override
                    public void run() {
                      writeFinishedListener.writeFinished();
                    }
                  });
            status = STATE_CONNECTED;
          }
        };
  }

  public void initializeDevice() {
    if (initialize(myActivity)) {
      status = STATE_INITIALIZED;
    }
  }

  public void searchNearbyDevices()
      throws InterruptedException { // TODO: check if the gps is enabled, if not enable it.

    if (status != STATE_INITIALIZED) {
      return;
    }

    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_SCAN)
        != PackageManager.PERMISSION_GRANTED)
      ;
    Handler handler = new Handler();
    handler.postDelayed(
        () -> {
          if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_SCAN)
              != PackageManager.PERMISSION_GRANTED)
            ;
          status = STATE_SCAN_FINISHED;
          if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_SCAN)
              != PackageManager.PERMISSION_GRANTED)
            ;
          bluetoothAdapter.stopLeScan(scanCallback);
          myActivity.runOnUiThread(
              new Runnable() {
                @Override
                public void run() {
                  bluetoothSearchFinishedListener.bluetoothSearchFinishedListener(
                      getNearbyDeviceNames());
                }
              });
        },
        myActivity.getResources().getInteger(R.integer.MAX_SEARCH_TIME));
    bluetoothAdapter.startLeScan(scanCallback);
    status = STATE_INITIALIZED;
  }

  public void stopBluetoothScan() {
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_SCAN)
        != PackageManager.PERMISSION_GRANTED)
      ;
    bluetoothAdapter.stopLeScan(scanCallback);
  }

  public void setOnDataReceivedListener(OnDataReceivedListener listener) {
    dataReceivedListener = listener;
  }

  public void setOnBluetoothConnectedListener(OnBluetoothConnectedListener listener) {
    bluetoothConnectedListener = listener;
  }

  public void setOnBluetoothSearchFinished(OnBluetoothSearchFinishedListener listener) {
    bluetoothSearchFinishedListener = listener;
  }

  public void setOnBluetoothDisconnectedListener(OnBluetoothDisconnectedListener listener) {
    bluetoothDisconnectedListener = listener;
  }

  public ArrayList<String> getNearbyDeviceNames() {
    if (status == STATE_SCAN_FINISHED) {
      ArrayList<String> names = new ArrayList<>();
      for (BluetoothDevice i : scanResult) {
        if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
            != PackageManager.PERMISSION_GRANTED)
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
    BluetoothManager btManager =
        (BluetoothManager) myActivity.getSystemService(Context.BLUETOOTH_SERVICE);
    bluetoothAdapter = btManager.getAdapter();
    if (bluetoothAdapter == null) {
      status = STATE_BLUETOOTH_NOT_SUPPORTED;
      return false;
    }
    if (!bluetoothAdapter.isEnabled()) {
      status = STATE_BLUETOOTH_DISABLED;
      return false;
    }
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH)
        != PackageManager.PERMISSION_GRANTED) toRequest.add(Manifest.permission.BLUETOOTH);
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_ADMIN)
        != PackageManager.PERMISSION_GRANTED) toRequest.add(Manifest.permission.BLUETOOTH_ADMIN);
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED)
      toRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED)
      toRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION);

    if (!toRequest.isEmpty()) {
      status = STATE_PERMISSION_REQUIRED;
      return false;
    }
    return true;
  }

  public boolean enableBluetooth() {
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED)
      ;
    return bluetoothAdapter.enable();
  }

  private BluetoothDevice getDeviceByName(String name) {
    for (BluetoothDevice i : scanResult) {
      if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
          != PackageManager.PERMISSION_GRANTED)
        ;
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }

  public boolean isFirstTime() { // TODO : implement to check if this is the first time
    return true;
  }

  public void startReading() {
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED)
      ;
    bluetoothGatt.discoverServices();
  }

  public void connectToDevice(String name) {
    BluetoothDevice toConnect = getDeviceByName(name);
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED)
      ;
    if (toConnect == null) {
      Toast.makeText(myActivity, "No device Found with this name!", Toast.LENGTH_SHORT).show();
      return;
    }
    bluetoothGatt =
        toConnect.connectGatt(myActivity, false, bluetoothGattCallback); // TODO: Fix auto connect-
    bluetoothConnectedListener.bluetoothConnectedListener();
    status = STATE_CONNECTED;
  }

  public void read() { // TODO: continue the implementation and check if works.
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED)
      ;
    if (defaultCharacteristic == null) {
      Toast.makeText(myActivity, "Not ready yet!", Toast.LENGTH_SHORT).show();
      return;
    }
    bluetoothGatt.readCharacteristic(defaultCharacteristic);
  }

  public void startDataNotify() {
    defaultCharacteristic.setValue("connected".getBytes(StandardCharsets.UTF_8));
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED)
      ;
    bluetoothGatt.writeCharacteristic(defaultCharacteristic);
    setOnWriteFinishedListener(
        new OnWriteFinishedListener() {
          @Override
          public void writeFinished() {
            myActivity.runOnUiThread(
                new Runnable() {
                  @Override
                  public void run() {
                    if (ActivityCompat.checkSelfPermission(
                            myActivity, Manifest.permission.BLUETOOTH_CONNECT)
                        != PackageManager.PERMISSION_GRANTED)
                      ;
                    bluetoothGatt.setCharacteristicNotification(defaultCharacteristic, true);
                    UUID CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID =
                        UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
                    BluetoothGattDescriptor descriptor =
                        defaultCharacteristic.getDescriptor(
                            CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID);
                    boolean enabled =
                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    bluetoothGatt.writeDescriptor(descriptor);
                  }
                });
          }
        });
  }

  public void stopDataNotify() {
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED)
      ;
    bluetoothGatt.setCharacteristicNotification(defaultCharacteristic, false);
    UUID CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID =
        UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    BluetoothGattDescriptor descriptor =
        defaultCharacteristic.getDescriptor(CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID);
    boolean enabled = descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
    bluetoothGatt.writeDescriptor(descriptor);
  }

  private void sendACK() {
    defaultCharacteristic.setValue("1".getBytes(StandardCharsets.UTF_8));
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED)
      ;
    bluetoothGatt.writeCharacteristic(defaultCharacteristic);
    status = STATE_SENDING_ACK;
  }

  public boolean isReadyToRead() {
    return defaultCharacteristic != null;
  }

  public void disconnectDevice() {
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED)
      ;
    if (getStatus() == STATE_CONNECTED) bluetoothGatt.disconnect();
  }

  public String[] getRequiredPerms() {
    String[] perms = new String[toRequest.size()];
    for (int i = 0; i < perms.length; i++) {
      perms[i] = toRequest.get(i);
    }
    return (toRequest.isEmpty()) ? null : perms;
  }

  public void sendData(String data) {
    defaultCharacteristic.setValue(data.getBytes(StandardCharsets.UTF_8));
    if (ActivityCompat.checkSelfPermission(myActivity, Manifest.permission.BLUETOOTH_CONNECT)
        != PackageManager.PERMISSION_GRANTED)
      ;
    bluetoothGatt.writeCharacteristic(defaultCharacteristic);
    status = STATE_SENDING_DATA;
  }

  public void startHistoryReading(String type) {
    sendData("h" + type);
    status = STATE_READING_HISTORY;
  }

  public void setOnHistoryReadFinished(OnHistoryReadFinishedListener historyReadFinished) {
    this.historyReadFinishedListener = historyReadFinished;
  }

  public void setOnWriteFinishedListener(OnWriteFinishedListener writeFinishedListener) {
    this.writeFinishedListener = writeFinishedListener;
  }

  public boolean isReadyForMainScreen() {
    return !(defaultCharacteristic == null);
  }
}
