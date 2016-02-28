package com.aupic.aupic.Holder.FFmpeg;

/**
 * Created by saheb on 28/2/16.
 */
public class ImageVideoHolder {

    private String imagePath;
    private String videoPath;
    private Boolean created;

    public ImageVideoHolder(String imagePath, String videoPath, Boolean created) {
        this.imagePath = imagePath;
        this.videoPath = videoPath;
        this.created   = created;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Boolean getCreated() {
        return created;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }
}
