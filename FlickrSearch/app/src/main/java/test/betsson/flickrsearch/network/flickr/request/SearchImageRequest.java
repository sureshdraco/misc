package test.betsson.flickrsearch.network.flickr.request;

/**
 * Created by suresh on 08/02/15.
 */
public class SearchImageRequest extends GetRequest {
    private static final String NO_OF_SEARCH_RESULT = "1";
    public static final String QUERY_PARAM_NO_OF_SEARCH_RESULT = "per_page";
    public static final String QUERY_PARAM_SEARCH_TEXT = "text";
    public static final String QUERY_PARAM_METHOD = "method";
    public static final String METHOD = "flickr.photos.search";

    private String searchText;

    public SearchImageRequest(String searchText) {
        this.searchText = searchText;
    }

    @Override
    void initQueryParams() {
        queryParams.put(QUERY_PARAM_NO_OF_SEARCH_RESULT, NO_OF_SEARCH_RESULT);
        queryParams.put(QUERY_PARAM_METHOD, METHOD);
        queryParams.put(QUERY_PARAM_SEARCH_TEXT, searchText);
    }

}
