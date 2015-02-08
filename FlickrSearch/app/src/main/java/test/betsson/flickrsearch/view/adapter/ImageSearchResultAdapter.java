package test.betsson.flickrsearch.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import test.betsson.flickrsearch.R;

public class ImageSearchResultAdapter extends CursorAdapter {

    private LayoutInflater layoutInflater;
    private final Context context;

    public ImageSearchResultAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup view) {
        return null;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
//            convertView = layoutInflater.inflate(R.layout.row, null);
//            viewHolder = new ViewHolder();
//
//            viewHolder.name = (TextView) convertView.findViewById(R.id.contactName);
//            viewHolder.transferStatus = (ImageView) convertView.findViewById(R.id.transactionStatus);
//            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
//            viewHolder.transferAmount = (TextView) convertView.findViewById(R.id.transferAmount);
//            viewHolder.transferCharge = (TextView) convertView.findViewById(R.id.trasnferCharge);
//            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            // remove tasks related to the recycled list item
        }

        getCursor().moveToPosition(position);
        final Cursor cursor = getCursor();
//        TransactionHistoryItem transactionHistoryItem = TransactionHistoryContentProvider.getTransactionHistoryItemFromCursor(getCursor(), context);
//        if (transactionHistoryItem != null) {
//            convertView.setVisibility(View.VISIBLE);
//            setBackground(convertView, transactionHistoryItem);
//            fillData(viewHolder, transactionHistoryItem);
//            final String transferId = cursor.getString(cursor.getColumnIndex(TransactionHistoryTable.COLUMN_TRANSACTION_ID));
//            convertView.setOnClickListener(new TransactionHistoryItemClick(transferId));
//        } else {
//            convertView.setVisibility(View.GONE);
//        }

        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
    }

    static class ViewHolder {
        private TextView name, date, transferAmount, transferCharge;
        private ImageView transferStatus;
    }
}
