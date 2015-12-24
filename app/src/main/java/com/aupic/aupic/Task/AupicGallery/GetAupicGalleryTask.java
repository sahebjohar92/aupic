package com.aupic.aupic.Task.AupicGallery;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Helper.ImageCacheHelper;
import com.aupic.aupic.Holder.Gallery.VideoGalleryDTO;
import com.aupic.aupic.Holder.Gallery.VideoGalleryListDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saheb on 23/12/15.
 */
public class GetAupicGalleryTask extends AsyncTask<Object, String, VideoGalleryListDTO>{

    Context context = null;

    @Override
    protected VideoGalleryListDTO doInBackground(Object... parameters) {

        VideoGalleryListDTO videoGalleryListDTO = new VideoGalleryListDTO();
        List<VideoGalleryDTO> videoGalleryDTOList = new ArrayList<>();
        context = (Context) parameters[0];

        videoGalleryDTOList = loadData(videoGalleryDTOList);

        videoGalleryListDTO.setVideoGalleryDTOList(videoGalleryDTOList);

        return videoGalleryListDTO;
    }

    @Override
    protected void onPostExecute(VideoGalleryListDTO videoGalleryDTOList) {

        AppBus.getInstance().post(videoGalleryDTOList);
    }


    private List<VideoGalleryDTO> loadData(List<VideoGalleryDTO> videoGalleryDTOList) {

        Cursor cursor;

        String[] mediaColumns = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE };

        String searchParams = "bucket_display_name = \"" + StringConstants.VIDEO + "\"";

        cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, searchParams, null, null);


        if (cursor.moveToFirst()) {
            do {

                VideoGalleryDTO newVVI = new VideoGalleryDTO();

                newVVI.filePath = cursor.getString(cursor
                                 .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                newVVI.title    = cursor.getString(cursor
                                  .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));

                newVVI.mimeType = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));

                getVideoThumbNail(newVVI.filePath);

                videoGalleryDTOList.add(newVVI);
            } while (cursor.moveToNext());
        }

        return videoGalleryDTOList;
    }

    private void getVideoThumbNail(String fileName) {

        Bitmap image;
        boolean isInCache;
        isInCache = ImageCacheHelper.getInstance().hasImage(fileName);

        if (!isInCache) {
            image = ThumbnailUtils.createVideoThumbnail(fileName, MediaStore.Video.Thumbnails.MINI_KIND);
            ImageCacheHelper.getInstance().addToCache(fileName, image);
        }
    }
}
