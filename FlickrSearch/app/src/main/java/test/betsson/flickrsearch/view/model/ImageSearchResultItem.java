package test.betsson.flickrsearch.view.model;

/**
 * Created by suresh.kumar on 2015-02-04.
 */
public class ImageSearchResultItem {

	private String imageUrl, imageTitle;

	public ImageSearchResultItem(String imageUrl, String imageTitle) {
		this.imageUrl = imageUrl;
		this.imageTitle = imageTitle;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getImageTitle() {
		return imageTitle;
	}
}
