package test.betsson.flickrsearch.provider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

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
        String[] available = {ImageSearchResultTable.COLUMN_IMAGE_URL,
                ImageSearchResultTable.COLUMN_IMAGE_TITLE, ImageSearchResultTable.COLUMN_ID};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }

    }

    /**
     *
     * @param cursor
     * @return null if cursor is null, bad data or the TransactionHistoryItem
     */

    // public static TransactionHistoryItem getTransactionHistoryItemFromCursor(Cursor cursor, Context context) {
    // TransactionHistoryItem transactionHistoryItem = null;
    // try {
    // Contact contact = new Contact();
    // String transferAmount, date, transferCharge;
    // int optionId;
    // String statusType = cursor.getString(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_TRANSFER_STATUS_TYPE));
    // contact.setName(cursor.getString(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_CONTACT_NAME)));
    // contact.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_CONTACT_ID))));
    // contact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_CONTACT_NUMBER)));
    // transferAmount = cursor.getString(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_IMAGE_TITLE));
    // date = cursor.getString(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_FORMATTED_TRANSFER_TIME));
    // optionId = cursor.getInt(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_PRODUCT_ID));
    // transferCharge = cursor.getString(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_FORMATTED_TRANSFER_CHARGE));
    // String statusId = cursor.getString(cursor.getColumnIndex(ImageSearchResultTable.COLUMN_TRANSFER_STATUS_ID));
    // Drawable drawable;
    // if (AirtimeStatus.isSuccess(statusType)) {
    // drawable = context.getResources().getDrawable(R.drawable.icon_status_successful_large);
    // } else if (AirtimeStatus.isPending(statusType)) {
    // drawable = context.getResources().getDrawable(R.drawable.icon_status_pending_large);
    // } else {
    // drawable = context.getResources().getDrawable(R.drawable.icon_status_failure_large);
    // }
    // transactionHistoryItem = new TransactionHistoryItem(contact, date, transferAmount, transferCharge, drawable, statusType, statusId, optionId);
    // } catch (Exception ignored) {
    // Log.e(TAG, ignored.toString());
    // }
    // return transactionHistoryItem;
    // }
}