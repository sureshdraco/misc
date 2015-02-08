package test.betsson.flickrsearch.network.flickr.response;

import android.text.TextUtils;

/**
 * Created by suresh on 08/02/15.
 */
public class BaseResponse {
    private String stat;

    public boolean isSuccess() {
        return !TextUtils.isEmpty(stat) && stat.equals("ok");
    }
}
