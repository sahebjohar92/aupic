package com.aupic.aupic.Activity.AupicDisplay;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Holder.Media.MediaAudioDto;
import com.aupic.aupic.R;
import com.aupic.aupic.Task.AupicCreation.CreateAupicTask;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 24/11/15.
 */
public class AupicDisplayActivity extends AupFragmentActivity {

    @InjectView(R.id.video_view)
    VideoView myVideoView;

    @Override
    protected int getTitleText() {

        return R.string.slide_show;
    }

    @Override
    protected int getContentViewId() {

        return R.layout.video_view;
    }

    private int position = 0;
    private MediaController mediaControls;
    private String filePath;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBus.getInstance().register(this);

        ButterKnife.inject(this);

        if ( null != getIntent()) {

            filePath = getIntent().getStringExtra(IntentConstants.VIDEO_FILE_PATH);

            initializeVideoPlayer();
        }
    }

    private void initializeVideoPlayer() {

        if (mediaControls == null) {
            mediaControls = new MediaController(AupicDisplayActivity.this);
        }

        try {
            //set the media controller in the VideoView
            myVideoView.setMediaController(mediaControls);

            //set the uri of the video to be played
            myVideoView.setVideoURI(Uri.parse(filePath));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        myVideoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                // close the progress bar and play the video
                //if we have a position on savedInstanceState, the video playback should start from here
                myVideoView.seekTo(position);
                if (position == 0) {
                    myVideoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    myVideoView.pause();
                }
            }
        });


    }
}
