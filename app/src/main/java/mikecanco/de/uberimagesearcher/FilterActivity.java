package mikecanco.de.uberimagesearcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class FilterActivity extends Activity {

    Button bSubmit;
    EditText etWebsite;
    Spinner sprColor;
    Spinner sprType;
    Spinner sprSize;
    CheckBox cbColor;
    CheckBox cbType;
    CheckBox cbSize;
    String sColor;
    String sType;
    String sSize;
    String sWebsite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initializeViews();

        final SearchFilter sfIsTheBest = new SearchFilter();


        bSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                sColor=sprColor.getSelectedItem().toString();
                sfIsTheBest.setColor(sColor);



                sType=sprType.getSelectedItem().toString();
                sfIsTheBest.setType(sType);



                sSize=sprSize.getSelectedItem().toString();
                sfIsTheBest.setSize(sSize);


                if(!(etWebsite.getText().toString()).isEmpty()){
                    sWebsite = etWebsite.getText().toString();
                    sWebsite.replaceAll("\\s+","");
                    sfIsTheBest.setSite(sWebsite);
                }

                Intent i = new Intent();
                i.putExtra("filter", sfIsTheBest);
                setResult(RESULT_OK, i);
                finish();


            }
        });
    }

    private void initializeViews() {

        bSubmit = (Button) findViewById(R.id.bSubmit);
        etWebsite = (EditText) findViewById(R.id.etSiteFilter);
        sprColor = (Spinner) findViewById(R.id.sprColorFilter);
        sprType = (Spinner) findViewById(R.id.sprImageType);
        sprSize = (Spinner) findViewById(R.id.sprImageSize);



        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etWebsite.getWindowToken(), 0);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
