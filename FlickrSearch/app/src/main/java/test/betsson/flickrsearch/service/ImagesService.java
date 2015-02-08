package test.betsson.flickrsearch.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import test.betsson.flickrsearch.BuildConfig;
import test.betsson.flickrsearch.network.flickr.ApiRequests;
import test.betsson.flickrsearch.network.flickr.response.SearchImageResponse;

public class ImagesService extends Service {
    private static final String INTENT_ACTION_INITIATE_IMAGE_SEARCH = "INTENT_ACTION_INITIATE_IMAGE_SEARCH";
    private static final String TAG = ImagesService.class.getSimpleName();
    private static final String INTENT_EXTRA_SEARCH_TEXT = "searchText";

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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (BuildConfig.DEBUG) Log.e(TAG, String.valueOf(volleyError.getMessage()));
            }
        });
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
