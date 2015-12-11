package com.aupic.aupic.Task.FFmpeg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;

import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Helper.VideoKit;
import com.aupic.aupic.Holder.FFmpeg.AudioMixingHolder;
import com.aupic.aupic.Holder.Media.MediaAudioDto;

import java.util.Objects;

/**
 * Created by saheb on 11/12/15.
 */
public class AudioMixingTask extends AsyncTask<Object, String, Boolean> {

    ProgressDialog progressDialog;
    private Context context;
    Boolean merged;
    PowerManager.WakeLock wakeLock;
    String mixedFile;
    String secondFile;
    String firstFile;
    String imagePath;
    MediaAudioDto mediaAudioDto;

    public AudioMixingTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Mixing Audios .......");
        progressDialog.show();

        PowerManager powerManager = (PowerManager)context.getSystemService(Activity.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "VK_LOCK");
        wakeLock.acquire();
    }

    @Override
    protected Boolean doInBackground(Object... parameters) {

        firstFile       = (String) parameters[0];
        secondFile      = (String) parameters[1];
        mixedFile       = (String) parameters[2];

        imagePath       = (String) parameters[3];
        mediaAudioDto = (MediaAudioDto) parameters[4];

        String[] command = {"ffmpeg","-y","-i",firstFile,"-i",secondFile,"-strict","experimental",
                            "-filter_complex","amix=inputs=2:duration=first:dropout_transition=3",
                            "-c:a","libmp3lame",mixedFile};

        try {

           merged = VideoKit.getInstance().process(command);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return merged;
    }

    @Override
    protected void onPostExecute(Boolean mixed) {

        progressDialog.dismiss();
        if (wakeLock.isHeld())
            wakeLock.release();

        AppBus.getInstance().post(new AudioMixingHolder(mixed, imagePath, mediaAudioDto, mixedFile));

    }
}
