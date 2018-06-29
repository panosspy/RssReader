package com.example.rssreader;

import java.net.URL;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	//dilono kapoies metablites pou tha mou xreiastoun poio kato
	EditText text;
	URL tourl;
	String s;
	Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//ftiaxno to layout mou me EditText kai Button
		text=(EditText)this.findViewById(R.id.editText1);
		b=(Button)findViewById(R.id.button1);
		//dilono sto button na min bori na gini click
		b.setEnabled(false);
		//ftiaxno enan listener sto EditText mou
		text.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				//sti methodo opou kalite molis allaksei to periexomeno tou EditText diabazo ti exei graftei kai to bazo se mia metabliti
				s = text.getText().toString();
				try {
					//metatrepo apo string se url oti mou exei dosi o xristis
					tourl=new URL(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("MainActivityUrl",e.getMessage());
				}
				//ean tora to url pou exei dosei einai sosto tote energopoiite to button
				b.setEnabled(URLUtil.isValidUrl(s));
				
			}
			
		});
		//ftiaxno enan listener gia to button
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ftiaxno ena intent
				Intent i=new Intent();
				//tou stelno to string pou exoume parei apo pano
				i.putExtra("tourl",s);
				//deixno poio tha einai to activity pou tha energopoiithi meta apo auto
				i.setAction("com.example.rssreader.lista");
				//energopoio to activity
				startActivity(i);				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
