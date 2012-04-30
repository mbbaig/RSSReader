package com.androidclub.source;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter {
	
	public static final String KEY_ID = "_id";
	public static final String KEY_TITLE = "feed_title";
	public static final String KEY_URL = "feed_url";
	
	private static final String DATABASE_NAME = "dbFeeds";
	private static final String DATABASE_TABLE = "feeds_table";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper helper;
	private final Context daContext;
	private SQLiteDatabase daDatabase;
	String[] columns = {KEY_ID, KEY_TITLE, KEY_URL};
	
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(
					"CREATE TABLE " + DATABASE_TABLE + " (" + 
					KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_TITLE + " TEXT NOT NULL, " +
					KEY_URL + " TEXT NOT NULL);"
			);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
			onCreate(db);
		}
		
	}
	
	public DatabaseAdapter(Context c){
		daContext = c;
	}
	
	public DatabaseAdapter open() throws SQLException{
		helper = new DbHelper(daContext);
		daDatabase = helper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		helper.close();
	}

	public long updateDatabase(String title, String url) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_URL, url);
		return daDatabase.insert(DATABASE_TABLE, null, cv);
	}


	public String[] getIds() {
		// TODO Auto-generated method stub
		Cursor c = daDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String[] ids = new String[c.getCount()];
		
		int id = c.getColumnIndex(KEY_ID);
		int i = 0;
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			ids[i] = c.getString(id);
			i++;
		}
		return ids;
	}
	
	public String[] getTitles() {
		// TODO Auto-generated method stub
		Cursor c = daDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String[] feeds = new String[c.getCount()];
		
		int title = c.getColumnIndex(KEY_TITLE);
		int i = 0;
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			feeds[i] = c.getString(title);
			i++;
		}
		return feeds;
	}

	public String[] getURLs() {
		// TODO Auto-generated method stub
		Cursor c = daDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String[] urls = new String[c.getCount()];
		
		int url = c.getColumnIndex(KEY_URL);
		int i = 0;
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			urls[i] = c.getString(url);
			i++;
		}
		return urls;
	}

	public void editDatabase(long id, String title, String url) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_ID, id);
		cv.put(KEY_TITLE, title);
		cv.put(KEY_URL, url);
		daDatabase.update(DATABASE_TABLE, cv, KEY_ID + "=" + id, null);
	}

	public void deleteFeed(String id) {
		// TODO Auto-generated method stub
		long rowid = Long.parseLong(id);
		daDatabase.delete(DATABASE_TABLE, KEY_ID + "=" + rowid, null);
	}

}
