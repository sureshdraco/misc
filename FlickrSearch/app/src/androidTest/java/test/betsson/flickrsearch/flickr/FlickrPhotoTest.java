package test.betsson.flickrsearch.flickr;

import test.betsson.flickrsearch.network.flickr.response.model.FlickrPhoto;

import android.test.AndroidTestCase;

/**
 * Created by suresh on 08/02/15.
 */
public class FlickrPhotoTest extends AndroidTestCase {

	private final String PHOTO_ID = "123457890";
	private final String TITLE = "title";
	private final String SERVER_ID = "456";
	private final String FARM_ID = "123";
	private final String SECRET = "3frt54";

	public void testGetFlickrImageSource() throws Exception {
		FlickrPhoto flickrPhoto = new FlickrPhoto(PHOTO_ID, TITLE, SERVER_ID, FARM_ID, SECRET);
		assertEquals("https://farm123.staticflickr.com/456/123457890_3frt54_q.jpg", flickrPhoto.getFlickrImageSource());
	}
}
