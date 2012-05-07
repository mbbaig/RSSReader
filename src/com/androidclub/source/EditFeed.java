package com.androidclub.source;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditFeed extends Activity implements OnClickListener{

	Button update, cancel;
	EditText feedTitle, feedURL;
	String title, url;
	long id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editfeed);
		if (savedInstanceState == null){
        	Bundle extras = getIntent().getExtras();
        	if (extras == null){
        		id = 0;
        		title = null;
        		url = null;
        	}
        	else{
        		id = Long.parseLong(extras.getString("id"));
        		title = extras.getString("title");
        		url = extras.getString("url");
        	}
        }
        else{
        	id = Long.parseLong((String) savedInstanceState.getSerializable("id"));
        	title = (String) savedInstanceState.getSerializable("title");
        	url = (String) savedInstanceState.getSerializable("url");
        }
		update = (Button) findViewById(R.id.btnUpdate);
		cancel = (Button) findViewById(R.id.btnCancelEdit);
		feedTitle = (EditText) findViewById(R.id.etTitleEdit);
		feedURL = (EditText) findViewById(R.id.etURLEdit);
		
		feedTitle.setText(title);
		feedURL.setText(url);
		
		update.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnUpdate:
			try {
				String title = feedTitle.getText().toString();
				String url = feedURL.getText().toString();
				if (url.contains("http://") || url.contains("https://")){
					DatabaseAdapter feed = new DatabaseAdapter(this);
					feed.open();
					feed.editDatabase(id, title, url);
					feed.close();
					finish();
				}
				else{
					Dialog d = new Dialog(this);
					d.setTitle("Error");
					TextView tv = new TextView(this);
					tv.setText("Please provide a host protocol in the url\n" +
							"For Example:\n" +
							"-http://\n" +
							"-https://");
					d.setContentView(tv);
					d.show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Dialog d = new Dialog(this);
				d.setTitle("Error");
				TextView tv = new TextView(this);
				tv.setText(e.toString());
				d.setContentView(tv);
				d.show();
			}
			break;
		case R.id.btnCancelEdit:
			finish();
			break;
		}
	}
	
}
