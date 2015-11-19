package com.aupic.aupic.Activity.media;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.GridView;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Adaptors.Media.MediaAlbumAdaptor;
import com.aupic.aupic.Event.AppBus;
import com.aupic.aupic.Holder.Media.MediaAlbumsDTO;
import com.aupic.aupic.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by root on 9/11/15.
 */
public class MediaAlbumActivity extends AupFragmentActivity {

    private MediaAlbumAdaptor mediaAlbumAdaptor;

    @InjectView(R.id.phoneAlbumGrid)
    GridView mediaAlbumGrid;

    @Override
    protected int getTitleText() {

        return R.string.media_albums;
    }

    @Override
    protected int getContentViewId() {

        return R.layout.albums_grid_view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBus.getInstance().register(this);

        ButterKnife.inject(this);

        initialize();
    }

    private void initialize() {

        MediaAlbumsDTO mediaAlbum;
        List<MediaAlbumsDTO> mediaAlbumsDTOList = new ArrayList<>();

        String[] projection = new String[] { MediaStore.Audio.Albums._ID,
                                             MediaStore.Audio.Albums.ALBUM,
                                             MediaStore.Audio.Albums.ALBUM_ART,
                                             MediaStore.Audio.Albums.NUMBER_OF_SONGS };

        String selection = "1) GROUP BY 1,(2";
        String sortOrder = MediaStore.Audio.Media.ALBUM + " ASC";

        Cursor cur = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projection, selection, null, sortOrder);

        if (cur.moveToFirst()) {

            String albumArtPath;
            String albumName;
            int albumId;
            int songsCount;

            int album_id = cur
                    .getColumnIndex(MediaStore.Audio.Albums._ID);

            int album_name = cur
                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM);

            int album_art = cur
                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);

            int songs_count = cur
                    .getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);

            do {

                albumName = cur.getString(album_name);
                albumId = cur.getInt(album_id);
                albumArtPath = cur.getString(album_art);
                songsCount = cur.getInt(songs_count);

                if (albumName != null && albumName.length() > 0) {

                    mediaAlbum = new MediaAlbumsDTO();

                    mediaAlbum.setAlbumId(albumId);
                    mediaAlbum.setAlbumName(albumName);
                    mediaAlbum.setSongsCount(songsCount);
                    mediaAlbum.setAlbumArtPath(albumArtPath);

                    mediaAlbumsDTOList.add(mediaAlbum);
                }

            } while (cur.moveToNext());

            mediaAlbumAdaptor = new MediaAlbumAdaptor(this, R.id.phoneAlbumGrid ,mediaAlbumsDTOList);
            mediaAlbumGrid.setAdapter(mediaAlbumAdaptor);
        }
        cur.close();
    }

}
