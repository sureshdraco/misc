package test.betsson.flickrsearch.network.flickr;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;

import test.betsson.flickrsearch.network.flickr.request.SearchImageRequest;
import test.betsson.flickrsearch.network.flickr.response.SearchImageResponse;
import test.betsson.flickrsearch.network.volley.GsonRequest;
import test.betsson.flickrsearch.network.volley.VolleyClient;

public class ApiRequests {
    private static final String API_BASE_URL = "https://api.flickr.com/services/rest/";

    public static void searchImages(Context context, String searchText, Response.Listener<SearchImageResponse> listener, Response.ErrorListener errorListener) {
        SearchImageRequest searchImageRequest = new SearchImageRequest(searchText);
        VolleyClient
                .getInstance(context)
                .getRequestQueue()
                .add(new GsonRequest<SearchImageResponse>(Request.Method.GET, API_BASE_URL + searchImageRequest.getRequestParams(), searchImageRequest,
                        SearchImageResponse.class, null, listener, errorListener));
    }
}