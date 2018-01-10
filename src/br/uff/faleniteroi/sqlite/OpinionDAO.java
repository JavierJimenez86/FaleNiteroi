package br.uff.faleniteroi.sqlite;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import br.uff.faleniteroi.entity.Opinion;

public class OpinionDAO {
	
	public static final String TABLE_NAME = "OpinionTable";
	public static final String COLUMN_NAME_ID = "ID";
	public static final String COLUMN_NAME_COMMENT = "comment";
	public static final String COLUMN_NAME_TYPE = "type";
	public static final String COLUMN_NAME_SERVICE = "service";
	public static final String COLUMN_NAME_DATE = "date";
	public static final String COLUMN_NAME_PROTOCOL = "protocol";
	public static final String COLUMN_NAME_RESPONSE = "response";
	public static final String COLUMN_NAME_FINISHED = "finished";
	
	private DBHelper dbHelper;
	private SQLiteDatabase opinionDB;
	
	public OpinionDAO(Context context)
	{
		this.dbHelper = new DBHelper(context);
		this.opinionDB = dbHelper.getWritableDatabase();
	}
	
	public void insertOpinion(Opinion opinion){

		ContentValues cv = new ContentValues();
		
		cv.put(COLUMN_NAME_COMMENT, opinion.getComment());
		cv.put(COLUMN_NAME_TYPE, opinion.getType());
		cv.put(COLUMN_NAME_SERVICE, opinion.getService());
		cv.put(COLUMN_NAME_DATE, opinion.getDateEpoch());
		cv.put(COLUMN_NAME_PROTOCOL, opinion.getProtocol());
		cv.put(COLUMN_NAME_RESPONSE, opinion.getResponse());
		cv.put(COLUMN_NAME_FINISHED, opinion.isFinished());
		
		opinionDB.insert(TABLE_NAME, null, cv);
	}
	
	public ArrayList<Opinion> listLast()
	{
		ArrayList<Opinion> result = new ArrayList<Opinion>();
		
		Cursor opinionCursor = this.opinionDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_COMMENT,
				COLUMN_NAME_TYPE,
				COLUMN_NAME_SERVICE,
				COLUMN_NAME_DATE,
				COLUMN_NAME_PROTOCOL,
				COLUMN_NAME_RESPONSE,
				COLUMN_NAME_FINISHED },
				null, null, null, null, COLUMN_NAME_DATE + " DESC", "10");
		
		opinionCursor.moveToFirst();
		
		if (!opinionCursor.isAfterLast()) {
			do {
				
				boolean finished = opinionCursor.getInt(6) == 0 ? false : true;
				
				result.add(new Opinion(
						opinionCursor.getString(0),
						opinionCursor.getInt(1), 
						opinionCursor.getString(2), 
						new Date(opinionCursor.getLong(3) * 1000),
						opinionCursor.getString(4), 
						opinionCursor.getString(5), 
						finished
					));

			} while (opinionCursor.moveToNext());

			opinionCursor.close();
		}
		
		return result;
	}
	
	public ArrayList<Opinion> listCritics()
	{
		ArrayList<Opinion> result = new ArrayList<Opinion>();
		
		Cursor opinionCursor = this.opinionDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_COMMENT,
				COLUMN_NAME_TYPE,
				COLUMN_NAME_SERVICE,
				COLUMN_NAME_DATE,
				COLUMN_NAME_PROTOCOL,
				COLUMN_NAME_RESPONSE,
				COLUMN_NAME_FINISHED },
				COLUMN_NAME_TYPE + "=?", new String[]{"0"}, null, null, COLUMN_NAME_DATE + " DESC");
		
		opinionCursor.moveToFirst();
		
		if (!opinionCursor.isAfterLast()) {
			do {
				
				boolean finished = opinionCursor.getInt(6) == 0 ? false : true;
				
				result.add(new Opinion(
						opinionCursor.getString(0),
						opinionCursor.getInt(1), 
						opinionCursor.getString(2), 
						new Date(opinionCursor.getLong(3) * 1000),
						opinionCursor.getString(4), 
						opinionCursor.getString(5), 
						finished
					));

			} while (opinionCursor.moveToNext());

			opinionCursor.close();
		}
		
		return result;
	}
	
	public ArrayList<Opinion> listSuggestions()
	{
		ArrayList<Opinion> result = new ArrayList<Opinion>();
		
		Cursor opinionCursor = this.opinionDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_COMMENT,
				COLUMN_NAME_TYPE,
				COLUMN_NAME_SERVICE,
				COLUMN_NAME_DATE,
				COLUMN_NAME_PROTOCOL,
				COLUMN_NAME_RESPONSE,
				COLUMN_NAME_FINISHED },
				COLUMN_NAME_TYPE + "=?", new String[]{"1"}, null, null, COLUMN_NAME_DATE + " DESC");
		
		opinionCursor.moveToFirst();
		
		if (!opinionCursor.isAfterLast()) {
			do {
				
				boolean finished = opinionCursor.getInt(6) == 0 ? false : true;
				
				result.add(new Opinion(
						opinionCursor.getString(0),
						opinionCursor.getInt(1), 
						opinionCursor.getString(2), 
						new Date(opinionCursor.getLong(3) * 1000),
						opinionCursor.getString(4), 
						opinionCursor.getString(5), 
						finished
					));

			} while (opinionCursor.moveToNext());

			opinionCursor.close();
		}
		
		return result;
	}
	
	public ArrayList<Opinion> listSearched(String query)
	{
		ArrayList<Opinion> result = new ArrayList<Opinion>();
		
		Cursor opinionCursor = this.opinionDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_COMMENT,
				COLUMN_NAME_TYPE,
				COLUMN_NAME_SERVICE,
				COLUMN_NAME_DATE,
				COLUMN_NAME_PROTOCOL,
				COLUMN_NAME_RESPONSE,
				COLUMN_NAME_FINISHED},
				COLUMN_NAME_COMMENT + " LIKE ? OR "
				+ COLUMN_NAME_PROTOCOL + " LIKE ? ", 
				new String[]{"%"+query+"%", "%"+query+"%"}, null, null, COLUMN_NAME_DATE + " DESC");
		
		opinionCursor.moveToFirst();
		
		if (!opinionCursor.isAfterLast()) {
			do {
				
				boolean finished = opinionCursor.getInt(6) == 0 ? false : true;
				
				result.add(new Opinion(
						opinionCursor.getString(0),
						opinionCursor.getInt(1), 
						opinionCursor.getString(2), 
						new Date(opinionCursor.getLong(3) * 1000),
						opinionCursor.getString(4), 
						opinionCursor.getString(5), 
						finished
					));

			} while (opinionCursor.moveToNext());

			opinionCursor.close();
		}
		
		return result;
	}
	
	public long getNumOpinions(){
		return DatabaseUtils.queryNumEntries(opinionDB, TABLE_NAME);
	}
	
}
