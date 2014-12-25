package mikecanco.de.uberimagesearcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SearchActivity extends Activity {

    private static final int SETTINGS = 1;
    @InjectView(R.id.etSearch) EditText etQuery;
    @InjectView(R.id.gvImages) GridView gvResults;
    @InjectView(R.id.bSearch) Button btnSearch;
    SearchFilter sf;
    String query;
    String sColor, sType, sSize = "any";
    String sWebsite = "";
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;
    // public static int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
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

        //added this listener to remove 'return' characters from search query
        etQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i = s.length(); i > 0; i--){

                    if(s.subSequence(i-1, i).toString().equals("\n"))
                        s.replace(i-1, i, "");

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            sf = (SearchFilter) data
                    .getSerializableExtra("filter");
            sColor = sf.getColor();
            sSize = sf.getSize();
            sType = sf.getType();
            sWebsite = sf.getSite();
            if(sWebsite==null){
                sWebsite="";
            }

        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, FilterActivity.class);

                // magic number 7
                startActivityForResult(i, 7);
                break;
            default:
                break;
        }
        return true;

    }

    @OnClick(R.id.bSearch)
    public void onImageSearch(View v) {
        // This part dismisses the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etQuery.getWindowToken(), 0);
        //
        imageResults.clear();
        query = etQuery.getText().toString();
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
