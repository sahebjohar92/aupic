package com.aupic.aupic.Activity.base;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aupic.aupic.Activity.aupic.AupicCreatorActivity;
import com.aupic.aupic.Adaptors.Aupic_Creator.AupicSideBarImageAdaptor;
import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Holder.Aupic_Creator.AupicSideBarViewHolder;
import com.aupic.aupic.Holder.Media.SelectedImagesDTO;
import com.aupic.aupic.ImageDragHelper.LinearLayoutListView;
import com.aupic.aupic.ImageDragHelper.PassObject;
import com.aupic.aupic.R;
import com.aupic.aupic.Storage.TransientDataRepo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by saheb on 20/10/15.
 */
public abstract class AupFragmentActivity extends ActionBarActivity {

    protected ActionBar actionBar;
    protected String titleText = "";
    protected AupicSideBarViewHolder.SelectedSideBarImage selectedSideBarImageListener;

    protected abstract int getContentViewId();

    protected abstract int getTitleText();

    public void setTitleText(String text) {
        titleText = text;

        actionBar.setTitle(text);
    }

    public AupFragmentActivity() {
    }

    protected void passChildContext(AupicCreatorActivity childContext) {

        selectedSideBarImageListener =  childContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.inject(this);

        AppBus.getInstance().register(this);
        actionBar = getSupportActionBar();
        titleText = getResources().getString(getTitleText());
        actionBar.setTitle(titleText);

        if (null != getActionbarTitle(this)) {
            getActionbarTitle(this).setEllipsize(TextUtils.TruncateAt.MARQUEE);
            getActionbarTitle(this).setSelected(true);
        }
    }

    public TextView getActionbarTitle(Activity activity) {
        return (TextView) activity.findViewById(R.id.action_bar_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Bitmap getImageFromImagePath(String imagePath, boolean isFirstImage) {

        Bitmap bitmap = null;
        try {

            File f = new File(imagePath);
            if (f.exists()) {

                Uri contentUri = Uri.fromFile(f);
                InputStream image_stream = getContentResolver().openInputStream(contentUri);
                bitmap = BitmapFactory.decodeStream(image_stream);

                if (!isFirstImage) {
                    bitmap = getResizeBitmap(bitmap, 125);
                } else {
                    bitmap = getResizeBitmap(bitmap, 400);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public Bitmap getResizeBitmap(Bitmap image, int maxSize) {

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @SuppressWarnings("unchecked")
    public LinkedHashMap<String, Integer> getImagesMap() {

        return (LinkedHashMap<String, Integer>) TransientDataRepo.getInstance().
                                                    getData(StringConstants.SELECTED_IMAGES);
    }

    public Integer getCount() {

        checkTransientRepoInstance();

        Integer count = (Integer) TransientDataRepo.getInstance().
                                            getData(StringConstants.SELECTED_IMAGE_COUNT);

        if ( null == count) {
            count = 0;
        }

        return count;
    }

    public void incrementCount() {

        checkTransientRepoInstance();

        Integer count = getCount();
        TransientDataRepo.getInstance().putData(StringConstants.SELECTED_IMAGE_COUNT, ++count);
    }

    public void decrementCount() {

        checkTransientRepoInstance();

        Integer count = getCount();
        TransientDataRepo.getInstance().putData(StringConstants.SELECTED_IMAGE_COUNT, --count);
    }

    @SuppressWarnings("unchecked")
    public  LinkedHashMap<String, Integer> removeImage(String imageName, LinkedHashMap<String, Integer> imageMap) {

        checkTransientRepoInstance();

        LinkedHashMap<String, Integer> tempMap = new LinkedHashMap<>();

        boolean isFound = false;

        for (Map.Entry<String, Integer> entry : imageMap.entrySet()) {

            if (entry.getKey().equals(imageName)) {

                isFound = true;
            } else {

                if (isFound) {
                    tempMap.put(entry.getKey(), entry.getValue() - 1);
                } else {
                    tempMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        imageMap = null;
        decrementCount();

        TransientDataRepo.getInstance()
                .putData(StringConstants.SELECTED_IMAGES, tempMap);

        return tempMap;
    }

    public Map<String, Integer> sortImagesMap(Map<String, Integer> imageMap) {

        Set<Map.Entry<String, Integer>> set = imageMap.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(set);

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        imageMap.clear();

        for(int i = 0;i < list.size(); i++) {

            Map.Entry<String, Integer> entry = list.get(i);

            imageMap.put(entry.getKey(), entry.getValue());
        }

        TransientDataRepo.getInstance()
                .putData(StringConstants.SELECTED_IMAGES, imageMap);

        return imageMap;
    }

    @SuppressWarnings("unchecked")
    protected Integer getPositionFromImageName(String selectedImageFromSideBar) {

        checkTransientRepoInstance();

        if ( null != selectedImageFromSideBar) {

            LinkedHashMap<String, Integer> map = (LinkedHashMap<String, Integer>) TransientDataRepo
                                             .getInstance().getData(StringConstants.SELECTED_IMAGES);

            return map.get(selectedImageFromSideBar);
        }
        return 0;
    }

    private void checkTransientRepoInstance() {

        TransientDataRepo instance = TransientDataRepo.getInstance();

        if (null == instance) {

            TransientDataRepo.init();
        }
    }

    protected AdapterView.OnItemLongClickListener myOnItemLongClickListener = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            SelectedImagesDTO selectedItem = (SelectedImagesDTO)(parent.getItemAtPosition(position));

            AupicSideBarImageAdaptor associatedAdapter = (AupicSideBarImageAdaptor)(parent.getAdapter());
            List<SelectedImagesDTO> associatedList = associatedAdapter.getList();

            PassObject passObj = new PassObject(view, selectedItem, associatedList);

            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, passObj, 0);

            return true;
        }

    };

    protected View.OnDragListener myOnDragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:

                    PassObject passObj = (PassObject)event.getLocalState();
                    View view = passObj.view;
                    SelectedImagesDTO passedItem = passObj.item;
                    List<SelectedImagesDTO> srcList = passObj.srcList;
                    ListView oldParent = (ListView)view.getParent();
                    AupicSideBarImageAdaptor srcAdapter = (AupicSideBarImageAdaptor)(oldParent.getAdapter());

                    LinearLayoutListView newParent = (LinearLayoutListView)v;
                    AupicSideBarImageAdaptor destAdapter = (AupicSideBarImageAdaptor)(newParent.listView.getAdapter());
                    List<SelectedImagesDTO> destList = destAdapter.getList();

                    if(removeItemToList(srcList, passedItem)){
                        addItemToList(destList, passedItem);
                    }

                    srcAdapter.notifyDataSetChanged();
                    destAdapter.notifyDataSetChanged();

                    newParent.listView.smoothScrollToPosition(destAdapter.getCount()-1);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }

            return true;
        }

    };

    protected AdapterView.OnItemClickListener listOnItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            SelectedImagesDTO selectedImagesDTO = (SelectedImagesDTO) (parent.getItemAtPosition(position));

            if (null != selectedImagesDTO.getImagePath()) {

                if (!selectedImagesDTO.getIsSelected()) {

                    selectedSideBarImageListener.getSelectedSideBarImage(selectedImagesDTO.getImagePath());
                }
            }
        }

    };

    private boolean removeItemToList(List<SelectedImagesDTO> l, SelectedImagesDTO it){
        boolean result = l.remove(it);
        return result;
    }

    private boolean addItemToList(List<SelectedImagesDTO> l, SelectedImagesDTO it){
        boolean result = l.add(it);
        return result;
    }

    protected void mediaScan(String filePath) {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
}



