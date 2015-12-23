package com.aupic.aupic.Holder.Gallery;

import java.io.Serializable;

/**
 * Created by saheb on 23/12/15.
 */
public class VideoGalleryDTO implements Serializable{

    public String filePath;
    public String mimeType;
    public String thumbPath;
    public String title;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
