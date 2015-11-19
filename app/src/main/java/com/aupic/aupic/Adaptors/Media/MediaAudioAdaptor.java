package com.aupic.aupic.Adaptors.Media;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.aupic.aupic.Holder.Media.MediaAudioDto;
import com.aupic.aupic.Holder.Media.MediaAudioViewHolder;
import com.aupic.aupic.R;

import java.util.List;

/**
 * Created by saheb on 10/11/15.
 */
public class MediaAudioAdaptor extends ArrayAdapter<MediaAudioDto> implements
                                                MediaAudioViewHolder.getCheckedPositionFromCheckBox {

    private Context context;
    private int checkedPosition = -1;
    private int previousPosition = -1;
    private MediaAudioViewHolder.getCheckedMediaFromCheckBox listener;

    public MediaAudioAdaptor (Context context, int resourceId, List<MediaAudioDto> mediaAudio,
                              MediaAudioViewHolder.getCheckedMediaFromCheckBox listener) {

        super(context, resourceId, mediaAudio);
        this.context = context;
        this.listener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        MediaAudioViewHolder mediaAudioViewHolder;
        final MediaAudioDto mediaAudioDto = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if ( convertView == null) {

            convertView = mInflater.inflate(R.layout.media_items, null);
            mediaAudioViewHolder = new MediaAudioViewHolder(convertView, this, listener);

            convertView.setTag(mediaAudioViewHolder);
        } else {

            mediaAudioViewHolder = (MediaAudioViewHolder) convertView.getTag();
        }

        mediaAudioViewHolder.render(context, mediaAudioDto, position, checkedPosition,
                                    previousPosition);

        return convertView;
    }

    @Override
    public void getCheckedPosition(int pos) {

        previousPosition = checkedPosition;
        checkedPosition = pos;

        if (checkedPosition != -1) {
            this.notifyDataSetChanged();
        }
    }
}
