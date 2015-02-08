package test.betsson.flickrsearch;

import android.test.AndroidTestCase;

import com.google.gson.Gson;

import test.betsson.flickrsearch.network.flickr.response.SearchImageResponse;

/**
 * Created by suresh on 08/02/15.
 */
public class SearchImageResponseTest extends AndroidTestCase {
    private String response = "{\"photos\":{\"page\":1,\"pages\":3382779,\"perpage\":1,\"total\":\"3382779\",\"photo\":[{\"id\":\"16474042895\",\"owner\":\"11421804@N04\",\"secret\":\"98776cb970\",\"server\":\"7458\",\"farm\":8,\"title\":\"3945A\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]},\"stat\":\"ok\"}";
    private Gson gson;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        gson = new Gson();
    }

    public void testResponseParse() throws Exception {
        gson.fromJson(response, SearchImageResponse.class).isSuccess();
    }
}
