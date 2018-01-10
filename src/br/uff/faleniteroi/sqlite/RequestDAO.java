package br.uff.faleniteroi.sqlite;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import br.uff.faleniteroi.entity.Request;

public class RequestDAO {
	
	public static final String TABLE_NAME = "RequestTable";
	public static final String COLUMN_NAME_ID = "ID";
	public static final String COLUMN_NAME_ADDRESS = "address";
	public static final String COLUMN_NAME_PHOTO = "photo";
	public static final String COLUMN_NAME_DESCRIPTION = "description";
	public static final String COLUMN_NAME_DATE = "date";
	public static final String COLUMN_NAME_SERVICE = "service";
	public static final String COLUMN_NAME_LATITUDE = "latitude";
	public static final String COLUMN_NAME_LONGITUDE = "longitude";
	public static final String COLUMN_NAME_FINISHED = "finished";
	public static final String COLUMN_NAME_PROTOCOL = "protocol";
	public static final String COLUMN_NAME_RESPONSE = "response";
	public static final String COLUMN_NAME_LIKE = "like";
	public static final String COLUMN_NAME_UNLIKE = "unlike";
	
	private DBHelper dbHelper;
	private SQLiteDatabase requestDB;
	
	public RequestDAO(Context context)
	{
		this.dbHelper = new DBHelper(context);
		this.requestDB = dbHelper.getWritableDatabase();
	}
	
	public void createRequest(Request request){

		ContentValues cv = new ContentValues();
		
		cv.put(COLUMN_NAME_ADDRESS, request.getAddress());
		cv.put(COLUMN_NAME_PHOTO, request.getPhoto());
		cv.put(COLUMN_NAME_DESCRIPTION, request.getDescription());
		cv.put(COLUMN_NAME_DATE, request.getDateEpoch());
		cv.put(COLUMN_NAME_SERVICE, request.getService());
		cv.put(COLUMN_NAME_LATITUDE, request.getLatitude());
		cv.put(COLUMN_NAME_LONGITUDE, request.getLongitude());
		cv.put(COLUMN_NAME_FINISHED, request.isFinished());
		cv.put(COLUMN_NAME_PROTOCOL, request.getProtocol());
		cv.put(COLUMN_NAME_RESPONSE, request.getResponse());

		requestDB.insert(TABLE_NAME, null, cv);
	}
	
	public void updateRequest(Request request) {
		
		ContentValues cv = new ContentValues();

		cv.put(COLUMN_NAME_LIKE, request.getLike());
		cv.put(COLUMN_NAME_UNLIKE, request.getUnlike());

		String requestId = String.valueOf(request.getRequestId());
		requestDB.update(TABLE_NAME, cv, COLUMN_NAME_ID + "=?", new String[] {requestId});
	}
	
	public ArrayList<Request> listLast()
	{
		ArrayList<Request> result = new ArrayList<Request>();
		
		Cursor requestCursor = this.requestDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_ADDRESS,
				COLUMN_NAME_PHOTO,
				COLUMN_NAME_DESCRIPTION,
				COLUMN_NAME_DATE,
				COLUMN_NAME_SERVICE,
				COLUMN_NAME_LATITUDE,
				COLUMN_NAME_LONGITUDE,
				COLUMN_NAME_FINISHED,
				COLUMN_NAME_PROTOCOL,
				COLUMN_NAME_RESPONSE,
				COLUMN_NAME_LIKE,
				COLUMN_NAME_UNLIKE,
				COLUMN_NAME_ID},
				null, null, null, null, COLUMN_NAME_DATE + " DESC", "10");
		
		requestCursor.moveToFirst();
		
		if (!requestCursor.isAfterLast()) {
			do {
				
				boolean finished = requestCursor.getInt(7) == 0 ? false : true;
				
				result.add(new Request(requestCursor.getString(4), 
						requestCursor.getString(0), 
						requestCursor.getString(1), 
						requestCursor.getString(2), 
						new Date(requestCursor.getLong(3) * 1000), 
						requestCursor.getDouble(5), 
						requestCursor.getDouble(6),
						finished,
						requestCursor.getString(8), 
						requestCursor.getString(9),
						requestCursor.getInt(10),
						requestCursor.getInt(11),
						requestCursor.getInt(12)
					));

			} while (requestCursor.moveToNext());

			requestCursor.close();
		}
		
		return result;
	}
	
	public ArrayList<Request> listUnderAnalysis()
	{
		ArrayList<Request> result = new ArrayList<Request>();
		
		Cursor requestCursor = this.requestDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_ADDRESS,
				COLUMN_NAME_PHOTO,
				COLUMN_NAME_DESCRIPTION,
				COLUMN_NAME_DATE,
				COLUMN_NAME_SERVICE,
				COLUMN_NAME_LATITUDE,
				COLUMN_NAME_LONGITUDE,
				COLUMN_NAME_FINISHED,
				COLUMN_NAME_PROTOCOL,
				COLUMN_NAME_RESPONSE,
				COLUMN_NAME_LIKE,
				COLUMN_NAME_UNLIKE,
				COLUMN_NAME_ID},
				COLUMN_NAME_FINISHED + "=?", new String[]{"0"}, null, null, COLUMN_NAME_DATE + " DESC");
		
		requestCursor.moveToFirst();
		
		if (!requestCursor.isAfterLast()) {
			do {
				
				boolean finished = requestCursor.getInt(7) == 0 ? false : true;
				
				result.add(new Request(requestCursor.getString(4), 
						requestCursor.getString(0), 
						requestCursor.getString(1), 
						requestCursor.getString(2), 
						new Date(requestCursor.getLong(3) * 1000), 
						requestCursor.getDouble(5), 
						requestCursor.getDouble(6),
						finished,
						requestCursor.getString(8), 
						requestCursor.getString(9),
						requestCursor.getInt(10),
						requestCursor.getInt(11),
						requestCursor.getInt(12)
					));

			} while (requestCursor.moveToNext());

			requestCursor.close();
		}
		
		return result;
	}
	
	public ArrayList<Request> listFinished()
	{
		ArrayList<Request> result = new ArrayList<Request>();
		
		Cursor requestCursor = this.requestDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_ADDRESS,
				COLUMN_NAME_PHOTO,
				COLUMN_NAME_DESCRIPTION,
				COLUMN_NAME_DATE,
				COLUMN_NAME_SERVICE,
				COLUMN_NAME_LATITUDE,
				COLUMN_NAME_LONGITUDE,
				COLUMN_NAME_FINISHED,
				COLUMN_NAME_PROTOCOL,
				COLUMN_NAME_RESPONSE,
				COLUMN_NAME_LIKE,
				COLUMN_NAME_UNLIKE,
				COLUMN_NAME_ID},
				COLUMN_NAME_FINISHED + "=?", new String[]{"1"}, null, null, COLUMN_NAME_DATE + " DESC");
		
		requestCursor.moveToFirst();
		
		if (!requestCursor.isAfterLast()) {
			do {
				
				boolean finished = requestCursor.getInt(7) == 0 ? false : true;
				
				result.add(new Request(requestCursor.getString(4), 
						requestCursor.getString(0), 
						requestCursor.getString(1), 
						requestCursor.getString(2), 
						new Date(requestCursor.getLong(3) * 1000), 
						requestCursor.getDouble(5), 
						requestCursor.getDouble(6),
						finished,
						requestCursor.getString(8), 
						requestCursor.getString(9),
						requestCursor.getInt(10),
						requestCursor.getInt(11),
						requestCursor.getInt(12)
					));

			} while (requestCursor.moveToNext());

			requestCursor.close();
		}
		
		return result;
	}
	
	public ArrayList<Request> listSearched(String query)
	{
		ArrayList<Request> result = new ArrayList<Request>();
		
		Cursor requestCursor = this.requestDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_ADDRESS,
				COLUMN_NAME_PHOTO,
				COLUMN_NAME_DESCRIPTION,
				COLUMN_NAME_DATE,
				COLUMN_NAME_SERVICE,
				COLUMN_NAME_LATITUDE,
				COLUMN_NAME_LONGITUDE,
				COLUMN_NAME_FINISHED,
				COLUMN_NAME_PROTOCOL,
				COLUMN_NAME_RESPONSE,
				COLUMN_NAME_LIKE,
				COLUMN_NAME_UNLIKE,
				COLUMN_NAME_ID},
				COLUMN_NAME_DESCRIPTION + " LIKE ? OR "
				+ COLUMN_NAME_ADDRESS + " LIKE ? OR "
				+ COLUMN_NAME_PROTOCOL + " LIKE ? ", 
				new String[]{"%"+query+"%", "%"+query+"%", "%"+query+"%"}, null, null, COLUMN_NAME_DATE + " DESC");
		
		requestCursor.moveToFirst();
		
		if (!requestCursor.isAfterLast()) {
			do {
				
				boolean finished = requestCursor.getInt(7) == 0 ? false : true;
				
				result.add(new Request(requestCursor.getString(4), 
						requestCursor.getString(0), 
						requestCursor.getString(1), 
						requestCursor.getString(2), 
						new Date(requestCursor.getLong(3) * 1000), 
						requestCursor.getDouble(5), 
						requestCursor.getDouble(6),
						finished,
						requestCursor.getString(8), 
						requestCursor.getString(9),
						requestCursor.getInt(10),
						requestCursor.getInt(11),
						requestCursor.getInt(12)
					));

			} while (requestCursor.moveToNext());

			requestCursor.close();
		}
		
		return result;
	}
	
	public ArrayList<Request> listLastWithLatLon()
	{
		ArrayList<Request> result = new ArrayList<Request>();
		
		Cursor requestCursor = this.requestDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_ADDRESS,
				COLUMN_NAME_PHOTO,
				COLUMN_NAME_DESCRIPTION,
				COLUMN_NAME_DATE,
				COLUMN_NAME_SERVICE,
				COLUMN_NAME_LATITUDE,
				COLUMN_NAME_LONGITUDE,
				COLUMN_NAME_FINISHED,
				COLUMN_NAME_PROTOCOL,
				COLUMN_NAME_RESPONSE,
				COLUMN_NAME_LIKE,
				COLUMN_NAME_UNLIKE,
				COLUMN_NAME_ID},
				COLUMN_NAME_LATITUDE + " !=? and " 
				+ COLUMN_NAME_LONGITUDE + " !=? ", new String[]{"0","0"}, null, null, COLUMN_NAME_DATE + " DESC", "50");
		
		requestCursor.moveToFirst();
		
		if (!requestCursor.isAfterLast()) {
			do {
				
				boolean finished = requestCursor.getInt(7) == 0 ? false : true;
				
				result.add(new Request(requestCursor.getString(4), 
						requestCursor.getString(0), 
						requestCursor.getString(1), 
						requestCursor.getString(2), 
						new Date(requestCursor.getLong(3) * 1000), 
						requestCursor.getDouble(5), 
						requestCursor.getDouble(6),
						finished,
						requestCursor.getString(8), 
						requestCursor.getString(9),
						requestCursor.getInt(10),
						requestCursor.getInt(11),
						requestCursor.getInt(12)
					));

			} while (requestCursor.moveToNext());

			requestCursor.close();
		}
		
		return result;
	}
	
	public long getNumRequests(){
		return DatabaseUtils.queryNumEntries(requestDB, TABLE_NAME);
	}
}
