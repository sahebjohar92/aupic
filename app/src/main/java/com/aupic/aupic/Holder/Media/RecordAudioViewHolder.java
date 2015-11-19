package com.aupic.aupic.Holder.Media;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.aupic.aupic.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 14/11/15.
 */
public class RecordAudioViewHolder {

    @InjectView(R.id.btn_save_audio)
    Button saveAudio;

    @InjectView(R.id.btn_cancel_audio)
    Button cancelAudio;

    @InjectView(R.id.chronometer)
    Chronometer chronometer;

    private audioRecorderCallBack listener;

    public RecordAudioViewHolder(View view, audioRecorderCallBack listener) {

        ButterKnife.inject(this, view);
        this.listener = listener;
    }

    public interface audioRecorderCallBack {

        public void saveAudioCallBack(AlertDialog alertDialog);

        public void cancelAudioCallBack(AlertDialog alertDialog);
    }

    public void render(Context context, final AlertDialog alertDialog) {

        chronometer.start();

        saveAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                listener.saveAudioCallBack(alertDialog);
            }
        });

        cancelAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                listener.cancelAudioCallBack(alertDialog);
            }
        });
    }
}
