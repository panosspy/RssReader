package com.example.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SmallView extends Activity{
	
	//dilono kapoies metablites pou tha xreiastoun parakato
	TextView title;
	TextView description;
	ImageView image;
	String sT,sD,sI,cleanUp;
	URL imgurl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_smallview);
		//dilono enan tipo dikis mou sinartisis
		Imagereader task = new Imagereader();
		//ftiaxno ena intent
		Intent i = this.getIntent();
		//perno tis times apo proigoumeno intent
		sT =(String)i.getExtras().get("Title");
		sD =(String)i.getExtras().getString("Desc");
		//ftiaxno ta view mou
		title =(TextView)this.findViewById(R.id.textViewsmall1);
		image=(ImageView)this.findViewById(R.id.imageView1);
		description =(TextView)this.findViewById(R.id.textViewsmall2);
		
		//elenxo ean sto descriptio pou exo diabasei iparxei eikona
		if(sD.startsWith("<")==false){
			//ean den eiparxei eikona tote apla emfanizo sto view mou ti title kai to description
			title.setText(sT);
			description.setText(sD);
		}
		else{
			//ean iparxei xorizo to descritpion string apo tin eikona
			cleanUp = sD.substring(0, sD.indexOf(">")+1);
			//brisko pou akrivos briskete to url tis eikonas
			sD = sD.substring(sD.indexOf("src=") + 5);
			//xorizo to url apo to string
			int indexOf = sD.indexOf("'");
			if (indexOf==-1){
				indexOf = sD.indexOf("\"");
			}
			sD = sD.substring(0, indexOf);
			sI=sD;
			//ksanaperno olokliro to description apo to intent
			sD =(String)i.getExtras().getString("Desc");
           //xorizo tora to descriptio kai kratao to description xoris tin eikona
			sD = sD.substring(cleanUp.length(),sD.length() );
           
			try {
				//kano apo string se url tin eikona
				imgurl = new URL(sI);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//kalo tin sinartisi mou me to url tis eikonas
			task.execute(imgurl);
		}
	}
	//i sinartisi mou pou kanei extend tin asynctask pernodei url kai epistrefi bitmap
	public class Imagereader extends AsyncTask<URL, Void, Bitmap>{

		@Override
		protected Bitmap doInBackground(URL... params) {
			// TODO Auto-generated method stub
			  try {
				//ftiaxno tin bitmap kai tis dino tin timi pou xreiazete
				Bitmap bitmap = BitmapFactory.decodeStream((InputStream)params[0].getContent());
				//epistrefo to bitmap
				return bitmap;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//ftiaxno ta view mou pou tha emfanizode sto UI
			title.setText(sT);
			image.setImageBitmap(result);
			description.setText(sD);
		}
		
		
		
	}

}
