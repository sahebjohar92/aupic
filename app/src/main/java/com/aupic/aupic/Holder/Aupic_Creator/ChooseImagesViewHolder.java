package com.aupic.aupic.Holder.Aupic_Creator;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 15/11/15.
 */
public class ChooseImagesViewHolder {

    @InjectView(R.id.ll_choose_camera)
    LinearLayout chooseCamera;

    @InjectView(R.id.ll_choose_gallery)
    LinearLayout chooseGallery;

    private ChooseImagesCallBack listener;

    public interface ChooseImagesCallBack {

        public void getChosenImagesCallBack(int type,AlertDialog alertDialog);
    }

    public ChooseImagesViewHolder(View view, ChooseImagesCallBack listener) {

        ButterKnife.inject(this, view);
        this.listener = listener;
    }

    public void render(Context context, final AlertDialog alertDialog) {

        chooseCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.getChosenImagesCallBack(StringConstants.CAMERA_CHOSEN, alertDialog);
            }
        });

        chooseGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.getChosenImagesCallBack(StringConstants.GALLERY_CHOSEN, alertDialog);
            }
        });
    }
}
