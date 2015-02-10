package test.betsson.flickrsearch.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import test.betsson.flickrsearch.BuildConfig;
import test.betsson.flickrsearch.view.adapter.model.ImageSearchResultItem;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class ImageSearchContentProvider extends ContentProvider {

	private static final String TAG = ImageSearchContentProvider.class.getSimpleName();
	// database
	private ImageSearchDatabaseHelper database;

	private static final String AUTHORITY = "test.betsson.flickrsearch";
	private static final String BASE_PATH = "search_result";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	public static final int ALL = 1;

	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, ALL);
	}

	@Override
	public boolean onCreate() {
		database = new ImageSearchDatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		sortOrder = ImageSearchResultTable.COLUMN_ID + " ASC";
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		Cursor cursor = null;
		// check if the caller has requested a column which does not exists
		checkColumns(projection);
		SQLiteDatabase db = database.getReadableDatabase();
		// Set the table
		queryBuilder.setTables(ImageSearchResultTable.TABLE_IMAGE_SEARCH_RESULT);
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case ALL:
			cursor = queryBuilder.query(db, projection, selection,
					selectionArgs, null, null, sortOrder);
			// make sure that potential listeners are getting notified
			cursor.setNotificationUri(getContext().getContentResolver(), CONTENT_URI);
			break;
		}
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		id = sqlDB.insert(ImageSearchResultTable.TABLE_IMAGE_SEARCH_RESULT, null, values);
		getContext().getContentResolver().notifyChange(CONTENT_URI, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		rowsDeleted = sqlDB.delete(ImageSearchResultTable.TABLE_IMAGE_SEARCH_RESULT, selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(CONTENT_URI, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		rowsUpdated = sqlDB.update(ImageSearchResultTable.TABLE_IMAGE_SEARCH_RESULT,
				values,
				selection,
				selectionArgs);
		getContext().getContentResolver().notifyChange(CONTENT_URI, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		String[] available = { ImageSearchResultTable.COLUMN_IMAGE_URL,
				ImageSearchResultTable.COLUMN_IMAGE_TITLE, ImageSearchResultTable.COLUMN_ID };
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
			// check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}

	public static void saveImageSearchResult(Context context, ArrayList<ImageSearchResultItem> imageSearchResultItems) {
		ArrayList<ContentValues> contentValuesList = new ArrayList<>();
		for (ImageSearchResultItem imageSearchResultItem : imageSearchResultItems) {
			contentValuesList.add(getContentValues(imageSearchResultItem));
		}
		for (ContentValues values : contentValuesList) {
			context.getContentResolver().insert(ImageSearchContentProvider.CONTENT_URI, values);
		}
		if (BuildConfig.DEBUG) Log.d(TAG, "Notification uri to result is sent");
		context.getContentResolver().notifyChange(ImageSearchContentProvider.CONTENT_URI, null);
	}

	private static ContentValues getContentValues(ImageSearchResultItem imageSearchResultItem) {
		ContentValues values = new ContentValues();
		values.put(ImageSearchResultTable.COLUMN_IMAGE_TITLE, imageSearchResultItem.getImageTitle());
		values.put(ImageSearchResultTable.COLUMN_IMAGE_URL, imageSearchResultItem.getImageUrl());
		return values;
	}

	public static ImageSearchResultItem getSearchResultItemFromCursor(Cursor cursor) {
		String imageTitle = cursor.getString(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_IMAGE_TITLE));
		String imageUrl = cursor.getString(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_IMAGE_URL));
		return new ImageSearchResultItem(imageUrl, imageTitle);
	}
}