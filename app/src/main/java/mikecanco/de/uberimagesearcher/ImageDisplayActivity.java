package mikecanco.de.uberimagesearcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.loopj.android.image.SmartImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ImageDisplayActivity extends Activity {

    @InjectView(R.id.bSend) Button bSend;
    @InjectView(R.id.ivResult) SmartImageView ivImage;
    ImageResult image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        ButterKnife.inject(this);
        image = (ImageResult) getIntent().getSerializableExtra("result");
        ivImage.setImageUrl(image.getFullUrl());
    }

    @OnClick(R.id.bSend)
    public void clickSend() {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("application/image");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Look here!");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "check out this picture: "+image.getFullUrl());
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
