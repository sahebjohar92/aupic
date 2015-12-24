package com.aupic.aupic.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.aupic.aupic.Storage.DiskLruCache;

import java.io.File;

/**
 * Created by saheb on 24/12/15.
 */
public class ImageCacheHelper {

    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 30; // 30MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";

    private static DiskLruCache mDiskLruCache = null;
    private static ImageCacheHelper instance = null;

    private Context context;

    private ImageCacheHelper(Context context){
        this.context = context;
    }

    public static ImageCacheHelper getInstance(){
        return instance;
    }

    public static void init(Context context){
        instance = new ImageCacheHelper(context);
        instance.initCache();
    }

    public void initCache(){

        File cacheDir = DiskLruCache.getDiskCacheDir(context, DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheDir);
    }

    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... params) {
            synchronized (mDiskCacheLock) {
                File cacheDir = params[0];
                mDiskLruCache = DiskLruCache.openCache(context, cacheDir, DISK_CACHE_SIZE);
                mDiskCacheStarting = false; // Finished initialization
                mDiskCacheLock.notifyAll(); // Wake any waiting threads
            }
            return null;
        }
    }


    private void addBitmapToCache(String url, Bitmap bitmap) {
        String key = convertUrlToName(url);
        // Also add to disk cache
        synchronized (mDiskCacheLock) {
            if (mDiskLruCache != null && mDiskLruCache.get(key) == null) {
                mDiskLruCache.put(key, bitmap);
            }
        }
    }

    public Bitmap getBitmapFromDiskCache(String url) {
        synchronized (mDiskCacheLock) {
            // Wait while disk cache is started from background thread
            while (mDiskCacheStarting) {
                try {
                    mDiskCacheLock.wait();
                } catch (InterruptedException e) {}
            }
            if (mDiskLruCache != null) {
                return mDiskLruCache.get(convertUrlToName(url));
            }
        }
        return null;
    }

    public boolean hasImage(String url){
        // Wait while disk cache is started from background thread
        while (mDiskCacheStarting) {
            try {
                mDiskCacheLock.wait();
            } catch (InterruptedException e) {}
        }
        if (mDiskLruCache != null) {
            return (mDiskLruCache.get(convertUrlToName(url)) != null);
        }
        return false;
    }

    public static String convertUrlToName(String url){
        if(url != null && !url.isEmpty()){
            return url.hashCode() + "";
        }
        return "";
    }

    public void loadFromCache(String url, BitmapLoaderCallbacks bitmapLoaderCallbacks){
        new GetBitmapTask(url, bitmapLoaderCallbacks).execute();
    }

    public void addToCache(String url,Bitmap bitmap){
        if(bitmap != null && url != null){
            addBitmapToCache(url, bitmap);
        }
    }

    public void clearCache(){
        if(mDiskLruCache != null){
            mDiskLruCache.clearCache();
        }
    }


    public static interface BitmapLoaderCallbacks{

        void onBitmapLoaded(String url, Bitmap bitmap);
        void onLoadingFailed();
    }

    class GetBitmapTask extends AsyncTask<Void, Void, Bitmap> {

        String url;
        BitmapLoaderCallbacks bitmapLoaderCallbacks;

        public GetBitmapTask(String url, BitmapLoaderCallbacks bitmapLoaderCallbacks){
            this.url = url;
            this.bitmapLoaderCallbacks = bitmapLoaderCallbacks;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = getBitmapFromDiskCache(url);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmapLoaderCallbacks != null) {
                if (bitmap != null) {
                    bitmapLoaderCallbacks.onBitmapLoaded(url,bitmap);
                }else {
                    bitmapLoaderCallbacks.onLoadingFailed();
                }
            }

        }
    }
}
