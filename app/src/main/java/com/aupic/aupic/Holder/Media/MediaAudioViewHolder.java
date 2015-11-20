package com.aupic.aupic.Holder.Media;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 10/11/15.
 */
public class MediaAudioViewHolder {

    @InjectView(R.id.media_title)
    TextView mediaTitle;

    @InjectView(R.id.media_album)
    TextView mediaAlbum;

    @InjectView(R.id.media_play_button)
    ImageView mediaButton;

    @InjectView(R.id.audio_duration)
    TextView audioDuration;

    @InjectView(R.id.select_check_box)
    CheckBox selectCheckBox;

    private int mediaPlayButton = 1;
    private int songInitialized = 2;
    private MediaAudioViewHolder.getCheckedPositionFromCheckBox listener;
    MediaAudioViewHolder.getCheckedMediaFromCheckBox audioActivityListener;

    public MediaAudioViewHolder(View view, MediaAudioViewHolder.getCheckedPositionFromCheckBox listener,
                                MediaAudioViewHolder.getCheckedMediaFromCheckBox audioActivityListener) {

        ButterKnife.inject(this, view);
        this.listener = listener;
        this.audioActivityListener = audioActivityListener;
    }

    public interface getCheckedPositionFromCheckBox {

        public void getCheckedPosition(int pos);
    }

    public interface getCheckedMediaFromCheckBox {

        public void getCheckedPosition(MediaAudioDto mediaAudioDto);
    }

    public void render(final Context context, final MediaAudioDto mediaAudioDto, final int position,
                       int checkedPosition, int previousPosition) {

        final boolean isSelected[] = {false, false, false};

        if ( null != mediaAudioDto) {

            mediaTitle.setText(mediaAudioDto.getTitle());
            mediaAlbum.setText(mediaAudioDto.getAlbumName());

            mediaTitle.setEllipsize(TextUtils.TruncateAt.END);
            mediaAlbum.setEllipsize(TextUtils.TruncateAt.END);

            mediaTitle.setLines(StringConstants.AUDIO_LINE_LIMIT);
            mediaAlbum.setLines(StringConstants.AUDIO_LINE_LIMIT);

            //audioDuration.setText(makeStringFromDuration(mediaAudioDto.getDuration()));


            final MediaPlayer mp = new MediaPlayer();

            if (checkedPosition != -1 && checkedPosition == position) {

                selectCheckBox.setChecked(true);
                mediaButton.setVisibility(View.VISIBLE);
            } else {
                selectCheckBox.setChecked(false);
                mediaButton.setVisibility(View.GONE);
                mp.release();
            }

            if (previousPosition != -1 && previousPosition == position) {

                selectCheckBox.setChecked(false);
                mediaButton.setVisibility(View.GONE);
                mp.release();
            }


            selectCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CheckBox cb = (CheckBox) v;

                    if (cb.isChecked()) {
                        listener.getCheckedPosition(position);
                        mediaButton.setVisibility(View.VISIBLE);
                        audioActivityListener.getCheckedPosition(mediaAudioDto);
                    } else {
                        listener.getCheckedPosition(-1);
                        mediaButton.setVisibility(View.GONE);
                        audioActivityListener.getCheckedPosition(null);
                    }
                }
            });

            mediaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isSelected[songInitialized]) {
                        try {

                            mp.setDataSource(mediaAudioDto.getData());
                            mp.prepare();
                            isSelected[songInitialized] = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (isSelected[mediaPlayButton]) {
                        isSelected[mediaPlayButton] = false;
                        mediaButton.setImageResource(R.drawable.play);
                        mp.pause();
                    } else {
                        isSelected[mediaPlayButton] = true;
                        mediaButton.setImageResource(R.drawable.pause);
                        mp.start();
                    }
                }
            });
        }
    }

    private String makeStringFromDuration(Integer duration) {

        String dur = StringConstants.WHITESPACE;

        if ( duration != null) {

            Double item = Double.valueOf(duration);
            Double q = item/(60D * 60D);

            double roundOff = Math.round(q * 100.0) / 100.0;

            int decimal = (int) roundOff;
            double fractional = (roundOff - decimal) * 100;

            dur = decimal+"";
            dur += StringConstants.COLON;
            dur += fractional;
        }

        return dur;
    }
}
