package test.betsson.flickrsearch.view.adapter;

import test.betsson.flickrsearch.R;
import test.betsson.flickrsearch.network.volley.VolleyClient;
import test.betsson.flickrsearch.provider.ImageSearchContentProvider;
import test.betsson.flickrsearch.view.adapter.model.ImageSearchResultItem;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

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
			convertView = layoutInflater.inflate(R.layout.search_result_row, null);
			viewHolder = new ViewHolder();
			viewHolder.imageTitle = (TextView) convertView.findViewById(R.id.imageTitle);
			viewHolder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.image);
			viewHolder.networkImageView.setDefaultImageResId(R.drawable.broken_image);
			viewHolder.networkImageView.setErrorImageResId(R.drawable.broken_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		getCursor().moveToPosition(position);
		ImageSearchResultItem imageSearchResultItem = ImageSearchContentProvider.getSearchResultItemFromCursor(getCursor());
		if (imageSearchResultItem != null) {
			convertView.setVisibility(View.VISIBLE);
			fillData(viewHolder, imageSearchResultItem);
		} else {
			convertView.setVisibility(View.GONE);
		}

		return convertView;
	}

	private void fillData(ViewHolder viewHolder, ImageSearchResultItem imageSearchResultItem) {
		viewHolder.networkImageView.setImageUrl(imageSearchResultItem.getImageUrl(), VolleyClient.getInstance(context).getImageLoader());
		viewHolder.imageTitle.setText(imageSearchResultItem.getImageTitle());
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	}

	static class ViewHolder {
		private TextView imageTitle;
		private NetworkImageView networkImageView;
	}
}
