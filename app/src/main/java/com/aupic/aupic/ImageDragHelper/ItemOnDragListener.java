package com.aupic.aupic.ImageDragHelper;

import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.aupic.aupic.Adaptors.Aupic_Creator.AupicSideBarImageAdaptor;
import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.Holder.Media.SelectedImagesDTO;
import com.aupic.aupic.Storage.TransientDataRepo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by saheb on 12/1/16.
 */
public class ItemOnDragListener implements View.OnDragListener{

    SelectedImagesDTO selectedImagesDTO;
    ImageOrderDrag imageOrderDragListener;
    String selectedImagePath;

    public ItemOnDragListener(SelectedImagesDTO i, ImageOrderDrag imageOrderDragListener){
        this.selectedImagesDTO = i;
        this.imageOrderDragListener = imageOrderDragListener;
    }

    public interface ImageOrderDrag {

        public void imageOrderDragged(String selectedImagePath);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        PassObject passObj;
        View view;
        SelectedImagesDTO passedItem;
        List<SelectedImagesDTO> srcList;
        ListView oldParent;
        Integer itemPos;

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundColor(0x30000000);

                passObj = (PassObject)event.getLocalState();
                view = passObj.view;
                oldParent = (ListView)view.getParent();

                Integer visibleListHeight = oldParent.getHeight();
                Integer itemHeight = v.getHeight();
                Integer totalHeightListView = getTotalHeightOfListView(oldParent, itemHeight);
                Float position = v.getY();

                itemPos = position.intValue();

                if (itemPos + itemHeight + 50  > visibleListHeight) {

                    if (visibleListHeight < totalHeightListView) {

                        oldParent.smoothScrollToPosition(oldParent.getAdapter().getCount());
                    }
                }

                Log.v("Total Height of list view ", totalHeightListView.toString());
                Log.v("View height", itemHeight.toString());
                Log.v("Dragged to position ", position.toString());

                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:

                passObj = (PassObject)event.getLocalState();
                view = passObj.view;
                passedItem = passObj.item;
                srcList = passObj.srcList;
                oldParent = (ListView)view.getParent();
                AupicSideBarImageAdaptor srcAdapter = (AupicSideBarImageAdaptor)(oldParent.getAdapter());

                ListView newParent = (ListView)v.getParent();
                AupicSideBarImageAdaptor destAdapter = (AupicSideBarImageAdaptor)(newParent.getAdapter());
                List<SelectedImagesDTO> destList = destAdapter.getList();

                int removeLocation = srcList.indexOf(passedItem);
                int insertLocation = destList.indexOf(selectedImagesDTO);

                if(srcList != destList || removeLocation != insertLocation){
                    if(removeItemToList(srcList, passedItem)){
                        destList.add(insertLocation, passedItem);
                    }

                    srcAdapter.notifyDataSetChanged();
                    destAdapter.notifyDataSetChanged();

                    List<SelectedImagesDTO> list = destAdapter.getList();
                    changeImageOrdering(list);

                    imageOrderDragListener.imageOrderDragged(selectedImagePath);
                }

                break;
            case DragEvent.ACTION_DRAG_ENDED:
            default:
                break;
        }

        return true;
    }

    private boolean removeItemToList(List<SelectedImagesDTO> l, SelectedImagesDTO it){
        boolean result = l.remove(it);
        return result;
    }

    private void changeImageOrdering(List<SelectedImagesDTO> list) {

        Integer count = 0;
        LinkedHashMap<String, Integer> imageMap = new LinkedHashMap<>();

        for (SelectedImagesDTO selectedImagesDTO : list) {

            if ( null != selectedImagesDTO.getImagePath()) {

                if (selectedImagesDTO.getIsSelected()) {
                    selectedImagePath = selectedImagesDTO.getImagePath();
                }

                imageMap.put(selectedImagesDTO.getImagePath(), ++count);
            }
        }

        TransientDataRepo.getInstance().putData(StringConstants.SELECTED_IMAGES, imageMap);
    }

    public int getTotalHeightOfListView(ListView listView, int height) {

        ListAdapter mAdapter = listView.getAdapter();

        int totalHeight = 0;
        int count = mAdapter.getCount();

        for (int i = 0; i < count; i++) {

            totalHeight += height;
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));

        }

        totalHeight = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));

        return totalHeight;

    }

}
