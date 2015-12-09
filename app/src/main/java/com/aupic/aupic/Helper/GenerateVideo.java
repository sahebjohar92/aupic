package com.aupic.aupic.Helper;

import android.os.Environment;
import android.util.Log;

import com.aupic.aupic.Constant.StringConstants;

import java.io.File;

/**
 * Created by saheb on 24/11/15.
 */
public class GenerateVideo {

    public void createVideoFromImageAndAudio(String imagePath, String audioPath) {

//        File folder = Environment.getExternalStorageDirectory();
//        String path = folder.getAbsolutePath() + "/DCIM/Camera";
//        // ArrayList<String> paths = (ArrayList<String>) getListOfFiles(path, "jpg");
//        long millis = System.currentTimeMillis();
//
//        videoPath = path + "/" + "test_sham_"+millis+".3gp";
//
//        try {
//
//
//
//            //audio grabber
//            FrameGrabber grabber2 = new FFmpegFrameGrabber(folder.getAbsolutePath()+"/Samsung/Music/Over_the_horizon.mp3");
//
//            //video grabber
//            FrameGrabber grabber1 = new FFmpegFrameGrabber(path+"/20140527_133034.jpg");
//            grabber1.start();
//
//            grabber2.start();
//
//            recorder = new FFmpegFrameRecorder(path
//                    + "/" + "test_sham_"+millis+".3gp",  grabber1.getImageWidth(), grabber1.getImageHeight(),2);
//
//            //recorder.setVideoCodec(5);
//            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
//            // recorder.setVideoCodec(avcodec.AV_CODEC_ID_MP4ALS);
//
//            recorder.setFormat("3gp");
//            //  recorder.setFormat("mp4");
//            recorder.setFrameRate(frameRate);
//            recorder.setSampleRate(grabber2.getSampleRate());
//            recorder.setVideoBitrate(30);
//            startTime = System.currentTimeMillis();
//            recorder.start();
//
//            Frame frame1, frame2 = null;
//
//            while ((frame1 = grabber1.grabFrame()) != null ||
//
//                    (frame2 = grabber2.grabFrame()) != null) {
//
//                recorder.record(frame1);
//
//                recorder.record(frame2);
//
//            }
//
//            recorder.stop();
//
//            grabber1.stop();
//
//            grabber2.stop();
//
//
//
//
//            System.out.println("Total Time:- " + recorder.getTimestamp());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        File directoryPath = Environment.getExternalStoragePublicDirectory(StringConstants.DIRECTORY +
                  StringConstants.DIRECTORY);

        String output = new File(directoryPath, "video.mp4").getAbsolutePath();
        Log.i("Test", "Let's set output to " + output);

        String cmd="ffmpeg -i "+ imagePath +" -i "+ audioPath +" -acodec copy "+ output;

        Log.e("chck plzzzzz", "after " + cmd);


        try{

            Process p = Runtime.getRuntime().exec(cmd);

        }
        catch(Exception e)
        {
            System.out.println("exception"+e);
        }
    }
}
