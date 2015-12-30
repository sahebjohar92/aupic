package com.aupic.aupic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Activity.camera.CameraActivityNew;
import com.aupic.aupic.Activity.gallery.GalleryAlbumActivity;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Activity.gallery.GalleryVideoActivity;
import com.aupic.aupic.Helper.ImageCacheHelper;
import com.aupic.aupic.Holder.Gallery.VideoGalleryListDTO;
import com.aupic.aupic.Storage.TransientDataRepo;
import com.aupic.aupic.Task.AupicGallery.GetAupicGalleryTask;
import com.squareup.otto.Subscribe;

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

    @InjectView(R.id.child_create_aupic)
    LinearLayout child_create_aupic;

    private boolean isChildVisible                  = false;
    private Integer videoGallerySize                = null;
    private VideoGalleryListDTO videoGalleryListDTO = new VideoGalleryListDTO();

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



        new GetAupicGalleryTask().execute(this);

        initialize();
    }

    @Subscribe
    public void onAsyncTaskResult(VideoGalleryListDTO videoGalleryDTOList) {

        this.videoGalleryListDTO = videoGalleryDTOList;
        videoGallerySize         = videoGalleryDTOList.getVideoGalleryDTOList().size();
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

                if (videoGallerySize == 0) {

                    Toast.makeText(activity, "No Aupic available", Toast.LENGTH_LONG).show();

                } else {

                    Intent videoIntent = new Intent(activity, GalleryVideoActivity.class);
                    videoIntent.putExtra(IntentConstants.AUPIC_MAP, videoGalleryListDTO);
                    startActivity(videoIntent);
                }
            }
        });

        create_aupic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                child_create_aupic.setVisibility(View.VISIBLE);

                if (!isChildVisible) {

                    Animation animation = AnimationUtils.loadAnimation(activity, R.anim.move);
                    create_aupic.startAnimation(animation);

                    Animation animation1 = AnimationUtils.loadAnimation(activity, R.anim.slide);
                    child_create_aupic.startAnimation(animation1);

                    isChildVisible = true;
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageCacheHelper.getInstance().clearCache();
        AppBus.getInstance().unregister(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        TransientDataRepo.getInstance().clearAll();
    }
}
