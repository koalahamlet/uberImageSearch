package mikecanco.de.uberimagesearcher;

/**
 * Created by koalahamlet on 12/25/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

import java.util.List;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageInfo = this.getItem(position);
        SmartImageView ivImage;
        if (convertView == null){
            LayoutInflater infaltor = LayoutInflater.from(getContext());
            ivImage = (SmartImageView) infaltor.inflate(R.layout.item_image_result, parent, false);
        }else{
            ivImage = (SmartImageView) convertView;
            // set a clear image view
            ivImage.setImageResource(0);
        }
        ivImage.setImageUrl(imageInfo.getThumbUrl());;
        return ivImage;
    }

}

