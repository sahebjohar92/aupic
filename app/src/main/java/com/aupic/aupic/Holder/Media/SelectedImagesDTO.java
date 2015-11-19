package com.aupic.aupic.Holder.Media;

import android.graphics.Bitmap;

import butterknife.InjectView;

/**
 * Created by saheb on 5/11/15.
 */
public class SelectedImagesDTO {

    private String imagePath;
    private Bitmap image;
    private String audioPath;
    private Integer audioDuration;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public Integer getAudioDuration() {
        return audioDuration;
    }

    public void setAudioDuration(Integer audioDuration) {
        this.audioDuration = audioDuration;
    }
}
