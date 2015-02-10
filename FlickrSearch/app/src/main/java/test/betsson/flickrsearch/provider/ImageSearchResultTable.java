package test.betsson.flickrsearch.provider;

import android.database.sqlite.SQLiteDatabase;

public class ImageSearchResultTable {

	// Database table
	public static final String TABLE_IMAGE_SEARCH_RESULT = "imageSearchResult";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_IMAGE_URL = "image_url";
	public static final String COLUMN_IMAGE_TITLE = "image_title";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_IMAGE_SEARCH_RESULT
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_IMAGE_URL + " text not null, "
			+ COLUMN_IMAGE_TITLE + " text not null"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		// TODO
	}
}
