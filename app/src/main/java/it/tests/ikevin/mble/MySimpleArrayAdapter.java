package it.tests.ikevin.mble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
* Created by AnnaMaria on 24/06/2015.
*/
public class MySimpleArrayAdapter extends ArrayAdapter<BluetoothGattCharacteristic> {
    private final Context context;
    public MySimpleArrayAdapter(Context context, List<BluetoothGattCharacteristic> characteristicList) {
      super(context, R.layout.device_list_item, characteristicList);
      this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.device_list_item, parent, false);
      TextView serviceText = (TextView) rowView.findViewById(R.id.deviceName);
      // Not used (only one field per characteristic)
      // TextView characteristicsText = (TextView) rowView.findViewById(R.id.deviceAddress);
      String characteristic = ServiceActivity.mCharacteristicList.get(position).getUuid().toString();
      serviceText.setText(characteristic);
      if (characteristic.equalsIgnoreCase(TumakuBLE.CHARACTERISTIC_CONTROL)) rowView.setBackgroundColor(Color.GREEN);
      if (characteristic.equalsIgnoreCase(TumakuBLE.SENSORTAG_HUMIDITY_DATA)) rowView.setBackgroundColor(Color.RED);
      if (characteristic.equalsIgnoreCase(TumakuBLE.SENSORTAG_HUMIDITY_CONF)) rowView.setBackgroundColor(Color.RED);
      if (characteristic.equalsIgnoreCase(TumakuBLE.SENSORTAG_KEY_DATA)) rowView.setBackgroundColor(Color.YELLOW);
      if (characteristic.equalsIgnoreCase(TumakuBLE.SENSORTAG_IR_TEMPERATURE_DATA)) rowView.setBackgroundColor(Color.RED);
      if (characteristic.equalsIgnoreCase(TumakuBLE.SENSORTAG_IR_TEMPERATURE_CONF)) rowView.setBackgroundColor(Color.RED);
      if (characteristic.equalsIgnoreCase(TumakuBLE.TETHERCELL_AUTH_PIN)) rowView.setBackgroundColor(Color.BLUE);
      if (characteristic.equalsIgnoreCase(TumakuBLE.TETHERCELL_VOLTAGE)) rowView.setBackgroundColor(Color.BLUE);
      if (characteristic.equalsIgnoreCase(TumakuBLE.TETHERCELL_STATE)) rowView.setBackgroundColor(Color.BLUE);
      if (characteristic.equalsIgnoreCase(TumakuBLE.TETHERCELL_UTC)) rowView.setBackgroundColor(Color.BLUE);
      if (characteristic.equalsIgnoreCase(TumakuBLE.TETHERCELL_PERIOD)) rowView.setBackgroundColor(Color.BLUE);
      if (characteristic.equalsIgnoreCase(TumakuBLE.TETHERCELL_TIMER_ARRAY)) rowView.setBackgroundColor(Color.BLUE);
      if (characteristic.equalsIgnoreCase(TumakuBLE.TETHERCELL_TIMER_ARRAY_INDEX)) rowView.setBackgroundColor(Color.BLUE);

      if (characteristic.equalsIgnoreCase(TumakuBLE.BLEDUINO_UART_RX)) rowView.setBackgroundColor(Color.BLUE);
      if (characteristic.equalsIgnoreCase(TumakuBLE.BLEDUINO_UART_TX)) rowView.setBackgroundColor(Color.BLUE);
      return rowView;
    }
  }
