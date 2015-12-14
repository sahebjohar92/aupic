package com.aupic.aupic.Adaptors.Gallery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.aupic.aupic.Holder.Gallery.GalleryAlbumsViewHolder;
import com.aupic.aupic.Holder.Gallery.GalleryPhotoAlbum;
import com.aupic.aupic.R;

import java.util.List;

/**
 * Created by saheb on 4/11/15.
 */
public class GalleryAlbumsAdaptor extends ArrayAdapter<GalleryPhotoAlbum> {

    private Context context;
    private boolean newActivity;
    private GalleryAlbumsViewHolder.SelectedAlbumCallBack selectedAlbumCallBack;

    public GalleryAlbumsAdaptor (Context context, int resourceId, List<GalleryPhotoAlbum> galleryPhotoAlbums,
                                 boolean newActivity,GalleryAlbumsViewHolder.SelectedAlbumCallBack
                                 selectedAlbumCallBack) {

        super(context, resourceId, galleryPhotoAlbums);
        this.context = context;
        this.newActivity = newActivity;
        this.selectedAlbumCallBack = selectedAlbumCallBack;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        GalleryAlbumsViewHolder galleryAlbumsViewHolder;
        final GalleryPhotoAlbum galleryPhotoAlbum = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                                   .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if ( convertView == null) {

            convertView = mInflater.inflate(R.layout.albums_new, null);
            galleryAlbumsViewHolder = new GalleryAlbumsViewHolder(convertView, newActivity,
                                                                  selectedAlbumCallBack);

            convertView.setTag(galleryAlbumsViewHolder);
        } else {

            galleryAlbumsViewHolder = (GalleryAlbumsViewHolder) convertView.getTag();
        }

        galleryAlbumsViewHolder.render(context, galleryPhotoAlbum);

        return convertView;
    }
}
