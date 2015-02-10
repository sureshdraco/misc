package test.betsson.flickrsearch.util;

import java.util.HashMap;

import test.betsson.flickrsearch.util.GetRequestUtil;

import android.test.AndroidTestCase;

/**
 * Created by suresh on 08/02/15.
 */
public class GetRequestUtilTest extends AndroidTestCase {

	public void testGetQueryString() throws Exception {
		HashMap<String, String> queryParams = new HashMap<>();
		queryParams.put("key1", "value1");
		queryParams.put("key2", "value2");
		queryParams.put("key3", "value3");
		queryParams.put("key4", "value4");
		assertEquals("?key4=value4&key3=value3&key2=value2&key1=value1", GetRequestUtil.getQueryString(queryParams));
	}

	public void testEmptyGetQueryString() throws Exception {
		HashMap<String, String> queryParams = new HashMap<>();
		assertEquals("", GetRequestUtil.getQueryString(queryParams));
	}

	public void testOneGetQueryString() throws Exception {
		HashMap<String, String> queryParams = new HashMap<>();
		queryParams.put("key1", "value1");
		assertEquals("?key1=value1", GetRequestUtil.getQueryString(queryParams));
	}
}
