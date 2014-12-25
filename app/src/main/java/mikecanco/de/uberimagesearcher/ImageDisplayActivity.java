package mikecanco.de.uberimagesearcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {


    SmartImageView ivImage;
    Button bSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        final ImageResult image = (ImageResult) getIntent().getSerializableExtra("result");
        bSend = (Button) findViewById(R.id.bSend);
        ivImage = (SmartImageView) findViewById(R.id.ivResult);
        ivImage.setImageUrl(image.getFullUrl());




        bSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("application/image");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Look here!");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "check out this picture: "+image.getFullUrl());
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));

            }
        });




    }

}
