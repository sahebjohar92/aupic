package com.aupic.aupic.Adaptors.Aupic_Creator;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.aupic.aupic.ImageDragHelper.ItemOnDragListener;
import com.aupic.aupic.Holder.Aupic_Creator.AupicSideBarViewHolder;
import com.aupic.aupic.Holder.Aupic_Creator.ChooseImagesViewHolder;
import com.aupic.aupic.Holder.Media.SelectedImagesDTO;
import com.aupic.aupic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saheb on 5/11/15.
 */
public class AupicSideBarImageAdaptor extends ArrayAdapter<SelectedImagesDTO> {

    private Context context;
    private ChooseImagesViewHolder.ChooseImagesCallBack chooseImagesCallBack;
    List<SelectedImagesDTO> imagesList = new ArrayList<>();
    View.OnDragListener myOnDragListener;
    ItemOnDragListener.ImageOrderDrag imageOrderDragListener;

    public AupicSideBarImageAdaptor (Context context, int resourceId, List<SelectedImagesDTO>
                                     galleryPhotoAlbums, ChooseImagesViewHolder.ChooseImagesCallBack
                                     chooseImagesCallBack,View.OnDragListener myOnDragListener,
                                     ItemOnDragListener.ImageOrderDrag imageOrderDrag) {

        super(context, resourceId, galleryPhotoAlbums);
        this.context = context;
        this.chooseImagesCallBack = chooseImagesCallBack;
        this.imagesList = galleryPhotoAlbums;
        this.myOnDragListener = myOnDragListener;
        this.imageOrderDragListener = imageOrderDrag;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        AupicSideBarViewHolder aupicSideBarViewHolder;
        final SelectedImagesDTO selectedImagesDTO = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if ( convertView == null) {

            convertView = mInflater.inflate(R.layout.gallery_items, null);
            aupicSideBarViewHolder = new AupicSideBarViewHolder(convertView, chooseImagesCallBack);

            convertView.setTag(aupicSideBarViewHolder);
        } else {

            aupicSideBarViewHolder = (AupicSideBarViewHolder) convertView.getTag();
        }

        aupicSideBarViewHolder.render(context, selectedImagesDTO);

        convertView.setOnDragListener(new ItemOnDragListener(imagesList.get(position), imageOrderDragListener));

        return convertView;
    }

    public List<SelectedImagesDTO> getList(){
        return imagesList;
    }
}
