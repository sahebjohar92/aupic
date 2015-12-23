package com.aupic.aupic.Holder.Gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aupic.aupic.Activity.AupicDisplay.AupicDisplayActivity;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 23/12/15.
 */
public class GalleryVideoViewHolder {

    @InjectView(R.id.video_thumb)
    ImageView albumImage;

    @InjectView(R.id.video_title)
    TextView albumName;

    @InjectView(R.id.video_layout)
    LinearLayout videoLayout;

    private Context context;

    public GalleryVideoViewHolder (View view, Context context) {

        ButterKnife.inject(this, view);
        this.context = context;
    }

    public void render(final VideoGalleryDTO videoGalleryDTO) {


        if ( null != videoGalleryDTO) {

            Bitmap videoThumbNail = getVideoThumbNail(videoGalleryDTO.filePath);
            albumImage.setImageBitmap(videoThumbNail);

            albumName.setText(videoGalleryDTO.title);

            albumName.setEllipsize(TextUtils.TruncateAt.END);
            albumName.setLines(2);

            videoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent videoViewIntent = new Intent(context, AupicDisplayActivity.class);
                    videoViewIntent.putExtra(IntentConstants.VIDEO_FILE_PATH, videoGalleryDTO.filePath);
                    context.startActivity(videoViewIntent);
                }
            });
        }
    }

    public Bitmap getVideoThumbNail(String fileName) {

        return ThumbnailUtils.createVideoThumbnail(fileName, MediaStore.Video.Thumbnails.MINI_KIND);
    }
}
