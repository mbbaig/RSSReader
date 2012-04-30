package com.androidclub.source;

import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class RssReaderActivity extends ListActivity {
    /** Called when the activity is first created. */
	
	//private String FEED_URI = "http://news.google.ca/news?pz=1&cf=all&ned=ca&hl=en&output=rss";
	private String FEED_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedlist);
        if (savedInstanceState == null){
        	Bundle extras = getIntent().getExtras();
        	if (extras == null){
        		FEED_URI = null;
        	}
        	else{
        		FEED_URI = extras.getString("url");
        	}
        }
        else{
        	FEED_URI = (String) savedInstanceState.getSerializable("url");
        }
        
        try {
			getListView().setBackgroundColor(Color.WHITE);
			setListAdapter(Adapters.loadCursorAdapter(this, R.xml.rss_feed,
			        "content://xmldocument/?url=" + Uri.encode(FEED_URI)));
			getListView().setOnItemClickListener(new UrlIntentListener());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Dialog d = new Dialog(this);
			d.setTitle("Error");
			TextView tv = new TextView(this);
			tv.setText(e.toString());
			d.setContentView(tv);
			d.show();
		}
    }
}