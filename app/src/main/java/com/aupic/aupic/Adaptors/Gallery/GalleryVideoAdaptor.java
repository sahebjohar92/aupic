package com.aupic.aupic.Adaptors.Gallery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.aupic.aupic.Holder.Gallery.GalleryVideoViewHolder;
import com.aupic.aupic.Holder.Gallery.VideoGalleryDTO;
import com.aupic.aupic.Holder.Gallery.VideoGalleryListDTO;
import com.aupic.aupic.R;

/**
 * Created by saheb on 23/12/15.
 */
public class GalleryVideoAdaptor extends ArrayAdapter<VideoGalleryDTO> {

    private Context context;
    private int count;

    public GalleryVideoAdaptor(Context context, int resourceId,
                               VideoGalleryListDTO videoGalleryListDTO) {

        super(context, resourceId, videoGalleryListDTO.getVideoGalleryDTOList());
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        GalleryVideoViewHolder galleryVideoViewHolder;
        final VideoGalleryDTO videoGalleryDTO = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if ( convertView == null) {

            convertView = mInflater.inflate(R.layout.video_list_layout, null);
            galleryVideoViewHolder = new GalleryVideoViewHolder(convertView, context);

            convertView.setTag(galleryVideoViewHolder);
        } else {

            galleryVideoViewHolder = (GalleryVideoViewHolder) convertView.getTag();
        }

        galleryVideoViewHolder.render(videoGalleryDTO);

        return convertView;
    }
}
