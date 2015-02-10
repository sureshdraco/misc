package test.betsson.flickrsearch.network.flickr.request;

import java.util.HashMap;

import test.betsson.flickrsearch.util.GetRequestUtil;

/**
 * Created by suresh on 08/02/15.
 */
public abstract class GetRequest {

	protected HashMap<String, String> queryParams = new HashMap<>();

	private static final String QUERY_PARAM_API_KEY = "api_key";
	private static final String QUERY_PARAM_RESPONSE_FORMAT = "format";
	private static final String QUERY_PARAM_PROPER_JSON = "nojsoncallback";
	private static final String QUERY_VALUE_PROPER_JSON = "1";
	private static final String QUERY_VALUE_RESPONSE_FORMAT = "json";

	private static final String API_KEY = "bd29e274e9626faabb31ab494ba6c84b";

	abstract void initQueryParams();

	public String getRequestParams() {
		initQueryParams();
		queryParams.put(QUERY_PARAM_API_KEY, API_KEY);
		queryParams.put(QUERY_PARAM_RESPONSE_FORMAT, QUERY_VALUE_RESPONSE_FORMAT);
		queryParams.put(QUERY_PARAM_PROPER_JSON, QUERY_VALUE_PROPER_JSON);
		return GetRequestUtil.getQueryString(queryParams);
	}
}