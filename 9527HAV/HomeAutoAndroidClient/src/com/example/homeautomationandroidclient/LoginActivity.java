package com.example.homeautomationandroidclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import MyHomeAutomationUserPack.HomeAutomationUser;
import MyHomeAutomationUserPack.ServerIp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private static final String NAMESPACE = "http://MyServerPack/";
	private static final String URL = "http://"
			+ com.example.homeautomationandroidclient.ServerIp.setting
			+ ":8080/HomeAutomationServer/HomeAutomationService?wsdl";;
	private static final String SOAP_ACTION = "HomeAutomationService";
	EditText editTextUname;
	EditText editTextPwd;
	Button buttonLogin;
	boolean userPresent;
	boolean flag = false;
	Handler h;
	String uname = " ";
	HomeAutomationUser fromClient;
	HomeAutomationUser fromServer;
	ServerIp sip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		System.out.println("Server Ip At Login: " + URL);
		editTextUname = (EditText) findViewById(R.id.editTextUname);
		editTextPwd = (EditText) findViewById(R.id.editTextPwd);
		buttonLogin = (Button) findViewById(R.id.buttonLogin);

		buttonLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				uname = editTextUname.getText().toString();
				checkUser user = new checkUser();
				user.execute("");

			}
		});

		h = new Handler(Looper.getMainLooper());
		h.post(new Runnable() {
			@Override
			public void run() {

				if (flag) {

					System.out.println("User Present in handler: "
							+ userPresent);
					if (userPresent) {
						Toast.makeText(getApplicationContext(),
								"Login successful", Toast.LENGTH_SHORT).show();
						System.out.println("User name=" + uname);
						if (userPresent) {
							Intent i = new Intent(getApplicationContext(),
									WebControlActivity.class);
							i.putExtra("UserName", uname);
							startActivity(i);
						}
					} else {
						Toast.makeText(getApplicationContext(), "Login Failed",
								Toast.LENGTH_SHORT).show();
					}

					h.removeCallbacks(this);
					return;
				}

				h.postAtTime(this, SystemClock.uptimeMillis());
			}

		});

	}

	public class checkUser extends AsyncTask<String, Void, String> {
		String resp;
		@Override
		protected String doInBackground(String... params) {
			flag = false;
			String METHOD_NAME = "AuthenticateAndroidUser";
			// String PARAMETER_NAME = "imgName";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			fromClient = new HomeAutomationUser();
			fromClient.UserName = editTextUname.getText().toString();
			fromClient.Password = editTextPwd.getText().toString();
			System.out.println("User name= " + fromClient.UserName);
			System.out.println("Pwd= " + fromClient.Password);
			PropertyInfo pi = new PropertyInfo();
			System.out.println("Pwd= " + fromClient.Password);
			pi.setName("fromClient");
			pi.setValue(objectToString(fromClient));
			pi.setType(String.class);
			request.addProperty(pi);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			try {
				androidHttpTransport.call(SOAP_ACTION, envelope);
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				resp = resultsRequestSOAP
						.getPrimitivePropertyAsString("return");
				if (resp.equals("notFound")) {
					// System.out.println("Inside If");
					userPresent = false;
				} else {
					fromServer = (HomeAutomationUser) stringToObject(resp);
					userPresent = true;
					System.out.println("User Present :" + userPresent);
					System.out.println("Inside else");
					flag = true;
				}
			} catch (Exception e) {
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
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
