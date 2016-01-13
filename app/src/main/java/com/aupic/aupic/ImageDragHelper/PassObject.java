package com.aupic.aupic.ImageDragHelper;

import android.view.View;

import com.aupic.aupic.Holder.Media.SelectedImagesDTO;

import java.util.List;

/**
 * Created by saheb on 12/1/16.
 */
public class PassObject {

    public View view;
    public SelectedImagesDTO item;
    public List<SelectedImagesDTO> srcList;

    public PassObject(View v, SelectedImagesDTO i, List<SelectedImagesDTO> s){
        view = v;
        item = i;
        srcList = s;
    }
}
