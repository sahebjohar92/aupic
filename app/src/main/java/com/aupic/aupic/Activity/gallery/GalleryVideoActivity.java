package com.aupic.aupic.Activity.gallery;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Adaptors.Gallery.GalleryVideoAdaptor;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Holder.Gallery.VideoGalleryDTO;
import com.aupic.aupic.Holder.Gallery.VideoGalleryListDTO;
import com.aupic.aupic.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 23/12/15.
 */
public class GalleryVideoActivity extends AupFragmentActivity {

    private VideoGalleryListDTO videoGalleryListDTO;
    private GalleryVideoAdaptor galleryVideoAdaptor;

    @InjectView(R.id.phoneAlbumGrid)
    GridView phoneAlbumGrid;

    @InjectView(R.id.ll_grid_view)
    RelativeLayout ll_grid_view;

    @Override
    protected int getTitleText() {

        return R.string.aupic_video;
    }

    @Override
    protected int getContentViewId() {

        return R.layout.albums_grid_view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBus.getInstance().register(this);

        ButterKnife.inject(this);

        ll_grid_view.setBackgroundColor(getResources().getColor(R.color.light_orange));

        if (null != getIntent() && null != getIntent().getExtras()) {

            videoGalleryListDTO = (VideoGalleryListDTO) getIntent().
                                  getSerializableExtra(IntentConstants.AUPIC_MAP);
        } else {

            videoGalleryListDTO.setVideoGalleryDTOList(loadData());
        }

        galleryVideoAdaptor = new GalleryVideoAdaptor(this, R.id.phoneAlbumGrid, videoGalleryListDTO);
        phoneAlbumGrid.setAdapter(galleryVideoAdaptor);
    }

    private List<VideoGalleryDTO> loadData() {

        List<VideoGalleryDTO> videoGalleryDTOList = new ArrayList<>();

        Cursor cursor;

        String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID };

        String[] mediaColumns = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE };

        String searchParams = "bucket_display_name = \"" + StringConstants.VIDEO + "\"";

        cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, searchParams, null, null);


        if (cursor.moveToFirst()) {
            do {

                VideoGalleryDTO newVVI = new VideoGalleryDTO();
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Video.Media._ID));

                Cursor thumbCursor = getContentResolver().query(
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
