package com.example.homeautomationandroidclient;

import MyHomeAutomationUserPack.ServerIp;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class BannerActivity extends Activity {

	ImageView imageViewNext;
	EditText editTextServerIp;
	Button btnProceed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_banner);
		editTextServerIp = (EditText) findViewById(R.id.editTextServerIp);
		imageViewNext = (ImageView) findViewById(R.id.imageViewNext);
		btnProceed = (Button) findViewById(R.id.buttonProceed);
		btnProceed.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				com.example.homeautomationandroidclient.ServerIp.setting = editTextServerIp
						.getText().toString();
				System.out
						.println("Server Ip: "
								+ com.example.homeautomationandroidclient.ServerIp.setting);
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.banner, menu);
		return true;
	}

}
