package test.betsson.flickrsearch.network.flickr.request;

import java.util.HashMap;

import test.betsson.flickrsearch.util.TeamsApiUtil;

/**
 * Created by suresh on 08/02/15.
 */
public abstract class GetRequest {

    protected HashMap<String, String> queryParams = new HashMap<>();

    private static final String QUERY_PARAM_API_KEY = "api_key";
    private static final String QUERY_PARAM_RESPONSE_FORMAT = "format";
    private static final String RESPONSE_FORMAT = "json";

    private static final String API_KEY = "bd29e274e9626faabb31ab494ba6c84b";

    abstract void initQueryParams();

    public String getRequestParams() {
        initQueryParams();
        queryParams.put(QUERY_PARAM_API_KEY, API_KEY);
        queryParams.put(QUERY_PARAM_RESPONSE_FORMAT, RESPONSE_FORMAT);
        return TeamsApiUtil.getQueryString(queryParams);
    }
}