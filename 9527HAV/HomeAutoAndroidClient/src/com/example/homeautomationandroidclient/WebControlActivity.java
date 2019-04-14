package com.example.homeautomationandroidclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import MyHomeAutomationUserPack.DeviceDetails;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class WebControlActivity extends Activity {
	DeviceDetails fromServer;
	Button btnSwitchControl;

	byte[] temp;
	String videoFeed = "", allow = "";
	 String device = "", notEmpty = "";
	Bitmap b, bitmap;
	int imageArray[];
	Paint p;
	Canvas c, canvas;
	ImageView iv, imageViewVideo;
	String uname = "", resp = "";
	Handler h;
	private static final String NAMESPACE = "http://MyServerPack/";
	private static final String URL = "http://"
			+ com.example.homeautomationandroidclient.ServerIp.setting
			+ ":8080/HomeAutomationServer/HomeAutomationService?wsdl";;
	private static final String SOAP_ACTION = "HomeAutomationService";
	int blueColor = Color.rgb(0, 162, 232);
	int greyColor = Color.rgb(37, 37, 37);
	boolean once = true;
	Runnable r;
	public static int DISTANCE = 48;
	boolean running = true, executeLongOperation = true, response = true,
			response1 = false;
	String METHOD_NAME = "videoStreamForAndroid";;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_control);
		temp = new byte[320 * 240 * 3];
		btnSwitchControl = (Button) findViewById(R.id.btnSwitchControl);
		btnSwitchControl.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						DeviceControlActivity.class);
				i.putExtra("deviceInfo", fromServer);
				//running=false;
				startActivity(i);
			}
		});
		Bundle bun = getIntent().getExtras();
		uname = bun.getString("UserName");
		iv = (ImageView) findViewById(R.id.imageView1);
		imageViewVideo = (ImageView) findViewById(R.id.imageViewVF);
		running = true;
		bitmap = Bitmap.createBitmap(320, 240, Config.ARGB_8888);
		canvas = new Canvas(bitmap);

		h = new Handler(Looper.getMainLooper());
		h.postDelayed(r = new Runnable() {

			@Override
			public void run() {
				if (running) {

					if (once) {
						b = Bitmap.createBitmap(iv.getWidth(), 365,
								Config.ARGB_8888);
					}
					if (response) {
						LongOperation lo = new LongOperation();
						lo.execute("");
						c = new Canvas(b);
						p = new Paint();
						p.setColor(Color.BLACK);
						c.drawRect(0, 0, iv.getWidth(), 365, p);
						p.setColor(greyColor);
						c.drawRect(0, 10, iv.getWidth(), DISTANCE, p);
						p.setStrokeWidth(15);
						p.setColor(greyColor);
						for (int i = 0; i < 8; i++) {
							c.drawLine((20 + (i * DISTANCE)), 100,
									(20 + (i * DISTANCE)), 355, p);
						}
						// System.out.println("From Server: "+notEmpty);
						if (notEmpty.equals("1")) {
							fromServer = (DeviceDetails) stringToObject(device);
							System.out.println("FromServer= "
									+ fromServer.deviceStatus);
							p.setColor(Color.rgb(223, 223, 223));
							if (fromServer.allControl.equals("Yes")) {
								c.drawText("Control Allowed", 10, 30, p);
							} else {
								c.drawText("Control Not-Allowed", 10, 30, p);
							}
							if (fromServer.feedback.equals("Yes")) {
								c.drawText("Feedback Generating...",
										iv.getWidth() / 2, 30, p);
							} else {
								c.drawText("Feedback Stoped",
										iv.getWidth() / 2, 30, p);
							}
							p.setStrokeWidth(1);
							p.setColor(Color.rgb(223, 223, 223));
							for (int i = 0; i < 8; i++) {
								c.drawText("" + fromServer.sensor.get(i),
										(18 + (i * DISTANCE)), 70, p);
							}

							for (int i = 0; i < 8; i++) {
								p.setStrokeWidth(15);
								p.setColor(blueColor);
								c.drawLine((20 + (i * DISTANCE)), 355,
										(20 + (i * DISTANCE)),
										355 - fromServer.sensor.get(i), p);
								p.setStrokeWidth(3);
								p.setColor(Color.RED);
								c.drawLine(12 + (i * DISTANCE),
										355 - fromServer.th.get(i),
										30 + (i * DISTANCE),
										355 - fromServer.th.get(i), p);
								c.drawText("" + fromServer.th.get(i),
										30 + (i * DISTANCE),
										355 - fromServer.th.get(i), p);
							}
							if (fromServer.allControl.equals("Yes")) {
								btnSwitchControl.setEnabled(true);
							} else {
								btnSwitchControl.setEnabled(false);
							}
							p.setColor(greyColor);
							c.drawRect(390, 100, iv.getWidth() - 10, 355, p);
							p.setColor(Color.rgb(223, 223, 223));
							for (int i = 0; i < 8; i++) {
								c.drawText("Device " + (i + 1) + ":", 395,
										120 + (i * 32), p);
							}
							int timerValue = 1;
							String stat = "";
							for (int i = 0; i < 8; i++) {
								if ((timerValue & fromServer.deviceStatus) == 0) {
									p.setColor(Color.GREEN);
									stat = "OFF";
								} else {
									p.setColor(Color.RED);
									stat = "ON";
								}
								c.drawText(stat, 445, 120 + (i * 32), p);
								timerValue *= 2;
							}
						}
						iv.setImageBitmap(b);
						response = false;
						response1 = true;
					} else if (response1) {
						LongOperation1 oo = new LongOperation1();
						oo.execute("");
						
						imageArray = new int[320 * 240];
						if (allow.equals("1")) {
							temp = (byte[]) stringToObject(videoFeed);
							int cnt = 0, k = 0;
							for (int y = 0; y < 240; y++) {
								for (int x = 0; x < 320; x++) {
									int col = (temp[cnt] & 0xff) << 16;
									cnt++;
									col |= (temp[cnt] & 0xff) << 8;
									cnt++;
									col |= (temp[cnt] & 0xff);
									cnt++;
									imageArray[k] = col;
									k++;
								}
							}

							System.out.println("Lenth of imageArray= "
									+ imageArray.length);
							canvas.drawBitmap(imageArray, 0, 320, 0, 0, 320,
									240, false, p);
							imageViewVideo.setImageBitmap(bitmap);
							imageViewVideo.invalidate();
						}

						response = true;
						response1 = false;
					}
				}
				h.postAtTime(this, SystemClock.uptimeMillis() +800);
			}
		},200);
	}

	@Override
	protected void onResume() {
		running = true;
		System.out.println("In Resume");
		executeLongOperation = true;
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		running = false;
		executeLongOperation = false;
		h.removeCallbacks(r);
		super.onBackPressed();
	}

	public class LongOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			METHOD_NAME = "fetchdataAndroid";
			// response = false;
			System.out.println("Calling: " + METHOD_NAME);
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			PropertyInfo pi = new PropertyInfo();
			pi.setName("uid");
			pi.setValue(uname);
			pi.setType(String.class);
			request.addProperty(pi);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			try {
				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				resp = resultsRequestSOAP
						.getPrimitivePropertyAsString("return");
				 Thread.sleep(100);
				
					StringTokenizer st = new StringTokenizer(resp, "###");
					while (st.hasMoreElements()) {
						device = st.nextToken();
						notEmpty = st.nextToken();

					}
			} catch (Exception e) {
				System.out.println("Error in reading device details data");
				e.printStackTrace();
			}
//			if (resp != null) {
//				System.out.println("Returning response for " + METHOD_NAME);
//				//response = true;
//			}
			return "";
		}

	}

	public class LongOperation1 extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			METHOD_NAME = "videoStreamForAndroid";
			// response = false;
			System.out.println("Calling: " + METHOD_NAME);
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			PropertyInfo pi = new PropertyInfo();
			pi.setName("uid");
			pi.setValue(uname);
			pi.setType(String.class);
			request.addProperty(pi);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			try {
				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				resp = resultsRequestSOAP
						.getPrimitivePropertyAsString("return");
				
				 Thread.sleep(100);
				 StringTokenizer st = new StringTokenizer(resp, "###");
					
					while (st.hasMoreElements()) {
						videoFeed = st.nextToken();
						allow = st.nextToken();
					}
			} catch (Exception e) {
				System.out.println("Error in reading device details data");
				e.printStackTrace();
			}
//			if (resp != null) {
//				System.out.println("Returning response for " + METHOD_NAME);
//				//response = true;
//			}
			return "";

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
		getMenuInflater().inflate(R.menu.web_control, menu);
		return true;
	}

}
