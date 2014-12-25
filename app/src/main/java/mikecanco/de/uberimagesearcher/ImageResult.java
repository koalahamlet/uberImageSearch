package mikecanco.de.uberimagesearcher;

/**
 * Created by koalahamlet on 12/25/14.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageResult implements Serializable {
    private static final long serialVersionUID = -6821779976976120547L;
    private String fullUrl;
    private String thumbUrl;

    public ImageResult(JSONObject json){
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
        } catch (JSONException e) {
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

