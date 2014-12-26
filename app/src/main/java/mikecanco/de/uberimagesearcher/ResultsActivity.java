package mikecanco.de.uberimagesearcher;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ResultsActivity extends Activity {

    String query;
    String sColor, sType, sSize = "any";
    String sWebsite = "";


    @InjectView(R.id.gvImages) GridView gvResults;
    @InjectView(R.id.pgBar) ProgressBar pgBar;
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;
    SearchFilter sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.inject(this);
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);

        pgBar.setVisibility(View.VISIBLE);

        Intent i = getIntent();

        sf = (SearchFilter) i
                .getSerializableExtra("filter");
        sColor = sf.getColor();
        sSize = sf.getSize();
        sType = sf.getType();
        sWebsite = sf.getSite();
        if(sWebsite==null){
            sWebsite="";
        }
        query = i.getStringExtra("queryTerm");

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Append more data into the adapter
                requestImages(totalItemsCount);
            }
        });

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View parent,
                                    int position, long arg3) {
                Intent i = new Intent(getApplicationContext(),
                        ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                i.putExtra("result", imageResult);
                startActivity(i);
            }
        });

        requestImages(0);
    }

    private void requestImages(int totalItemsCount) {

        String bigQuery = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
                + "start="
                + totalItemsCount
                + "&imgcolor="
                + sColor
                + "&as_sitesearch="
                + sWebsite
                + "&imgsz=" + sSize + "&imgtype=" + sType
                + "&v=1.0&q="
                + Uri.encode(query);
        Log.d("full query: ", bigQuery);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(bigQuery, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);
                JSONArray imageJsonResults = null;
                pgBar.setVisibility(View.INVISIBLE);
                try {
                    imageJsonResults = response.getJSONObject("responseData")
                            .getJSONArray("results");
                    imageAdapter.addAll(ImageResult
                            .fromJSONArray(imageJsonResults));
                    Log.d("DEBUG", imageResults.toString());
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), R.string.out_of_results, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable e, JSONObject errorResponse) {
                super.onFailure(e, errorResponse);
            }
        });

    }

}
