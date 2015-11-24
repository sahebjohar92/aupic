package com.aupic.aupic.Activity.AupicDisplay;

import android.os.Bundle;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Holder.Media.MediaAudioDto;
import com.aupic.aupic.R;
import com.aupic.aupic.Task.AupicCreation.CreateAupicTask;

import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by saheb on 24/11/15.
 */
public class AupicDisplayActivity extends AupFragmentActivity {

    private HashMap<String,MediaAudioDto> imageAudioMap = new HashMap<>();

    @Override
    protected int getTitleText() {

        return R.string.slide_show;
    }

    @Override
    protected int getContentViewId() {

        return R.layout.aupic_creator_layout;
    }

    static {

        System.loadLibrary("ffmpeg");

    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBus.getInstance().register(this);

        if ( null != getIntent()) {

            imageAudioMap = (HashMap<String, MediaAudioDto>) getIntent().
                    getSerializableExtra(IntentConstants.AUPIC_MAP);


            for (HashMap.Entry<String, MediaAudioDto> entry : imageAudioMap.entrySet()) {

                new CreateAupicTask
                        ().execute(entry.getKey(), entry.getValue());
            }
        }

        ButterKnife.inject(this);
    }
}
