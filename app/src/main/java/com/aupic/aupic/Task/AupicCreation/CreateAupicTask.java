package com.aupic.aupic.Task.AupicCreation;
import android.os.AsyncTask;

import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.Helper.GenerateVideo;
import com.aupic.aupic.Holder.Media.MediaAudioDto;

/**
 * Created by saheb on 24/11/15.
 */
public class CreateAupicTask extends AsyncTask<Object, String, String> {

    private GenerateVideo generateVideo = new GenerateVideo();

    @Override
    protected String doInBackground(Object... parameters) {

        String imagePath = (String) parameters[0];
        String audioPath = (String) parameters[1];

        createAudio(imagePath, audioPath);

        return null;
    }


    @Override
    protected void onPostExecute(String videoFile) {

    }

    private void createAudio(String imagePath, String audioPath) {

        generateVideo.createVideoFromImageAndAudio(imagePath, audioPath);
    }
}
