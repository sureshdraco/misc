package test.betsson.flickrsearch.service;

import java.util.ArrayList;

import test.betsson.flickrsearch.BuildConfig;
import test.betsson.flickrsearch.network.flickr.ApiRequests;
import test.betsson.flickrsearch.network.flickr.response.SearchImageResponse;
import test.betsson.flickrsearch.network.flickr.response.model.FlickrPhoto;
import test.betsson.flickrsearch.provider.ImageSearchContentProvider;
import test.betsson.flickrsearch.util.Constant;
import test.betsson.flickrsearch.view.model.ImageSearchResultItem;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class ImagesService extends Service {
	private static final String INTENT_ACTION_INITIATE_IMAGE_SEARCH = "INTENT_ACTION_INITIATE_IMAGE_SEARCH";
	private static final String TAG = ImagesService.class.getSimpleName();
	private static final String INTENT_EXTRA_SEARCH_TEXT = "searchText";
	public static final String BROADCAST_IMAGE_SEARCH_COMPLETED = "broadcastImageSearch";

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null && intent.getAction() != null) {
			if (intent.getAction().equals(INTENT_ACTION_INITIATE_IMAGE_SEARCH)) {
				String searchText = intent.getStringExtra(INTENT_EXTRA_SEARCH_TEXT);
				handleImageSearch(searchText);
			}
		}
		return START_STICKY;
	}

	private void handleImageSearch(String searchText) {
		ApiRequests.searchImages(getApplicationContext(), searchText, new Response.Listener<SearchImageResponse>() {
			@Override
			public void onResponse(SearchImageResponse searchImageResponse) {
				if (BuildConfig.DEBUG) Log.d(TAG, searchImageResponse.toString());
				if (searchImageResponse.isSuccess()) {
					handleImageSearchSuccess(searchImageResponse);
				} else {
					handleImageSearchFailed();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				if (BuildConfig.DEBUG) Log.e(TAG, String.valueOf(volleyError.getMessage()));
				handleImageSearchFailed();
			}
		});
	}

	private void handleImageSearchFailed() {
		clearCache();
		notifyImageSearchCompleted(false);
	}

	private void handleImageSearchSuccess(SearchImageResponse searchImageResponse) {
		clearCache();
		cacheImageSearchResult(searchImageResponse);
		notifyImageSearchCompleted(true);
	}

	private void notifyImageSearchCompleted(boolean success) {
		Intent intent = new Intent(BROADCAST_IMAGE_SEARCH_COMPLETED);
		intent.putExtra(Constant.INTENT_EXTRA_RESULT_CODE, success ? Constant.INTENT_EXTRA_RESULT_SUCCESS : Constant.INTENT_EXTRA_RESULT_FAILED);
		LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
	}

	private void cacheImageSearchResult(SearchImageResponse searchImageResponse) {
		ArrayList<ImageSearchResultItem> imageSearchResultItems = new ArrayList<>();
		for (FlickrPhoto flickrPhoto : searchImageResponse.getPhotos()) {
			imageSearchResultItems.add(new ImageSearchResultItem(flickrPhoto.getFlickrImageSource(), flickrPhoto.getTitle()));
		}
		ImageSearchContentProvider.saveImageSearchResult(getApplicationContext(), imageSearchResultItems);
	}

	private void clearCache() {
		getContentResolver().delete(ImageSearchContentProvider.CONTENT_URI, null, null);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// not used
		return null;
	}

	public static void initiateImageSearch(Context context, String searchText) {
		if (context == null || TextUtils.isEmpty(searchText)) {
			return;
		}
		if (BuildConfig.DEBUG) Log.d(TAG, "initiateImageSearch");
		Intent intent = new Intent(context, ImagesService.class);
		intent.setAction(ImagesService.INTENT_ACTION_INITIATE_IMAGE_SEARCH);
		intent.putExtra(INTENT_EXTRA_SEARCH_TEXT, searchText);
		context.startService(intent);
	}
}
