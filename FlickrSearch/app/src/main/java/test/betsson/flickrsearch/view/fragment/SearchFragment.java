package test.betsson.flickrsearch.view.fragment;

import test.betsson.flickrsearch.R;
import test.betsson.flickrsearch.provider.ImageSearchContentProvider;
import test.betsson.flickrsearch.service.ImagesService;
import test.betsson.flickrsearch.util.Constant;
import test.betsson.flickrsearch.view.adapter.ImageSearchResultAdapter;
import test.betsson.flickrsearch.view.custom.ActivityProgressIndicator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

	private ImageSearchResultAdapter imageSearchResultAdapter;
	private ListView searchResultListView;
	private Button searchBtn;
	private EditText searchText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
	}

	private void initView(View view) {
		searchResultListView = (ListView) view.findViewById(R.id.searchResultListView);
		searchText = (EditText) view.findViewById(R.id.searchText);
		searchBtn = (Button) view.findViewById(R.id.searchBtn);
		searchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateInput()) {
					getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
					ImagesService.initiateImageSearch(getActivity(), String.valueOf(searchText.getText()));
				}
			}
		});
		imageSearchResultAdapter = new ImageSearchResultAdapter(getActivity(), null);
		searchResultListView.setAdapter(imageSearchResultAdapter);
	}

	private boolean validateInput() {
		if (TextUtils.isEmpty(String.valueOf(searchText.getText()))) {
			searchText.setError(getResources().getString(R.string.search_text_missing));
			return false;
		}
		searchBtn.setError("");
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader cursorLoader = null;
		if (getActivity() != null) {
			cursorLoader = new CursorLoader(getActivity(), ImageSearchContentProvider.CONTENT_URI,
					null, null, null, null);
		}
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data != null && getActivity() != null) {
			data.setNotificationUri(getActivity().getContentResolver(), ImageSearchContentProvider.CONTENT_URI);
			imageSearchResultAdapter.changeCursor(data);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	@Override
	public void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(imageSearchResultBroadcast,
				new IntentFilter(ImagesService.BROADCAST_IMAGE_SEARCH_COMPLETED));
	}

	@Override
	public void onPause() {
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(imageSearchResultBroadcast);
		super.onPause();
	}

	private BroadcastReceiver imageSearchResultBroadcast = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				int resultCode = intent.getIntExtra(Constant.INTENT_EXTRA_RESULT_CODE, Constant.INTENT_EXTRA_RESULT_FAILED);
				if (isAdded()) {
					getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
					if (resultCode == Constant.INTENT_EXTRA_RESULT_SUCCESS) {
						getLoaderManager().restartLoader(0, null, SearchFragment.this);
					} else {
						Crouton.showText(getActivity(), context.getString(R.string.search_failed), Style.ALERT);
					}
				}
			}
		}
	};
}
