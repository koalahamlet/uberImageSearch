package mikecanco.de.uberimagesearcher;

/**
 * Created by koalahamlet on 12/25/14.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;

public class ImageResult implements Serializable {
    private String fullUrl;
    private String thumbUrl;

    public ImageResult(JSONObject json){
        try {
            this.fullUrl = json.getString("url");
            Log.d("url", fullUrl);
            this.thumbUrl = json.getString("tbUrl");
        } catch (JSONException e) {
            Log.d("danger", "nothing here");
            this.fullUrl = null;
            this.thumbUrl = null;
        }
    }

    public String getFullUrl() {
        return fullUrl;
    }
    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }
    public String getThumbUrl() {
        return thumbUrl;
    }
    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public static ArrayList<ImageResult> fromJSONArray(
            JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new ImageResult(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public String toString() {
        return "ImageResult [fullUrl=" + fullUrl + ", thumbUrl=" + thumbUrl
                + "]";
    }

}

