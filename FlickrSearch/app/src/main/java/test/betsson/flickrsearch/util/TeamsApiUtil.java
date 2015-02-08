package test.betsson.flickrsearch.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TeamsApiUtil {
    private static final String TAG = TeamsApiUtil.class.getSimpleName();

    public static String getQueryString(HashMap<String, String> params) {
        ArrayList<String> keys = new ArrayList<>(params.keySet());
        if (keys.isEmpty())
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        Collections.sort(keys);
        stringBuilder.append("?");
        for (int i = 0; i < keys.size(); i++) {
            stringBuilder.append(keys.get(i));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(params.get(keys.get(i))));
            if (i != keys.size() - 1) {
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }
}
