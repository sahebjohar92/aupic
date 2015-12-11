package com.aupic.aupic.Holder.FFmpeg;

import com.aupic.aupic.Holder.Media.MediaAudioDto;

/**
 * Created by saheb on 11/12/15.
 */
public class AudioMixingHolder {

    private Boolean isMixed;
    private MediaAudioDto mediaAudioDto;
    private String imagePath;
    private String mixedFile;

    public AudioMixingHolder(Boolean isMixed, String imagePath, MediaAudioDto mediaAudioDto,
                             String mixedFile) {

        this.isMixed        = isMixed;
        this.imagePath      = imagePath;
        this.mixedFile      = mixedFile;
        this.mediaAudioDto  = mediaAudioDto;
    }

    public Boolean getIsMixed() {
        return isMixed;
    }

    public void setIsMixed(Boolean isMixed) {
        this.isMixed = isMixed;
    }

    public MediaAudioDto getMediaAudioDto() {
        return mediaAudioDto;
    }

    public void setMediaAudioDto(MediaAudioDto mediaAudioDto) {
        this.mediaAudioDto = mediaAudioDto;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMixedFile() {
        return mixedFile;
    }

    public void setMixedFile(String mixedFile) {
        this.mixedFile = mixedFile;
    }
}
