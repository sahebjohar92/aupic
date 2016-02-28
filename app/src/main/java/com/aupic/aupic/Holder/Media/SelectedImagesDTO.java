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
    private boolean isSelected;
    private Boolean videoInProgress;
    private Boolean videoProgressDone;
    private String videoPath;

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

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Boolean getVideoInProgress() {
        return videoInProgress;
    }

    public void setVideoInProgress(Boolean videoInProgress) {
        this.videoInProgress = videoInProgress;
    }

    public Boolean getVideoProgressDone() {
        return videoProgressDone;
    }

    public void setVideoProgressDone(Boolean videoProgressDone) {
        this.videoProgressDone = videoProgressDone;
    }
}
