package com.androidclub.source;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddFeed extends Activity implements OnClickListener {

	Button add, cancel;
	EditText feedTitle, feedURL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfeed);

		add = (Button) findViewById(R.id.btnAdd);
		cancel = (Button) findViewById(R.id.btnCancel);
		feedTitle = (EditText) findViewById(R.id.etTitle);
		feedURL = (EditText) findViewById(R.id.etURL);

		add.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnAdd:
			try {
				String title = feedTitle.getText().toString();
				String url = feedURL.getText().toString();
				DatabaseAdapter feed = new DatabaseAdapter(AddFeed.this);
				feed.open();
				feed.updateDatabase(title, url);
				feed.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Dialog d = new Dialog(this);
				d.setTitle("Error");
				TextView tv = new TextView(this);
				tv.setText(e.toString());
				d.setContentView(tv);
				d.show();
			} finally {
				finish();
			}
			break;
		case R.id.btnCancel:
			finish();
			break;
		}
	}

}
