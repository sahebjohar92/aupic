package com.aupic.aupic.Holder.Gallery;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aupic.aupic.Activity.gallery.GalleryImagesActivity;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 4/11/15.
 */
public class GalleryAlbumsViewHolder {

    @InjectView(R.id.album_image)
    ImageView albumImage;

    @InjectView(R.id.album_name)
    TextView albumName;

    @InjectView(R.id.album_images_count)
    TextView albumImagesCount;

    @InjectView(R.id.album_layout)
    FrameLayout albumLayout;

    private boolean newActivity;
    private SelectedAlbumCallBack listener;

    public interface SelectedAlbumCallBack {

        public void getSelectedAlbumCallBack(String albumName);
    }

    public GalleryAlbumsViewHolder(View view, boolean newActivity, SelectedAlbumCallBack
                                                                    selectedAlbumCallBack) {

        ButterKnife.inject(this, view);
        this.newActivity = newActivity;
        this.listener = selectedAlbumCallBack;
    }

    public void render(final Context context, final GalleryPhotoAlbum galleryPhotoAlbum) {

        try {


            if (null != galleryPhotoAlbum ) {

                albumName.setText(galleryPhotoAlbum.getBucketName());

                albumImagesCount.setText(String.valueOf(galleryPhotoAlbum.getTotalCount()));

                albumImage.setImageBitmap(galleryPhotoAlbum.getAlbumImage());
                albumImage.setHorizontalFadingEdgeEnabled(true);
                albumImage.setVerticalFadingEdgeEnabled(true);
                albumImage.setFadingEdgeLength(StringConstants.FADE_LENGTH);

                albumLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (newActivity) {

                            Intent galleryImagesIntent = new Intent(context, GalleryImagesActivity.class);
                            galleryImagesIntent.putExtra(IntentConstants.ALBUM_NAME_INTENT_EXTRA,
                                    galleryPhotoAlbum.getBucketName());
                            galleryImagesIntent.putExtra(IntentConstants.NEW_ACTIVITY, newActivity);
                            context.startActivity(galleryImagesIntent);
                        } else {
                            listener.getSelectedAlbumCallBack(galleryPhotoAlbum.getBucketName());
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
