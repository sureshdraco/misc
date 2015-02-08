package test.betsson.flickrsearch.network.flickr.response;

import java.util.ArrayList;

/**
 * Created by suresh on 08/02/15.
 */
public class SearchImageResponse extends BaseResponse {
    private ArrayList<Photo> photos;

    @Override
    public String toString() {
        return "photos=" + photos;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public class Photo {
        private String id, title;

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}
