package com.aupic.aupic.Activity.gallery;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

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
    private HashMap<String, Bitmap> selectedImagesMap = new HashMap<>();
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

            thumbNails[i] = MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(), id,
                                                    MediaStore.Images.Thumbnails.MINI_KIND, null);
            arrPath[i] = imageCursor.getString(dataColumnIndex);
        }

        galleryImageAdaptors = new GalleryImageAdaptors(this, count, thumbnailSelection,
                                                        thumbNails, arrPath, selectedImagesMap, this);
        phoneImageGrid.setAdapter(galleryImageAdaptors);
        imageCursor.close();
    }

    @Override
    public void getSelectedImagesMap(HashMap<String, Bitmap> selectedImagesMap) {

        this.selectedImagesMap = selectedImagesMap;
        Log.d("Size of map", "" + selectedImagesMap.size());
    }

    @Optional
    @OnClick(R.id.uploadDONE)
    public void setUploadButton() {

        if (null == selectedImagesMap || selectedImagesMap.size() == 0) {

            Toast.makeText(this,StringConstants.UPLOAD_BUTTON_WARNING_NO_IMAGE_SELECTED ,
                            Toast.LENGTH_SHORT).show();
        } else {

            if (newActivity) {

                Intent aupicCreatorIntent = new Intent(this, AupicCreatorActivity.class);
                aupicCreatorIntent.putExtra(IntentConstants.SELECTED_IMAGES_MAP, selectedImagesMap);
                startActivity(aupicCreatorIntent);

            } else {

                Intent resultIntent = new Intent();
                resultIntent.putExtra(IntentConstants.SELECTED_IMAGES_MAP, selectedImagesMap);
                setResult(Activity.RESULT_OK, resultIntent);

                finish();
            }
        }
    }


}
