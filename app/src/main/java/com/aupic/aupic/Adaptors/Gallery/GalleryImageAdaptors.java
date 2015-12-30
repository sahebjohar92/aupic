package com.aupic.aupic.Adaptors.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aupic.aupic.Holder.Gallery.GalleryImageViewHolder;
import com.aupic.aupic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by saheb on 4/11/15.
 */
public class GalleryImageAdaptors extends BaseAdapter {

    private Context context;
    private int count;
    boolean[] thumbnailSelection;
    Bitmap[] thumbNails;
    String[] arrImagesPath;
    private Set<String> selectedImagesList = new HashSet<>();
    GalleryImageViewHolder.SelectedImagesMap selectedImagesMapListener;

    public GalleryImageAdaptors(Context context, int count, boolean[] thumbnailSelection,
                                Bitmap[] thumbNails, String[] arrImagesPath,
                                Set<String> selectedImagesList,
                                GalleryImageViewHolder.SelectedImagesMap listener) {

        this.context                   = context;
        this.count                     = count;
        this.thumbNails                = thumbNails;
        this.thumbnailSelection        = thumbnailSelection;
        this.arrImagesPath             = arrImagesPath;
        this.selectedImagesList         = selectedImagesList;
        this.selectedImagesMapListener = listener;
    }

    public int getCount() {
        return count;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        GalleryImageViewHolder galleryImageViewHolder;
        LayoutInflater mInflater = (LayoutInflater) context.
                                   getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.gallery_items, null);
            galleryImageViewHolder = new GalleryImageViewHolder(convertView, selectedImagesList,
                                                                selectedImagesMapListener);
            convertView.setTag(galleryImageViewHolder);

        } else {

            galleryImageViewHolder = (GalleryImageViewHolder) convertView.getTag();
        }

        galleryImageViewHolder.render(context, position, thumbnailSelection, thumbNails,
                                      arrImagesPath);

        return convertView;
    }

}
