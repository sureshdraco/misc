package test.betsson.flickrsearch.flickr;

import test.betsson.flickrsearch.network.flickr.response.SearchImageResponse;

import android.test.AndroidTestCase;

import com.google.gson.Gson;

/**
 * Created by suresh on 08/02/15.
 */
public class SearchImageResponseTest extends AndroidTestCase {
	private Gson gson;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		gson = new Gson();
	}

	public void testSuccessResponse() throws Exception {
		String response = "{\"photos\":{\"page\":1,\"pages\":3382779,\"perpage\":1,\"total\":\"3382779\",\"photo\":[{\"id\":\"16474042895\",\"owner\":\"11421804@N04\",\"secret\":\"98776cb970\",\"server\":\"7458\",\"farm\":8,\"title\":\"3945A\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]},\"stat\":\"ok\"}";
		assertEquals(true, gson.fromJson(response, SearchImageResponse.class).isSuccess());
		assertEquals("16474042895", gson.fromJson(response, SearchImageResponse.class).getPhotos().get(0).getId());
	}

	public void testFailedResponse() throws Exception {
		String response = "{\"stat\":\"fail\",\"code\":3,\"message\":\"Parameterless searches have been disabled. Please use flickr.photos.getRecent instead.\"}";
		assertEquals(false, gson.fromJson(response, SearchImageResponse.class).isSuccess());
	}
}
