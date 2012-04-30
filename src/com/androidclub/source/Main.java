package com.androidclub.source;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Main extends ListActivity {

	String[] ids, titles, urls;
	DatabaseAdapter adapter;
	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lv = (ListView) findViewById(android.R.id.list);
		try {
			adapter = new DatabaseAdapter(this);
			adapter.open();
			ids = adapter.getIds();
			titles = adapter.getTitles();
			urls = adapter.getURLs();
			adapter.close();
			lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles));
			//setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feeds));
			registerForContextMenu(lv);
		} catch (SQLException e) {
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
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent("com.androidclub.source.RSSREADERACTIVITY");
		i.putExtra("url", urls[position]);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case R.id.feed_add:
				Intent i = new Intent("com.androidclub.source.ADDFEED");
				startActivity(i);
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//return super.onContextItemSelected(item);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
			case R.id.edit_item:
				Intent i = new Intent("com.androidclub.source.EDITFEED");
				i.putExtra("id", ids[info.position]);
				i.putExtra("title", titles[info.position]);
				i.putExtra("url", urls[info.position]);
				startActivity(i);
		        return true;
			case R.id.delete_item:
				adapter.open();
				adapter.deleteFeed(ids[info.position]);
				adapter.close();
		    default:
		        return super.onContextItemSelected(item);
		}
	}
	
}
