package com.aupic.aupic.Activity.media;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Adaptors.Media.MediaAudioAdaptor;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Holder.Media.MediaAudioDto;
import com.aupic.aupic.Holder.Media.MediaAudioViewHolder;
import com.aupic.aupic.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by saheb on 10/11/15.
 */
public class MediaAudioActivity extends AupFragmentActivity implements MediaAudioViewHolder.getCheckedMediaFromCheckBox {

    MediaAudioAdaptor mediaAudioAdaptor;

    private MediaAudioDto selectedMediaAudioDto;

    @InjectView(R.id.media_list_view)
    ListView mediaListView;

    @Override
    protected int getTitleText() {

        return R.string.all_songs;
    }

    @Override
    protected int getContentViewId() {

        return R.layout.media_list_view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBus.getInstance().register(this);

        ButterKnife.inject(this);

        initialize();
    }

    private void initialize() {

        List<MediaAudioDto> mediaAudioDtoList = new ArrayList<>();

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM
        };

        Cursor cur = getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection, selection, null, null);


        if (cur.moveToFirst()) {

            MediaAudioDto mediaAudioDto;

            String songTitle;
            String songDisplayName;
            String songAlbum;
            String songData;
            Integer songsDuration;
            int songId;


            int song_id = cur
                    .getColumnIndex(MediaStore.Audio.Media._ID);

            int song_title = cur
                    .getColumnIndex(MediaStore.Audio.Media.TITLE);

            int song_data = cur
                    .getColumnIndex(MediaStore.Audio.Media.DATA);

            int song_display_name = cur
                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);

            int song_duration = cur
                    .getColumnIndex(MediaStore.Audio.Media.DURATION);

            int song_album = cur
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM);

            do {

                songTitle = cur.getString(song_title);
                songId = cur.getInt(song_id);
                songDisplayName = cur.getString(song_display_name);
                songsDuration = cur.getInt(song_duration);
                songData = cur.getString(song_data);
                songAlbum = cur.getString(song_album);

                if (songTitle != null && songTitle.length() > 0) {

                    mediaAudioDto = new MediaAudioDto();

                    mediaAudioDto.setAlbumName(songAlbum);
                    mediaAudioDto.setAudioId(songId);
                    mediaAudioDto.setData(songData);
                    mediaAudioDto.setDisplayName(songDisplayName);
                    mediaAudioDto.setDuration(songsDuration);
                    mediaAudioDto.setTitle(songTitle);

                    mediaAudioDtoList.add(mediaAudioDto);
                }

            } while (cur.moveToNext());

            mediaAudioAdaptor = new MediaAudioAdaptor(this, R.id.media_list_view ,mediaAudioDtoList,
                                                      this);
            mediaListView.setAdapter(mediaAudioAdaptor);
        }
        cur.close();

    }

    @Override
    public void getCheckedPosition(MediaAudioDto mediaAudioDto) {

        selectedMediaAudioDto = mediaAudioDto;
        Log.d("Media Audio Activity",""+mediaAudioDto);
    }

    @Optional
    @OnClick(R.id.done_audio_button)
    public void uploadAudio() {

        if (null != selectedMediaAudioDto) {

            Intent resultIntent = new Intent();
            resultIntent.putExtra(IntentConstants.SELECTED_AUDIO, selectedMediaAudioDto);
            setResult(Activity.RESULT_OK, resultIntent);

            finish();
        } else {
            Toast.makeText(this, "Select audio and then press done",Toast.LENGTH_SHORT).show();
        }
    }

    @Optional
    @OnClick(R.id.cancel_audio_button)
    public void cancel() {
        finish();
    }
}
