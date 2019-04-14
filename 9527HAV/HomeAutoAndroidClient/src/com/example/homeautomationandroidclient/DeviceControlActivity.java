package com.example.homeautomationandroidclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import MyHomeAutomationUserPack.DeviceDetails;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class DeviceControlActivity extends Activity {

	private static final String NAMESPACE = "http://MyServerPack/";
	private static final String URL = "http://"
			+ com.example.homeautomationandroidclient.ServerIp.setting
			+ ":8080/HomeAutomationServer/HomeAutomationService?wsdl";;
	private static final String SOAP_ACTION = "HomeAutomationService";
	DeviceDetails deviceData;
	DeviceDetails toWebControl;
	Button btnReleaseControl;
	Bundle recDeviceInfo;
	SeekBar seekBarTh1;
	SeekBar seekBarTh2;
	SeekBar seekBarTh3;
	SeekBar seekBarTh4;
	SeekBar seekBarTh5;
	SeekBar seekBarTh6;
	SeekBar seekBarTh7;
	SeekBar seekBarTh8;
	ToggleButton mySwitch[];
	SeekBar myTh[];
	int outData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_control);
		recDeviceInfo = getIntent().getExtras();
		deviceData = (DeviceDetails) recDeviceInfo
				.getSerializable("deviceInfo");
		toWebControl = new DeviceDetails();
		toWebControl.userId = deviceData.userId;
	//	System.out.println("User Id on Control dEvice=" + toWebControl.userId);
		btnReleaseControl = (Button) findViewById(R.id.btnReleaseControl);
		btnReleaseControl.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int temp = 0;
				for (int i = 0; i < 8; i++) {

					if (mySwitch[i].isChecked()) {
						System.out.println("in here:is checked ");
						temp |= (1 << i);
					}

				}
				toWebControl.deviceStatus = temp;
				for (int i = 0; i < 8; i++) {
					if (myTh[i].isEnabled()) {
						toWebControl.th.add(myTh[i].getProgress());
					} else {
						toWebControl.th.add(-1);
					}
				}

				LongOperation user = new LongOperation();
				user.execute("");
		//		System.out.println("In.....");
				finish();
			}
		});
		mySwitch = new ToggleButton[8];
		mySwitch[0] = (ToggleButton) findViewById(R.id.toggleButtonDev1);
		mySwitch[1] = (ToggleButton) findViewById(R.id.toggleButtonDev2);
		mySwitch[2] = (ToggleButton) findViewById(R.id.toggleButtonDev3);
		mySwitch[3] = (ToggleButton) findViewById(R.id.toggleButtonDev4);
		mySwitch[4] = (ToggleButton) findViewById(R.id.toggleButtonDev5);
		mySwitch[5] = (ToggleButton) findViewById(R.id.toggleButtonDev6);
		mySwitch[6] = (ToggleButton) findViewById(R.id.toggleButtonDev7);
		mySwitch[7] = (ToggleButton) findViewById(R.id.toggleButtonDev8);

		myTh = new SeekBar[8];
		myTh[0] = (SeekBar) findViewById(R.id.seekBarTh1);
		myTh[1] = (SeekBar) findViewById(R.id.seekBarTh2);
		myTh[2] = (SeekBar) findViewById(R.id.seekBarTh3);
		myTh[3] = (SeekBar) findViewById(R.id.seekBarTh4);
		myTh[4] = (SeekBar) findViewById(R.id.seekBarTh5);
		myTh[5] = (SeekBar) findViewById(R.id.seekBarTh6);
		myTh[6] = (SeekBar) findViewById(R.id.seekBarTh7);
		myTh[7] = (SeekBar) findViewById(R.id.seekBarTh8);
		for (int i = 0; i < 8; i++) {
			myTh[i].setMax(255);
		}
		int temp = 1;
		for (int i = 0; i < 8; i++) {
			if ((temp & deviceData.deviceStatus) == 0) {
				mySwitch[i].setChecked(false);
			} else {
				mySwitch[i].setChecked(true);
			}

			temp *= 2;
		}

		for (int i = 0; i < 8; i++) {
			if (deviceData.auto.get(i).equals("Yes")) {

				mySwitch[i].setEnabled(false);
				myTh[i].setProgress(deviceData.th.get(i));
				myTh[i].setEnabled(true);

			} else {
				mySwitch[i].setEnabled(true);
				myTh[i].setProgress(deviceData.th.get(i));
				myTh[i].setEnabled(false);
			}
		}
	}

	public void takeSensorValue(int SensorNo) {
		if (myTh[SensorNo].isEnabled()) {
			toWebControl.th.add(myTh[SensorNo].getProgress());
		}

	}

	public class LongOperation extends AsyncTask<String, Void, String> {
		String resp;
		@Override
		protected String doInBackground(String... params) {
			String METHOD_NAME = "addClientCommands";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			PropertyInfo pi = new PropertyInfo();
			pi.setName("clientCommands");
			pi.setValue(objectToString(toWebControl));
			pi.setType(String.class);
			request.addProperty(pi);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			try {
				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				resp = resultsRequestSOAP
						.getPrimitivePropertyAsString("return");
			} catch (Exception e) {
				System.out.println("Error in writing device details data");
				e.printStackTrace();
			}
			return null;
		}
	}

	String objectToString(Object obj) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(obj);
			b = bos.toByteArray();
		} catch (Exception e) {
			System.out.println("NOT SERIALIZABLE: " + e);
		}
		return Base64.encode(b);
	}

	Object stringToObject(String inp) {
		byte b[] = Base64.decode(inp);
		Object ret = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
			ObjectInput in = new ObjectInputStream(bis);
			ret = (Object) in.readObject();
			bis.close();
			in.close();
		} catch (Exception e) {
			System.out.println("NOT DE-SERIALIZABLE: " + e);
		}
		return ret;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.device_control, menu);
		return true;
	}

}
