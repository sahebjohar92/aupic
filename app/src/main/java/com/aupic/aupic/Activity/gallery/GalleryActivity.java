package com.aupic.aupic.Activity.gallery;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.GridView;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Adaptors.Gallery.GalleryImageAdaptors;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 4/11/15.
 */
public class GalleryActivity extends AupFragmentActivity {

    private GalleryImageAdaptors galleryImageAdaptors;

    @InjectView(R.id.uploadDONE)
    Button uploadButton;

    @InjectView(R.id.phoneImageGrid)
    GridView phoneImageGrid;

    @Override
    protected int getTitleText() {

        return R.string.gallery;
    }

    @Override
    protected int getContentViewId() {

        return R.layout.gallery_grid_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBus.getInstance().register(this);

        ButterKnife.inject(this);

        initialize();
    }

    private void initialize() {

        int count;
        Bitmap[] thumbNails;
        boolean[] thumbnailSelection;
        String[] arrPath;

        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;

        Cursor imageCursor =  managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                                           null, null, orderBy);

        int image_column_index = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);

        count = imageCursor.getCount();
        thumbNails = new Bitmap[count];
        arrPath = new String[count];
        thumbnailSelection = new boolean[count];

        for (int i = 0; i < count; i++) {

            imageCursor.moveToPosition(i);
            int id = imageCursor.getInt(image_column_index);
            int dataColumnIndex = imageCursor
                                  .getColumnIndex(MediaStore.Images.Media.DATA);

            thumbNails[i] = MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(), id,
                                                                      MediaStore.Images.Thumbnails.
                                                                      MINI_KIND, null);
            arrPath[i] = imageCursor.getString(dataColumnIndex);
        }

//        galleryImageAdaptors = new GalleryImageAdaptors(this, count, thumbnailSelection,
//                                                        thumbNails);
//        phoneImageGrid.setAdapter(galleryImageAdaptors);
        imageCursor.close();
    }
}
