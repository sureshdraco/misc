package test.betsson.flickrsearch.network.flickr.response.model;

public class FlickrPhoto {
	// https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
	private static final String FLICKR_IMAGE_SOURCE = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";
	private static final String FLICKR_IMAGE_SIZE = "q";

	private String id, title, server, farm, secret;

	public FlickrPhoto(String id, String title, String server, String farm, String secret) {
		this.id = id;
		this.title = title;
		this.server = server;
		this.farm = farm;
		this.secret = secret;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getFlickrImageSource() {
		return String.format(FLICKR_IMAGE_SOURCE, farm, server, id, secret, FLICKR_IMAGE_SIZE);
	}
}