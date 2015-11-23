package com.aupic.aupic.Task.ImageGallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.LruCache;
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
    private String tag;
    private LruCache<String, Bitmap> mMemoryCache;

    public GetImagesTask(LruCache<String, Bitmap> mMemoryCache) {

        this.mMemoryCache = mMemoryCache;
    }

    @Override
    protected Bitmap doInBackground(Object... parameters) {

        imageView        = (ImageView) parameters[0];
        context          = (Context) parameters[1];
        String imagePath = (String) parameters[2];
        tag = imageView.getTag().toString();

        bitmap = getImageFromImagePath(context, imagePath);
        //bitmap = getBitmapTest1(imagePath);
        addBitmapToMemoryCache(imagePath, bitmap);

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if ( !tag.equals(imageView.getTag().toString()) ) {

            return ;
        }

        if (bitmap != null) {

            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getBitmapTest1(String filePath) {


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Only scale if we need to
        // (16384 buffer for img processing)
        Boolean scaleByHeight = Math.abs(options.outHeight - 100) >= Math.abs(options.outWidth - 100);
        if(options.outHeight * options.outWidth * 2 >= 16384){
            // Load, scaling to smallest power of 2 that'll get it <= desired dimensions
            double sampleSize = scaleByHeight
                    ? options.outHeight / 100
                    : options.outWidth / 100;
            options.inSampleSize =
                    (int)Math.pow(2d, Math.floor(
                            Math.log(sampleSize)/Math.log(2d)));
        }

        // Do the actual decoding
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[512];
        return BitmapFactory.decodeFile(filePath, options);

    }

    private Bitmap getImageFromImagePath(Context context, String imagePath) {

        Bitmap bitmap = null;
        try {

            File f = new File(imagePath);
            if (f.exists()) {

                Uri contentUri = Uri.fromFile(f);
                InputStream image_stream = context.getContentResolver().openInputStream(contentUri);
                bitmap = BitmapFactory.decodeStream(image_stream);

                bitmap = getResizeBitmap(bitmap, 150);
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

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

}
