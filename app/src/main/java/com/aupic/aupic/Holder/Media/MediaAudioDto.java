package com.aupic.aupic.Holder.Media;

import java.io.Serializable;

/**
 * Created by saheb on 10/11/15.
 */
public class MediaAudioDto implements Serializable{

    private int audioId;
    private String title;
    private String albumName;
    private String data;
    private String displayName;
    private Integer duration;
    private boolean isInVidProgress;
    private String videoPath;
    private boolean vidProgressDone = false;

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public boolean getIsInVidProgress() {
        return isInVidProgress;
    }

    public void setInVidProgress(boolean isInVidProgress) {
        this.isInVidProgress = isInVidProgress;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public boolean getIsVidProgressDone() {
        return vidProgressDone;
    }

    public void setVidProgressDone(boolean vidProgressDone) {
        this.vidProgressDone = vidProgressDone;
    }
}
