package com.aupic.aupic.Task.FFmpeg;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;

import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Helper.GenerateFileNames;
import com.aupic.aupic.Helper.VideoKitInvoke;
import com.aupic.aupic.Holder.FFmpeg.ImageVideoHolder;

/**
 * Created by saheb on 28/2/16.
 */
public class MergeSingleAudioVideo extends AsyncTask<Object, String, Boolean> {

    GenerateFileNames generateFileNames = new GenerateFileNames();
    private Context context;
    Boolean created;
    PowerManager.WakeLock wakeLock;
    String videoFile;
    String imageFile;
    String audioFile;

    public MergeSingleAudioVideo(Context context) {
        this.context = context;
    }


    @Override
    protected Boolean doInBackground(Object... parameters) {

        audioFile       = (String) parameters[1];
        imageFile       = (String) parameters[0];
        videoFile       = generateFileNames.getVideoFileName();

        String[] command = {"ffmpeg","-y","-loop","1","-i",imageFile,"-i",audioFile,
                            "-strict","experimental","-r","25","-aspect","16:9",
                            "-ab","48000","-ac","2","-b:v","1024000","-ar","22050","-vcodec","mpeg4",
                            "-shortest",videoFile};

        try {

            created = VideoKitInvoke.getInstance().process(command, context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return created;
    }

    @Override
    protected void onPostExecute(Boolean mixed) {

        AppBus.getInstance().post(new ImageVideoHolder(imageFile, videoFile, created));

    }
}
