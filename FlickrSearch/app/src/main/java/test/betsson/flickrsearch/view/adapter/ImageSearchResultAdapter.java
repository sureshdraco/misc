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

import com.rebtel.rapi.model.AirtimeStatus;
import com.rebtel.sendly.R;
import com.rebtel.sendly.database.TransactionHistoryTable;
import com.rebtel.sendly.providers.TransactionHistoryContentProvider;
import com.rebtel.sendly.util.Constant;
import com.rebtel.sendly.view.model.TransactionHistoryItem;
import com.rebtel.sendly.viewmodels.TransactionHistoryItemClickCallback;

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
			convertView = layoutInflater.inflate(R.layout.transaction_history_list_item, null);
			viewHolder = new ViewHolder();

			viewHolder.name = (TextView) convertView.findViewById(R.id.contactName);
			viewHolder.transferStatus = (ImageView) convertView.findViewById(R.id.transactionStatus);
			viewHolder.date = (TextView) convertView.findViewById(R.id.date);
			viewHolder.transferAmount = (TextView) convertView.findViewById(R.id.transferAmount);
			viewHolder.transferCharge = (TextView) convertView.findViewById(R.id.trasnferCharge);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			// remove tasks related to the recycled list item
		}

		getCursor().moveToPosition(position);
		final Cursor cursor = getCursor();
		TransactionHistoryItem transactionHistoryItem = TransactionHistoryContentProvider.getTransactionHistoryItemFromCursor(getCursor(), context);
		if (transactionHistoryItem != null) {
			convertView.setVisibility(View.VISIBLE);
			setBackground(convertView, transactionHistoryItem);
			fillData(viewHolder, transactionHistoryItem);
			final String transferId = cursor.getString(cursor.getColumnIndex(TransactionHistoryTable.COLUMN_TRANSACTION_ID));
			convertView.setOnClickListener(new TransactionHistoryItemClick(transferId));
		} else {
			convertView.setVisibility(View.GONE);
		}

		return convertView;
	}

	private void setBackground(View convertView, TransactionHistoryItem transactionHistoryItem) {
		if (AirtimeStatus.isPending(transactionHistoryItem.getTransferStatusType())) {
			convertView.setBackgroundResource(R.drawable.selector_transaction_status_pending);
		} else if (AirtimeStatus.isSuccess(transactionHistoryItem.getTransferStatusType())) {
			convertView.setBackgroundResource(R.drawable.selector_transaction_status_success);
		} else {
			convertView.setBackgroundResource(R.drawable.selector_transaction_status_fail);
		}
	}

	private void fillData(ViewHolder viewHolder, TransactionHistoryItem transactionHistoryItem) {
		if (TextUtils.isEmpty(transactionHistoryItem.getContact().getName())) {
			viewHolder.name.setText(transactionHistoryItem.getContact().getPhoneNumber());
		} else {
			viewHolder.name.setText(transactionHistoryItem.getContact().getName());
		}
		viewHolder.transferAmount.setText(transactionHistoryItem.getTransferAmount());
		viewHolder.date.setText(transactionHistoryItem.getDate());
		viewHolder.transferCharge.setText(transactionHistoryItem.getTransferCharge());
		viewHolder.transferStatus.setImageDrawable(transactionHistoryItem.getTransferStatusDrawable());
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	}

	static class ViewHolder {
		private TextView name, date, transferAmount, transferCharge;
		private ImageView transferStatus;
	}

	class TransactionHistoryItemClick implements View.OnClickListener {
		private final String TAG = TransactionHistoryItemClick.class.getSimpleName();
		private String transferId;

		public TransactionHistoryItemClick(String transferId) {
			this.transferId = transferId;
		}

		@Override
		public void onClick(View view) {
			if (Constant.DEBUG) Log.d(TAG, "onClick(): " + transferId);
			transactionHistoryItemClickCallback.onRowClicked(transferId);
		}
	}
}
