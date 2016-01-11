package com.aupic.aupic.Activity.gallery;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.aupic.aupic.Activity.aupic.AupicCreatorActivity;
import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Adaptors.Gallery.GalleryImageAdaptors;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Holder.Gallery.GalleryImageViewHolder;
import com.aupic.aupic.R;
import com.aupic.aupic.Storage.TransientDataRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.Duration;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by saheb on 4/11/15.
 */
public class GalleryImagesActivity extends AupFragmentActivity implements GalleryImageViewHolder.SelectedImagesMap {

    private String albumName = StringConstants.DEFAULT_ALBUM_NAME;
    private LinkedHashMap<String, Integer> selectedImagesList = new LinkedHashMap<>();
    private boolean newActivity = true;

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

        if (null != getIntent() && null != getIntent().getExtras()) {

            albumName = getIntent().getStringExtra(IntentConstants.ALBUM_NAME_INTENT_EXTRA);
            newActivity = getIntent().getExtras().getBoolean(IntentConstants.NEW_ACTIVITY);
        }

        this.setTitleText(albumName + StringConstants.WHITESPACE + StringConstants.IMAGES);

        initialize();
    }

    private void initialize() {

        int count;
        Bitmap[] thumbNails;
        boolean[] thumbnailSelection;
        String[] arrPath;
        String searchParams = "bucket_display_name = \"" + albumName + "\"";
        GalleryImageAdaptors galleryImageAdaptors;

        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;

        Cursor imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                    columns, searchParams, null, orderBy + " DESC");

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

            arrPath[i] = imageCursor.getString(dataColumnIndex);
        }

        galleryImageAdaptors = new GalleryImageAdaptors(this, count, thumbnailSelection,
                                                        thumbNails, arrPath, selectedImagesList, this,
                                                        this);
        phoneImageGrid.setAdapter(galleryImageAdaptors);
        imageCursor.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSelectedImagesMap( LinkedHashMap<String, Integer> selectedImagesList) {

        this.selectedImagesList = selectedImagesList;

        LinkedHashMap<String, Integer> getImagesListData = ( LinkedHashMap<String, Integer>) TransientDataRepo.getInstance().
                                                           getData(StringConstants.SELECTED_IMAGES);

        if ( null != getImagesListData) {

            getImagesListData.putAll(selectedImagesList);
            this.selectedImagesList = getImagesListData;
        }

        TransientDataRepo.getInstance().putData(StringConstants.SELECTED_IMAGES,
                                                this.selectedImagesList);
    }

    @Optional
    @OnClick(R.id.uploadDONE)
    public void setUploadButton() {

        if (null == selectedImagesList || selectedImagesList.size() == 0) {

            Toast.makeText(this,StringConstants.UPLOAD_BUTTON_WARNING_NO_IMAGE_SELECTED ,
                            Toast.LENGTH_SHORT).show();
        } else {

            if (newActivity) {

                Intent aupicCreatorIntent = new Intent(this, AupicCreatorActivity.class);
                startActivity(aupicCreatorIntent);

            } else {

                Intent resultIntent = new Intent();
                resultIntent.putExtra(IntentConstants.SELECTED_IMAGES_MAP, true);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }
    }


}
