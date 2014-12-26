package mikecanco.de.uberimagesearcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SearchActivity extends Activity {

    @InjectView(R.id.etSearch) EditText etQuery;
    @InjectView(R.id.lvPrevious) ListView lvPrevious;
    @InjectView(R.id.textView) TextView tvSearched;
    SharedPreferences prefs;
    SearchFilter sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        sf = new SearchFilter();

        //added this listener to remove 'return' characters from search query
        etQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void afterTextChanged(Editable s) {
                for(int i = s.length(); i > 0; i--){
                    if(s.subSequence(i-1, i).toString().equals("\n"))
                        s.replace(i-1, i, "");
                }
            }
        });
        setupPreviousSearchedList();
    }

    private void setupPreviousSearchedList() {
        prefs = this.getSharedPreferences(
                "mikecanco.de.uberimagesearcher", Context.MODE_PRIVATE);
        if (prefs.contains("searchedList")){
            tvSearched.setVisibility(View.VISIBLE);
            lvPrevious.setVisibility(View.VISIBLE);
            Set<String> set = prefs.getStringSet("searchedList", new HashSet<String>());
            final ArrayList<String> list = new ArrayList<String>();
            list.addAll(set);
            final ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, list);
            lvPrevious.setAdapter(adapter);
        }
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
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, FilterActivity.class);
                startActivityForResult(i, 7);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupPreviousSearchedList();
    }

    @OnClick(R.id.bSearch)
    public void onImageSearch(View v) {
        // These lines dismisses the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etQuery.getWindowToken(), 0);
        //TODO:start ResultsActivity
        String query = etQuery.getText().toString();
        Set<String> set = prefs.getStringSet("searchedList", new HashSet<String>());
        // guard against similar searches
        if (!set.contains(query)){
            set.add(query);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("searchedList", set);
        editor.commit();

        Intent i = new Intent(this, ResultsActivity.class);
        i.putExtra("filter", sf);
        i.putExtra("queryTerm", query);
        startActivity(i);
    }
}
