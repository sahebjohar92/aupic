package com.aupic.aupic.Task.ImageGallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by saheb on 20/11/15.
 */
public class GetImagesTask extends AsyncTask<Object, String, Bitmap>{

    private ImageView imageView;
    private Context context;
    private Bitmap bitmap = null;
    private int path;

    @Override
    protected Bitmap doInBackground(Object... parameters) {

        imageView        = (ImageView) parameters[0];
        context          = (Context) parameters[1];
        String imagePath = (String) parameters[2];

        bitmap = getImageFromImagePath(context, imagePath);

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap != null) {

            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getImageFromImagePath(Context context, String imagePath) {

        Bitmap bitmap = null;
        try {

            File f = new File(imagePath);
            if (f.exists()) {

                Uri contentUri = Uri.fromFile(f);
                InputStream image_stream = context.getContentResolver().openInputStream(contentUri);
                bitmap = BitmapFactory.decodeStream(image_stream);

                bitmap = getResizeBitmap(bitmap, 200);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public Bitmap getResizeBitmap(Bitmap image, int maxSize) {

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}
