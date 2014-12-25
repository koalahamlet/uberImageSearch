package mikecanco.de.uberimagesearcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FilterActivity extends Activity {

    @InjectView(R.id.bSubmit) Button bSubmit;
    @InjectView(R.id.etSiteFilter) EditText etWebsite;
    @InjectView(R.id.sprColorFilter) Spinner sprColor;
    @InjectView(R.id.sprImageType) Spinner sprType;
    @InjectView(R.id.sprImageSize) Spinner sprSize;
    String sColor, sType, sSize, sWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.inject(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @OnClick(R.id.bSubmit)
    public void clickSubmit() {
        final SearchFilter sfilter = new SearchFilter();

        sColor=sprColor.getSelectedItem().toString();
        sfilter.setColor(sColor);

        sType=sprType.getSelectedItem().toString();
        sfilter.setType(sType);

        sSize=sprSize.getSelectedItem().toString();
        sfilter.setSize(sSize);

        if(!(etWebsite.getText().toString()).isEmpty()){
            sWebsite = etWebsite.getText().toString();
            sWebsite.replaceAll("\\s+","");
            sfilter.setSite(sWebsite);
        }

        Intent i = new Intent();
        i.putExtra("filter", sfilter);
        setResult(RESULT_OK, i);
        finish();
    }

}
