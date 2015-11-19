package com.aupic.aupic.Adaptors.Media;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.aupic.aupic.Holder.Media.MediaAlbumsDTO;
import com.aupic.aupic.Holder.Media.MediaAlbumViewHolder;
import com.aupic.aupic.R;

import java.util.List;

/**
 * Created by saheb on 10/11/15.
 */
public class MediaAlbumAdaptor extends ArrayAdapter<MediaAlbumsDTO> {

    private Context context;

    public MediaAlbumAdaptor (Context context, int resourceId, List<MediaAlbumsDTO> mediaAlbums) {

        super(context, resourceId, mediaAlbums);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        MediaAlbumViewHolder mediaAlbumViewHolder;
        final MediaAlbumsDTO mediaAlbumsDTO = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if ( convertView == null) {

            convertView = mInflater.inflate(R.layout.albums, null);
            mediaAlbumViewHolder = new MediaAlbumViewHolder(convertView);

            convertView.setTag(mediaAlbumViewHolder);
        } else {

            mediaAlbumViewHolder = (MediaAlbumViewHolder) convertView.getTag();
        }

        mediaAlbumViewHolder.render(context, mediaAlbumsDTO);

        return convertView;
    }
}
