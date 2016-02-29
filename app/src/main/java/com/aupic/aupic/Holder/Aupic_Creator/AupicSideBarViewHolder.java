package com.aupic.aupic.Holder.Aupic_Creator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aupic.aupic.Holder.Media.RecordAudioViewHolder;
import com.aupic.aupic.Holder.Media.SelectedImagesDTO;
import com.aupic.aupic.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 5/11/15.
 */
public class AupicSideBarViewHolder {

    @InjectView(R.id.gallery_image_layout)
    FrameLayout galleryImageLayout;

    @InjectView(R.id.thumbImage)
    com.aupic.aupic.Graphics.SquareImageWithoutFade thumbImage;

    @InjectView(R.id.select_box)
    ImageView selectBox;

    @InjectView(R.id.selected_text)
    TextView selectedText;

    @InjectView(R.id.progressSideBar)
    ProgressBar loader;

    private ChooseImagesViewHolder.ChooseImagesCallBack chooseImagesCallBack;

    public interface SelectedSideBarImage {

        public void getSelectedSideBarImage(String selectedSideBarImage);
    }

    public AupicSideBarViewHolder(View view,
                                  ChooseImagesViewHolder.ChooseImagesCallBack chooseImagesCallBack) {

        ButterKnife.inject(this, view);
        this.chooseImagesCallBack = chooseImagesCallBack;
    }

    public void render(final Context context, final SelectedImagesDTO selectedImagesDTO) {

        if ( null != selectedImagesDTO.getImagePath()) {

            Uri uri = Uri.fromFile(new File(selectedImagesDTO.getImagePath()));
            Picasso.with(context)
                    .load(uri)
                    .placeholder(null)
                    .resize(130, 150)
                    .into(thumbImage);

            selectBox.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);

            if (null != selectedImagesDTO.getAudioPath() && !selectedImagesDTO.getAudioPath()
                    .isEmpty() && !selectedImagesDTO.getIsSelected()) {

                selectBox.setVisibility(View.VISIBLE);

                if (null != selectedImagesDTO.getVideoProgressDone() && selectedImagesDTO.
                                                                        getVideoProgressDone()) {
                    selectBox.setImageResource(R.drawable.green_tick);
                } else {

                    if (null != selectedImagesDTO.getVideoInProgress() && selectedImagesDTO.
                                                                          getVideoInProgress()) {
                        loader.setVisibility(View.VISIBLE);
                    }
                    selectBox.setImageResource(R.drawable.red_tick);
                }
            }

            if (!selectedImagesDTO.getIsSelected()) {
               selectedText.setVisibility(View.GONE);
            } else {
                loader.setVisibility(View.GONE);
                selectedText.setVisibility(View.VISIBLE);
            }

        } else {

            thumbImage.setImageResource(R.drawable.add);
            selectBox.setVisibility(View.GONE);
            selectedText.setVisibility(View.GONE);

            galleryImageLayout.setBackground(null);
            thumbImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showAddImagesAlertBox(context, chooseImagesCallBack);
                }
            });
        }
    }

    private void showAddImagesAlertBox(Context context, ChooseImagesViewHolder.ChooseImagesCallBack
                                                                            chooseImagesCallBack) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.
                LAYOUT_INFLATER_SERVICE);
        View convertView = mInflater.inflate(R.layout.add_images_dialog_box, null);

        ChooseImagesViewHolder chooseImagesViewHolder = new ChooseImagesViewHolder(convertView,
                                                                                chooseImagesCallBack);

        chooseImagesViewHolder.render(context, alertDialog);

        alertDialog.setView(convertView);
        alertDialog.show();
    }
}
