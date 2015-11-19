package com.aupic.aupic.Activity.camera;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aupic.aupic.Activity.base.AupFragmentActivity;
import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.InjectView;

/**
 * Created by saheb on 21/10/15.
 */
public class CameraActivity extends AupFragmentActivity implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceHolder surfaceHolder;

    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;

    @InjectView(R.id.camera_capture)
    ImageView cameraCapture;

    @InjectView(R.id.cameraSurfaceView)
    SurfaceView surfaceView;

    @Override
    protected int getTitleText() {
        return R.string.click_pic;
    }

    @Override
    protected int getContentViewId() { return R.layout.camera_layout; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        jpegCallback = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                createDirectoryAndSaveFile(data, String.format("%d.jpg", System.currentTimeMillis()));
                Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_LONG).show();
                refreshCamera();
            }
        };

        cameraCapture.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        camera.takePicture(null, null, jpegCallback);
                    }
                }
        );
    }

    public void captureImage(View v) throws IOException {
        camera.takePicture(null, null, jpegCallback);
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        Camera.Parameters param;
        param = camera.getParameters();
        param.setPreviewSize(800, 600);
        camera.setParameters(param);

        camera.startFaceDetection();


        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
        }

        catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }

        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    private void createDirectoryAndSaveFile(byte[] data, String fileName) {

        String directoryPath = Environment.getExternalStorageDirectory() + StringConstants.DIRECTORY +
                                                                           StringConstants.PICTURES;

        File direct = new File(directoryPath);

        if (!direct.exists()) {
            File wallpaperDirectory = new File(directoryPath);
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File(directoryPath), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(data);
            out.close();
            galleryAddPic(directoryPath + "/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void galleryAddPic(String mCurrentPhotoPath) {

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
