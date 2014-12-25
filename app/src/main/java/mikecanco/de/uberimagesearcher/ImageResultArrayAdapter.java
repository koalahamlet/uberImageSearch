package mikecanco.de.uberimagesearcher;

/**
 * Created by koalahamlet on 12/25/14.
 */

import android.widget.ArrayAdapter;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.content.ClipData.Item;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
            ivImage.setImageResource(android.R.color.transparent);
        }
        ivImage.setImageUrl(imageInfo.getThumbUrl());
        return ivImage;
    }

}

