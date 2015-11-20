package com.aupic.aupic.Holder.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.aupic.aupic.R;
import com.aupic.aupic.Task.ImageGallery.GetImagesTask;

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
    private boolean isImageLoaded = false;

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

        thumbImage.setImageBitmap(null);

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

        if (!thumbImage.isInLayout()) {
            isImageLoaded = true;
            new GetImagesTask().execute(thumbImage, context, arrImagesPath[position]);
        }

        //thumbImage.setImageBitmap(getImageFromImagePath(context, arrImagesPath[position]));
    }

//    private Bitmap getImageFromImagePath(Context context, String imagePath) {
//
//        Bitmap bitmap = null;
//        try {
//
//            File f = new File(imagePath);
//            if (f.exists()) {
//
//                Uri contentUri = Uri.fromFile(f);
//                InputStream image_stream = context.getContentResolver().openInputStream(contentUri);
//                bitmap = BitmapFactory.decodeStream(image_stream);
//
//                bitmap = getResizeBitmap(bitmap, 100);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return bitmap;
//    }
//
//    public Bitmap getResizeBitmap(Bitmap image, int maxSize) {
//
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float)width / (float) height;
//        if (bitmapRatio > 0) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }
}
