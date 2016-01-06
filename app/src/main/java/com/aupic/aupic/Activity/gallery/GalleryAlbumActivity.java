package com.aupic.aupic.Activity.gallery;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.GridView;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Adaptors.Gallery.GalleryAlbumsAdaptor;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Holder.Gallery.GalleryAlbumsViewHolder;
import com.aupic.aupic.Holder.Gallery.GalleryPhotoAlbum;
import com.aupic.aupic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 4/11/15.
 */
public class GalleryAlbumActivity extends AupFragmentActivity implements GalleryAlbumsViewHolder
                                                                         .SelectedAlbumCallBack {

    private GalleryAlbumsAdaptor galleryAlbumsAdaptor;
    private boolean newActivity = true;

    @InjectView(R.id.phoneAlbumGrid)
    GridView phoneAlbumGrid;

    @Override
    protected int getTitleText() {

        return R.string.gallery_albums;
    }

    @Override
    protected int getContentViewId() {

        return R.layout.albums_grid_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBus.getInstance().register(this);

        ButterKnife.inject(this);

        if (null != getIntent() && null != getIntent().getExtras()) {

            newActivity = getIntent().getExtras().getBoolean(IntentConstants.NEW_ACTIVITY);
        }

        initialize();
    }

    @Override
    public void getSelectedAlbumCallBack(String albumName) {

        Intent galleryImagesIntent = new Intent(this, GalleryImagesActivity.class);

        galleryImagesIntent.putExtra(IntentConstants.ALBUM_NAME_INTENT_EXTRA, albumName);
        galleryImagesIntent.putExtra(IntentConstants.NEW_ACTIVITY, newActivity);

        startActivityForResult(galleryImagesIntent, Activity.RESULT_FIRST_USER);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            boolean isResult = data.getBooleanExtra(IntentConstants.SELECTED_IMAGES_MAP, false);

            Intent resultIntent = new Intent();
            resultIntent.putExtra(IntentConstants.SELECTED_IMAGES_MAP, isResult);
            setResult(Activity.RESULT_OK, resultIntent);

            finish();

        }
    }

    private void initialize() {

        List<GalleryPhotoAlbum> arrayListAlbums = new ArrayList<>();

        String[] PROJECTION_BUCKET = { MediaStore.Images.ImageColumns.BUCKET_ID,
                                       MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                                       MediaStore.Images.ImageColumns.DATE_TAKEN,
                                       MediaStore.Images.ImageColumns.DATA };



        String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";

        String BUCKET_ORDER_BY = "MAX(datetaken) DESC";


        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;



        Cursor cur = getContentResolver().query(images, PROJECTION_BUCKET,

                BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);

        GalleryPhotoAlbum album;

        if (cur.moveToFirst()) {

            String bucket;
            String date;
            String data;
            long bucketId;

            int bucketColumn = cur
                    .getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int dateColumn = cur
                    .getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);

            int dataColumn = cur
                    .getColumnIndex(MediaStore.Images.Media.DATA);

            int bucketIdColumn = cur
                    .getColumnIndex(MediaStore.Images.Media.BUCKET_ID);

            do {

                bucket = cur.getString(bucketColumn);
                date = cur.getString(dateColumn);
                data = cur.getString(dataColumn);
                bucketId = cur.getInt(bucketIdColumn);

                if (bucket != null && bucket.length() > 0) {

                    album = new GalleryPhotoAlbum();

                    GalleryPhotoAlbum firstAlbumImageDto = getImageFromGalleryName(bucket);

                    if (null != firstAlbumImageDto.getAlbumImage()) {

                        album.setBucketId(bucketId);
                        album.setBucketName(bucket);
                        album.setDateTaken(date);
                        album.setData(data);
                        album.setTotalCount(photoCountByAlbum(bucket));
                        album.setAlbumImage(firstAlbumImageDto.getAlbumImage());

                        arrayListAlbums.add(album);

                        Log.v("ListingImages", " bucket=" + bucket

                                + "  date_taken=" + date + "  _data=" + data

                                + " bucket_id=" + bucketId);
                    }

                }

            } while (cur.moveToNext());

            galleryAlbumsAdaptor = new GalleryAlbumsAdaptor(this, R.id.phoneAlbumGrid, arrayListAlbums,
                                                            newActivity, this);
            phoneAlbumGrid.setAdapter(galleryAlbumsAdaptor);
        }
        cur.close();
    }

    private int photoCountByAlbum(String bucketName) {

        try {

            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

            String searchParams = null;

            searchParams = "bucket_display_name = \"" + bucketName + "\"";

            Cursor mPhotoCursor = getContentResolver().query(

                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,

                    searchParams, null, orderBy + " DESC");

            if (mPhotoCursor.getCount() > 0) {

                return mPhotoCursor.getCount();

            }

            mPhotoCursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private GalleryPhotoAlbum getImageFromGalleryName(String bucketName) {

        int count;
        int id;
        int image_column_index;
        String imageName = null;
        Bitmap thumbNails = null;

        GalleryPhotoAlbum galleryPhotoAlbum = new GalleryPhotoAlbum();
        String searchParams = "bucket_display_name = \"" + bucketName + "\"";

        try {

            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;

            Cursor imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    columns, searchParams, null, orderBy + " DESC");

            image_column_index = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            count = imageCursor.getCount();

            if (count > 0) {
                imageCursor.moveToPosition(0);
                id = imageCursor.getInt(image_column_index);
                imageName = imageCursor.getString(image_column_index);
                thumbNails = MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(), id,
                        MediaStore.Images.Thumbnails.MINI_KIND, null);
            }
            imageCursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        galleryPhotoAlbum.setAlbumImage(thumbNails);
        galleryPhotoAlbum.setData(imageName);

        return galleryPhotoAlbum;
    }

}
