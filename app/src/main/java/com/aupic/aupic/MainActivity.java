package com.aupic.aupic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Activity.camera.CameraActivityNew;
import com.aupic.aupic.Activity.gallery.GalleryAlbumActivity;
import com.aupic.aupic.Event.AppBus;

import butterknife.InjectView;


public class MainActivity extends AupFragmentActivity {


    @InjectView(R.id.gallery_aupic)
    LinearLayout phone_gallery;

    @InjectView(R.id.camera)
    LinearLayout camera;

    @InjectView(R.id.view_aupic)
    LinearLayout aup_gallery;

    @InjectView(R.id.create_aupic)
    LinearLayout create_aupic;

    @Override
    protected int getTitleText() {
        return R.string.app_name;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBus.getInstance().register(this);

        initialize();
    }


    public void initialize() {

        final Activity activity = this;

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(activity, CameraActivityNew.class);
                startActivity(cameraIntent);
            }
        });

        phone_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(activity, GalleryAlbumActivity.class);
                startActivity(galleryIntent);
            }
        });

        aup_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        create_aupic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone_gallery.postDelayed(new Runnable() {
                    public void run() {
                        phone_gallery.setVisibility(View.VISIBLE);
                    }
                }, 700);

                camera.postDelayed(new Runnable() {
                    public void run() {
                        camera.setVisibility(View.VISIBLE);
                    }
                }, 350);
            }
        });


    }
}
