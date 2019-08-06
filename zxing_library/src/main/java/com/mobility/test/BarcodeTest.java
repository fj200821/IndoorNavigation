package com.mobility.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.client.android.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BarcodeTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);

		Button b = (Button) findViewById(R.id.startBarcode);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IntentIntegrator integrator = new IntentIntegrator(BarcodeTest.this);
				integrator.initiateScan();
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			Toast.makeText(this, "Barcode scanned :: "+scanResult.getContents(), Toast.LENGTH_LONG).show();
		}
	}
}
