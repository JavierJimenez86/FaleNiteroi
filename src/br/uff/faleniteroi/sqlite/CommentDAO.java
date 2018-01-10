package br.uff.faleniteroi.sqlite;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.uff.faleniteroi.entity.Comment;

public class CommentDAO {
	
	public static final String TABLE_NAME = "CommentTable";
	public static final String COLUMN_NAME_ID = "ID";
	public static final String COLUMN_NAME_COMMENT = "comment";
	public static final String COLUMN_NAME_USER_NAME = "userName";
	public static final String COLUMN_NAME_REQUEST_ID = "requestId";
	public static final String COLUMN_NAME_DATE = "date";
	
	private DBHelper dbHelper;
	private SQLiteDatabase commentDB;
	
	public CommentDAO(Context context)
	{
		this.dbHelper = new DBHelper(context);
		this.commentDB = dbHelper.getWritableDatabase();
	}
	
	public void insertCommnet(Comment comment){

		ContentValues cv = new ContentValues();
		
		cv.put(COLUMN_NAME_COMMENT, comment.getComment());
		cv.put(COLUMN_NAME_USER_NAME, comment.getUserName());
		cv.put(COLUMN_NAME_REQUEST_ID, comment.getRequestId());
		cv.put(COLUMN_NAME_DATE, comment.getDateEpoch());
		
		commentDB.insert(TABLE_NAME, null, cv);
	}
	
	public ArrayList<Comment>  listByRequestId(int requestId) {

		ArrayList<Comment> result = new ArrayList<Comment>();
	
		Cursor requestCursor = this.commentDB.query(TABLE_NAME, new String[] {
				COLUMN_NAME_COMMENT,
				COLUMN_NAME_USER_NAME,
				COLUMN_NAME_REQUEST_ID,
				COLUMN_NAME_DATE},
				COLUMN_NAME_REQUEST_ID + "=?", new String[]{String.valueOf(requestId)}, null, null, COLUMN_NAME_DATE + " DESC");

		requestCursor.moveToFirst();
		
		if (!requestCursor.isAfterLast()) {
			do {
				
				result.add(new Comment(requestCursor.getString(0), 
						requestCursor.getString(1), 
						requestCursor.getInt(2), 
						new Date(requestCursor.getLong(3) * 1000)
					));

			} while (requestCursor.moveToNext());

			requestCursor.close();
		}
		
		return result;
	}
	
}
