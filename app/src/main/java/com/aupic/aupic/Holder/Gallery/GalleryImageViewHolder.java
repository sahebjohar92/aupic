package com.aupic.aupic.Holder.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.aupic.aupic.R;
import com.aupic.aupic.Task.ImageGallery.GetImagesTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 4/11/15.
 */
public class GalleryImageViewHolder  {

    @InjectView(R.id.thumbImage)
    ImageView thumbImage;

    @InjectView(R.id.tick)
    ImageView imageSelectionTick;

    @InjectView(R.id.select_box)
    ImageView selectBox;

    public interface SelectedImagesMap {

        public void getSelectedImagesMap(HashMap<String, Bitmap> selectedImagesMap);
    }

    private SelectedImagesMap selectedImagesMapListener;
    private HashMap<String, Bitmap> selectedImagesMap;

    public GalleryImageViewHolder(View view, HashMap<String, Bitmap> selectedImagesMap,
                                  SelectedImagesMap selectedImagesMapListener) {

        ButterKnife.inject(this, view);
        this.selectedImagesMap         = selectedImagesMap;
        this.selectedImagesMapListener = selectedImagesMapListener;
    }

    public void render(Context context, final int position, final boolean[] thumbnailSelection,
                       final Bitmap[] thumbNails, final String[] arrImagesPath) {

        thumbImage.setId(position);
        thumbImage.setTag(position);
        imageSelectionTick.setId(position);
        selectBox.setImageResource(R.drawable.ic_action_brands);

        thumbImage.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                int id = thumbImage.getId();

                if ( thumbnailSelection[id]) {

                    selectedImagesMap.remove(arrImagesPath[id]);
                    selectBox.setImageResource(R.drawable.ic_action_brands);
                    thumbnailSelection[id] = false;
                } else {
                    selectedImagesMap.put(arrImagesPath[id], null);
                    selectBox.setImageResource(R.drawable.red_tick);
                    thumbnailSelection[id] = true;
                }

                selectedImagesMapListener.getSelectedImagesMap(selectedImagesMap);
            }
        });

//        Using Picasso so commenting this code
//        Bitmap image = getBitmapFromMemCache(arrImagesPath[position]);
//
//        if (null == image) {
//            thumbImage.setImageBitmap(null);
//            new GetImagesTask(mMemoryCache).execute(thumbImage, context, arrImagesPath[position]);
//        } else {
//            thumbImage.setImageBitmap(image);
//        }
        Uri uri = Uri.fromFile(new File(arrImagesPath[position]));
        Picasso.with(context)
                .load(uri)
                .placeholder(null)
                .resize(250, 300)
                .into(thumbImage);
    }


}
