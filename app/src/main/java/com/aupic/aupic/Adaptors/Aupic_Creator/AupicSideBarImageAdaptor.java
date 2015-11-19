package com.aupic.aupic.Adaptors.Aupic_Creator;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.aupic.aupic.Holder.Aupic_Creator.AupicSideBarViewHolder;
import com.aupic.aupic.Holder.Aupic_Creator.ChooseImagesViewHolder;
import com.aupic.aupic.Holder.Media.SelectedImagesDTO;
import com.aupic.aupic.R;

import java.util.List;

/**
 * Created by root on 5/11/15.
 */
public class AupicSideBarImageAdaptor extends ArrayAdapter<SelectedImagesDTO> {

    private Context context;
    private AupicSideBarViewHolder.SelectedSideBarImage selectedSideBarImage;
    private ChooseImagesViewHolder.ChooseImagesCallBack chooseImagesCallBack;

    public AupicSideBarImageAdaptor (Context context, int resourceId, List<SelectedImagesDTO>
                                     galleryPhotoAlbums, AupicSideBarViewHolder.SelectedSideBarImage
                                     selectedSideBarImage, ChooseImagesViewHolder.ChooseImagesCallBack
                                     chooseImagesCallBack) {

        super(context, resourceId, galleryPhotoAlbums);
        this.context = context;
        this.selectedSideBarImage = selectedSideBarImage;
        this.chooseImagesCallBack = chooseImagesCallBack;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        AupicSideBarViewHolder aupicSideBarViewHolder;
        final SelectedImagesDTO selectedImagesDTO = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if ( convertView == null) {

            convertView = mInflater.inflate(R.layout.gallery_items, null);
            aupicSideBarViewHolder = new AupicSideBarViewHolder(convertView, selectedSideBarImage,
                                                                            chooseImagesCallBack);

            convertView.setTag(aupicSideBarViewHolder);
        } else {

            aupicSideBarViewHolder = (AupicSideBarViewHolder) convertView.getTag();
        }

        aupicSideBarViewHolder.render(context, selectedImagesDTO);

        return convertView;
    }
}
