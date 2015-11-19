package com.aupic.aupic.Activity.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.aupic.aupic.Activity.aupic.AupicCreatorActivity;
import com.aupic.aupic.Constant.IntentConstants;
import com.aupic.aupic.Constant.StringConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by saheb on 21/10/15.
 */
public class CameraActivityNew extends Activity {

    private HashMap<String, Bitmap> capturedImageMap = new HashMap<>();
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private int CANCEL_PRESSED_LIMIT = 1;
    private boolean newActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (null != getIntent() && null != getIntent().getExtras()) {

            newActivity = getIntent().getExtras().getBoolean(IntentConstants.NEW_ACTIVITY);
        }

        dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {

            if (resultCode == RESULT_OK) {
                galleryAddPic();
                capturedImageMap.put(mCurrentPhotoPath, null);

                if (newActivity) {

                    Intent aupicCreatorIntent = new Intent(this, AupicCreatorActivity.class);
                    aupicCreatorIntent.putExtra(IntentConstants.SELECTED_IMAGES_MAP, capturedImageMap);
                    startActivity(aupicCreatorIntent);

                } else {

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(IntentConstants.SELECTED_IMAGES_MAP, capturedImageMap);
                    setResult(Activity.RESULT_OK, resultIntent);

                    finish();
                }

            } else if (resultCode == RESULT_CANCELED) {

                File file = new File(mCurrentPhotoPath);
                if (file.exists()) {
                    file.delete();
                }

                if (CANCEL_PRESSED_LIMIT > 0) {

                    if (CANCEL_PRESSED_LIMIT == 1) {

                        Toast.makeText(getApplicationContext(),
                                "Press back key once more to exit", Toast.LENGTH_SHORT)
                                .show();
                    }
                    dispatchTakePictureIntent();
                } else {
                    this.finish();
                }
                CANCEL_PRESSED_LIMIT--;
            } else {

                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("Camera Activity", "Enable to create image file");
            }

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(StringConstants.DIRECTORY +
                                                                        StringConstants.PICTURES);

        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.d("Image Directory Name", "Oops! Failed create "
                        + storageDir + " directory");
                return null;
            }
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
