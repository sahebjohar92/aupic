package com.aupic.aupic.Task.AupicGallery;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.Event.AppBus;
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

        String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID };

        String[] mediaColumns = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE };

        String searchParams = "bucket_display_name = \"" + StringConstants.VIDEO + "\"";

        cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, searchParams, null, null);


        if (cursor.moveToFirst()) {
            do {

                VideoGalleryDTO newVVI = new VideoGalleryDTO();
                int id = cursor.getInt(cursor
                                      .getColumnIndex(MediaStore.Video.Media._ID));

                Cursor thumbCursor = context.getContentResolver().query(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                + "=" + id, null, null);

                if (thumbCursor.moveToFirst()) {
                    newVVI.thumbPath = thumbCursor.getString(thumbCursor
                                       .getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                }

                newVVI.filePath = cursor.getString(cursor
                                 .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

                newVVI.title    = cursor.getString(cursor
                                  .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));

                newVVI.mimeType = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));

                videoGalleryDTOList.add(newVVI);
            } while (cursor.moveToNext());
        }

        return videoGalleryDTOList;
    }
}
