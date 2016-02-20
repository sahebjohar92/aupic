package com.aupic.aupic.Helper;

import android.os.Environment;
import android.util.Log;

import com.aupic.aupic.Constant.StringConstants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by saheb on 11/12/15.
 */
public class GenerateFileNames {


    public String getRecorderAudioFileName() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "Audio_" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(StringConstants.DIRECTORY +
                StringConstants.AUDIO);

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.d("Audio Directory Name", "Oops! Failed create "
                        + storageDir + " directory");
                return null;
            }
        }

        return storageDir + "/" + audioFileName + StringConstants.AUDIO_EXTENSION;
    }

    public String getVideoFileName() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "Video_" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(StringConstants.DIRECTORY +
                "/" +StringConstants.VIDEO);

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.d("Audio Directory Name", "Oops! Failed create "
                        + storageDir + " directory");
                return null;
            }
        }

        return storageDir + "/" + audioFileName + StringConstants.VIDEO_EXTENSION;
    }

    public String getVideoFileNameMpg() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "Video_" + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(StringConstants.DIRECTORY +
                StringConstants.AUDIO);

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.d("Audio Directory Name", "Oops! Failed create "
                        + storageDir + " directory");
                return null;
            }
        }

        return storageDir + "/" + audioFileName + ".ts";
    }
}
