package br.uff.faleniteroi.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "FaleNiteroiDB_20.SQLite";
	public static final int DB_VERSION = 1;
	private Context context;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		//request
		String sqlStatement = "CREATE TABLE " + RequestDAO.TABLE_NAME
				+ " ("
				+ RequestDAO.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ RequestDAO.COLUMN_NAME_ADDRESS + " TEXT, "
				+ RequestDAO.COLUMN_NAME_PHOTO + " TEXT, "
				+ RequestDAO.COLUMN_NAME_DESCRIPTION + " TEXT, "
				+ RequestDAO.COLUMN_NAME_DATE + " INTEGER, "
				+ RequestDAO.COLUMN_NAME_SERVICE + " TEXT, "
				+ RequestDAO.COLUMN_NAME_LATITUDE + " REAL, "
				+ RequestDAO.COLUMN_NAME_LONGITUDE + " REAL, "
				+ RequestDAO.COLUMN_NAME_FINISHED + " INTEGER, "
				+ RequestDAO.COLUMN_NAME_PROTOCOL + " TEXT, "
				+ RequestDAO.COLUMN_NAME_RESPONSE + " TEXT, "
				+ RequestDAO.COLUMN_NAME_LIKE + " INTEGER, "
				+ RequestDAO.COLUMN_NAME_UNLIKE + " INTEGER "
				+ ");";

		db.execSQL(sqlStatement);
		
		//comment
		String sqlStatement2 = "CREATE TABLE " + CommentDAO.TABLE_NAME
				+ " ("
				+ CommentDAO.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ CommentDAO.COLUMN_NAME_COMMENT + " TEXT, "
				+ CommentDAO.COLUMN_NAME_USER_NAME + " TEXT, "
				+ CommentDAO.COLUMN_NAME_REQUEST_ID + " INTEGER, "
				+ CommentDAO.COLUMN_NAME_DATE + " INTEGER "
				+ ");";
		
		db.execSQL(sqlStatement2);
		
		//opinion
		String sqlStatement3 = "CREATE TABLE " + OpinionDAO.TABLE_NAME
				+ " ("
				+ OpinionDAO.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ OpinionDAO.COLUMN_NAME_TYPE + " INTEGER, "
				+ OpinionDAO.COLUMN_NAME_SERVICE + " TEXT, "
				+ OpinionDAO.COLUMN_NAME_DATE + " INTEGER, "
				+ OpinionDAO.COLUMN_NAME_PROTOCOL + " TEXT, "
				+ OpinionDAO.COLUMN_NAME_RESPONSE + " TEXT, "
				+ OpinionDAO.COLUMN_NAME_COMMENT + " TEXT, "
				+ OpinionDAO.COLUMN_NAME_FINISHED + " INTEGER "
				+ ");";
		
		db.execSQL(sqlStatement3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	public void deleteDatabase() {
		context.deleteDatabase(DB_NAME);
	}
}
