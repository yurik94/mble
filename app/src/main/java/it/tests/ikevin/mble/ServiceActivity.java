/*
 * Created by Javier Montaner  (twitter: @tumaku_) during M-week (February 2014) of MakeSpaceMadrid
 * http://www.makespacemadrid.org
 * @ 2014 Javier Montaner
 * 
 * Licensed under the MIT Open Source License 
 * http://opensource.org/licenses/MIT
 * 
 * Many thanks to Yeelight (special mention to Daping Liu) and Double Encore (Dave Smith)
 * for their support and shared knowlegde
 * 
 * Based on the API released by Yeelight:
 * http://www.yeelight.com/en_US/info/download
 * 
 * Based on the code created by Dave Smith (Double Encore):
 * https://github.com/devunwired/accessory-samples/tree/master/BluetoothGatt
 * http://www.doubleencore.com/2013/12/bluetooth-smart-for-android/
 * 
 * 
 * Scan Bluetooth Low Energy devices and their services and characteristics.
 * If the Yeelight Service is found, an activity can be launched to control colour and intensity of Yeelight Blue bulb
 * 
 * Tested on a Nexus 7 (2013)
 * 
 */

package it.tests.ikevin.mble;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.ListActivity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ServiceActivity extends ListActivity 
	implements OnItemClickListener{
	
	public static List <BluetoothGattCharacteristic> mCharacteristicList=null;
	private ListView mListView;
	private MySimpleArrayAdapter mAdapter=null;
	private Context mContext=null;
	private String mServiceUUID;
	private TumakuBLE mTumakuBLE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("JMG","CharacteristicActivity triggered OnCreate()");
		mContext=this;
		//mTumakuBLE= TumakuBLE.getInstance(this);
		mTumakuBLE=((TumakuBLEApplication)getApplication()).getTumakuBLEInstance(this);
		mCharacteristicList=new ArrayList<BluetoothGattCharacteristic>();
		mServiceUUID =getIntent().getStringExtra(TumakuBLE.EXTRA_SERVICE);
		if(mServiceUUID==null) {
	    	if (Constant.DEBUG) Log.i("JMG", "No service data defined in intent");
    	    Toast.makeText(this, "No service data defined in intent", Toast.LENGTH_SHORT).show();    	    	
			finish();
		}
    	if (Constant.DEBUG) {
    		Log.i("JMG", "Service UUID: "+ mServiceUUID);
    	}
		
		setTitle("Service UUID: "+ mServiceUUID);
		TumakuBLE.ServiceType serviceType= mTumakuBLE.getService(mServiceUUID);
    	mCharacteristicList= serviceType.getCharacteristics();
		
        mListView = getListView();
        mAdapter=new MySimpleArrayAdapter(this,mCharacteristicList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);    		
	}
	
		
    @Override
    public void onItemClick(AdapterView<?> adapter, View view,
                int position, long id) {     	
	    if (Constant.DEBUG) Log.i("JMG", "Selected characteristic " + mCharacteristicList.get(position).getUuid().toString());
	    String selectedCharacteristic=mCharacteristicList.get(position).getUuid().toString();
		    	   if (selectedCharacteristic.equalsIgnoreCase(TumakuBLE.SENSORTAG_KEY_DATA)) {
					    if (Constant.DEBUG) Log.i("JMG", "HM10 Serial Pass Through Characteristic selected. Launch HM10 activity");
				    	Intent intentActivity= new Intent(mContext, HM10Activity.class);
				    	intentActivity.putExtra(TumakuBLE.EXTRA_ADDRESS,mTumakuBLE.getDeviceAddress());
				    	mContext.startActivity(intentActivity);	 		    		   
		    	   } else {
		    			  if ((selectedCharacteristic.equalsIgnoreCase(TumakuBLE.BLEDUINO_UART_RX))||
				   		    		(selectedCharacteristic.equalsIgnoreCase(TumakuBLE.BLEDUINO_UART_TX))) {
				   				    if (Constant.DEBUG) Log.i("JMG", "BLEduino UART Characteristic selected. Launch BLEduino UART activity");
				   			    	Intent intentActivity= new Intent(mContext, BLEduinoUartActivity.class);
				   			    	intentActivity.putExtra(TumakuBLE.EXTRA_ADDRESS,mTumakuBLE.getDeviceAddress());
				   			    	mContext.startActivity(intentActivity);	 
		    			  } else
		    				  Toast.makeText(this, "No management window defined for characteristic " + mCharacteristicList.get(position), Toast.LENGTH_SHORT).show();  
		    	   }
		    	}
	    	}
    
