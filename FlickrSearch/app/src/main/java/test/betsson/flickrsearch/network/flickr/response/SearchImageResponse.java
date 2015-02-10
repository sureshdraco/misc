package test.betsson.flickrsearch.network.flickr.response;

import java.util.ArrayList;

import test.betsson.flickrsearch.network.flickr.response.model.FlickrPhoto;

/**
 * Created by suresh on 08/02/15.
 */
public class SearchImageResponse extends BaseResponse {
	private Photos photos;

	public ArrayList<FlickrPhoto> getPhotos() {
		return photos.getPhotos();
	}

	class Photos {
		private ArrayList<FlickrPhoto> photo;

		public ArrayList<FlickrPhoto> getPhotos() {
			return photo;
		}
	}
}
