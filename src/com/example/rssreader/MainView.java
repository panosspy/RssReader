package com.example.rssreader;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainView extends Activity{

	//orizo metablites ena url kai mia mainview
	URL url;
	private MainView local;
	private ArrayList<String> als =new ArrayList<String>();
	private ArrayList<String> ald = new ArrayList<String>();
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainview);
		
		//bazo sti metabliti local na dixnei se auto to simio
		local=this;
		
		//dimiourgo mia metabliti task apo mia sunartisi pou douleuei se asynctask
		Readrsstask task = new Readrsstask();
		//ftiaxno to intent
		Intent i = this.getIntent();
		//diabazo apo to intent ena string me onoma tourl kai to bazo stin metabliti t2
		String s = i.getExtras().getString("tourl").toString();
		//kano ena try catch kai metatrepo to string pou peira prin se tipo url
		try {
			url= new URL(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("MainActivityURL1", e.getMessage());
		}
		
		//ksekinao to task me parametro to url pou eftiaksa poio pano
		task.execute(url);
		
	}
	
	//i sinartisi pou kanei extend tin asynctask tin kalo me ena url kai mou epistrefi ena ArrayList
	public class Readrsstask extends AsyncTask<URL, Void, ArrayList<RssItem>>{
		//ftiaxno ena ArrayList tipou string pou tha to xriasto poio kato
		
		
		//mesa sti sinartisi mou pou kanei extend tin asynctask exo tin doInBackground methodo i opoia trexei piso apo to UI
		@Override
		protected ArrayList<RssItem> doInBackground(URL... arg0) {
			try {
				//ftiaxno mia metabliti tipou RssFeed kai diabazo to url kai tou pernao tis times mesa apo tis klaseis pou einai idi ilopoiimenes
				RssFeed feed = RssReader.read(arg0[0]);
				//ftiaxno mia ArrayList tipou RRssItem kai tou pernao tis times apo tin proigoumeni metabliti feed
				ArrayList<RssItem> rssItems = feed.getRssItems();
				
				//epistrefo tin ArrayList
				return rssItems;
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Ean exei gini kapoio lathos i timi pou tha epistrepsi einai null
			return null;
		}
		
		@Override
		protected void onPostExecute(final ArrayList<RssItem> result) {		
			
			//apo tin ArrayList tipou string pou eixa ftiaksei tis bazo ta adikeimena apo tin ArrayList pou eixa diabazei apo to feed mesa se for kai tou dino mono tin timi title
			for(RssItem rssItem : result) {
			    als.add(rssItem.getTitle());
			}
			for(RssItem rssItem : result) {
			    ald.add(rssItem.getPubDate().toString());
			}
			//ftiaxno to ListView mou
			ListView Itemslistview = (ListView)findViewById(R.id.ListViewItems);
			//ftiaxno to adapter mou pou xreiazete stin listview kai tou dino tis parakato parametrous kai times
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(local,android.R.layout.simple_list_item_1,als);
			//energopoio to list view mou
			Itemslistview.setAdapter(new VersionAdapter(local));
			//bazo Listener sto list view
			Itemslistview.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					//ftiaxno neo intent
					Intent i1 = new Intent();
					//bazo sto intent tis times pou tha xreiasto sto epomeno activity mou
					i1.putExtra("Title", result.get(arg2).getTitle());
					i1.putExtra("Desc", result.get(arg2).getDescription());
					//tou dino apo pou tha einai to deutero activity
					i1.setClassName("com.example.rssreader", "com.example.rssreader.SmallView");
					//energopoio to activity me ta molis intent pou eftiaksa
					startActivity(i1);
				}
				
			});
			
		}	
	}
	class VersionAdapter extends BaseAdapter{
		
		private LayoutInflater layoutInflater;
		
		public VersionAdapter(MainView activity) {
			// TODO Auto-generated constructor stub
			layoutInflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return als.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View listItem = arg1;
			if (listItem == null) {
				listItem = layoutInflater.inflate(R.layout.list_medate, null);
			}

			// Initialize the views in the layout
			TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
			TextView tvDesc = (TextView) listItem.findViewById(R.id.desc);

			// set the views in the layout
			tvTitle.setText(als.get(arg0).toString());
			tvDesc.setText(ald.get(arg0).toString());

			return listItem;
		}
		
	}
}
