package com.aupic.aupic.Holder.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.aupic.aupic.R;

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
        this.selectedImagesMap = selectedImagesMap;
        this.selectedImagesMapListener = selectedImagesMapListener;
    }

    public void render(Context context, final int position, final boolean[] thumbnailSelection,
                       final Bitmap[] thumbNails, final String[] arrImagesPath) {

        thumbImage.setId(position);
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

        thumbImage.setImageBitmap(thumbNails[position]);

    }
}
