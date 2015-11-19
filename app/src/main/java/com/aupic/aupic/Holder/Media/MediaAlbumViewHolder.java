package com.aupic.aupic.Holder.Media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 10/11/15.
 */
public class MediaAlbumViewHolder {

    @InjectView(R.id.album_image)
    ImageView albumImage;

    @InjectView(R.id.album_name)
    TextView albumName;

    @InjectView(R.id.album_images_count)
    TextView albumImagesCount;

    @InjectView(R.id.album_layout)
    FrameLayout albumLayout;

    public MediaAlbumViewHolder(View view) {

        ButterKnife.inject(this, view);
    }

    public void render(final Context context, final MediaAlbumsDTO mediaAlbumsDTO) {

        try {


            if (null != mediaAlbumsDTO) {

                albumName.setText(mediaAlbumsDTO.getAlbumName());

                if (null != mediaAlbumsDTO.getAlbumArtPath()) {
                    Bitmap albumArt = getImageFromImagePath(mediaAlbumsDTO.getAlbumArtPath(),
                                                            context);
                    albumImage.setImageBitmap(albumArt);
                } else {
                    albumImage.setImageResource(R.drawable.photo_unavailable);
                }

                albumImage.setHorizontalFadingEdgeEnabled(true);
                albumImage.setVerticalFadingEdgeEnabled(true);
                albumImage.setFadingEdgeLength(StringConstants.FADE_LENGTH);

                albumLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, mediaAlbumsDTO.getAlbumName() + " album clicked",
                                       Toast.LENGTH_SHORT).show();
//                        Intent galleryImagesIntent = new Intent(context, GalleryImagesActivity.class);
//                        galleryImagesIntent.putExtra(IntentConstants.ALBUM_NAME_INTENT_EXTRA,
//                                galleryPhotoAlbum.getBucketName());
//                        context.startActivity(galleryImagesIntent);
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImageFromImagePath(String imagePath, Context context) {

        Bitmap bitmap = null;
        try {

            File f = new File(imagePath);
            if (f.exists()) {

                Uri contentUri = Uri.fromFile(f);
                InputStream image_stream = context.getContentResolver().openInputStream(contentUri);
                bitmap = BitmapFactory.decodeStream(image_stream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
