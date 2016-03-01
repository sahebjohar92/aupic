package com.aupic.aupic.Holder.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.aupic.aupic.Activity.gallery.GalleryImagesActivity;
import com.aupic.aupic.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.LinkedHashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 4/11/15.
 */
public class GalleryImageViewHolder  {

    @InjectView(R.id.thumbImage)
    ImageView thumbImage;

    @InjectView(R.id.select_box)
    ImageView selectBox;


    public interface SelectedImagesMap {

        public void getSelectedImagesMap( LinkedHashMap<String, Integer> selectedImagesList);
    }

    private SelectedImagesMap selectedImagesMapListener;
    private LinkedHashMap<String, Integer> selectedImagesList = new LinkedHashMap<>();

    public GalleryImageViewHolder(View view,  LinkedHashMap<String, Integer> selectedImagesList,
                                  SelectedImagesMap selectedImagesMapListener) {

        ButterKnife.inject(this, view);
        this.selectedImagesList         = selectedImagesList;
        this.selectedImagesMapListener = selectedImagesMapListener;
    }

    public void render(final Context context, final int position, final boolean[] thumbnailSelection,
                       final Bitmap[] thumbNails, final String[] arrImagesPath,
                       final GalleryImagesActivity galleryImagesActivity) {


        thumbImage.setId(position);
        thumbImage.setTag(position);
        selectBox.setImageResource(R.drawable.ic_action_brands);

        thumbImage.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {

                int id = thumbImage.getId();

                Integer count;

                if ( thumbnailSelection[id]) {

                    selectedImagesList = galleryImagesActivity.
                                                removeImage(arrImagesPath[id], selectedImagesList);

                    selectBox.setImageResource(R.drawable.ic_action_brands);
                    thumbnailSelection[id] = false;
                } else {
                    count = galleryImagesActivity.getCount();
                    selectedImagesList.put(arrImagesPath[id], ++count);

                    galleryImagesActivity.incrementCount();

                    selectBox.setImageResource(R.drawable.blue_tick);
                    thumbnailSelection[id] = true;
                }

                selectedImagesMapListener.getSelectedImagesMap(selectedImagesList);
            }
        });

        Uri uri = Uri.fromFile(new File(arrImagesPath[position]));
        Picasso.with(context)
                .load(uri)
                .placeholder(null)
                .resize(250, 300)
                .into(thumbImage);
    }


}
