package com.aupic.aupic.Holder.Gallery;

import java.io.Serializable;
import java.util.List;

/**
 * Created by saheb on 23/12/15.
 */
public class VideoGalleryListDTO implements Serializable{

    private List<VideoGalleryDTO> videoGalleryDTOList;

    public List<VideoGalleryDTO> getVideoGalleryDTOList() {
        return videoGalleryDTOList;
    }

    public void setVideoGalleryDTOList(List<VideoGalleryDTO> videoGalleryDTOList) {
        this.videoGalleryDTOList = videoGalleryDTOList;
    }
}
