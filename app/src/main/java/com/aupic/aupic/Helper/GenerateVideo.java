package com.aupic.aupic.Helper;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.aupic.aupic.Constant.StringConstants;

import java.io.File;

/**
 * Created by saheb on 24/11/15.
 */
public class GenerateVideo {

    private GenerateFileNames generateFileNames = new GenerateFileNames();

    public void createVideoFromImageAndAudio(String imagePath, String audioPath, String videoFile, Context context) {

        //ffmpeg -y -loop 1 -i "+imagePath+" -i "+demoAudioPath+" -strict experimental -s 1270x720 -r 25 -aspect 16:9 -vcodec mpeg4 -vcodec mpeg4 -ab 48000 -ac 2 -b 2097152 -ar 22050 -shortest "+demoVideoPath
        //commandStr = "ffmpeg -y -i "+useVideoPath1+" -i "+useVideoPath2+" -strict experimental -filter_complex [0:0][0:1][1:0][1:1]concat=n=2:v=1:a=1 "+demoVideoPath;



//        String[] command = {"ffmpeg","-y","-loop","1","-i",imagePath,"-i",audioPath,
//                "-strict","experimental","-r","25","-aspect","16:9",
//                "-ab","48000","-ac","2","-b:v","1024000","-ar","22050","-vcodec","mpeg4",
//                "-shortest",videoFile};


                String[] command = {"ffmpeg","-y","-loop","1","-i",imagePath,"-i",audioPath,
                "-strict","experimental","-r","25","-aspect","16:9",
                "-ab","48000","-ac","2","-b:v","1024000","-ar","22050","-vcodec","mpeg4",
                "-shortest",videoFile};

        File storageDir = Environment.getExternalStoragePublicDirectory(StringConstants.DIRECTORY +
                StringConstants.AUDIO);

        String file1 = storageDir + "/Video_20151213_122806.mp4";
        String file2 = storageDir + "/Video_20151213_123903.mp4";
        String file3 = generateFileNames.getVideoFileNameMpg();
        String file4 = storageDir + "/Video_20151214_144252.ts";
        String file5 = storageDir + "/Video_20151214_124136.mpg";



//        String[] command = {"ffmpeg","-y","-i",file1,"-i",file2,"-strict","experimental","-filter_complex",
//                            "[0:0][0:1][1:0][1:1]concat=n=2:v=1:a=1","-r","25","-aspect","16:9",
//                "-ab","48000","-ac","2","-b:v","1024000","-ar","22050","-vcodec","mpeg4",
//                "-shortest",videoFile};


//        String[] command = {"ffmpeg","-y","-i","concat:"+file2+"|"+file2,"-r","25","-aspect","16:9",
//                            "-b:v","2097152","-vcodec","mpeg4","-shortest",videoFile};

//        String[] command = {"ffmpeg","-i",file1,"-i",file2,"-filter_complex","[0:0][0:1][1:0][1:1]concat=n=2:v=1:a=1 [v] [a]",
//                            "-map","[v]","-map","[a]","-vcodec","mpeg4",videoFile};


//        String[] command = {"ffmpeg","-y","-i",file1,
//                "-i",file2,
//                "-filter_complex",
//                "[0:v]scale=640x480,setsar=1:1[v0];[1:v]scale=640x480,setsar=1:1[v1];[v0][0:a][v1][1:a] concat=n=2:v=1:a=1",
//                "-ab","48000","-ac","2","-ar","22050","-s","640x480","-r","25","-vcodec","mpeg4","-b:v","1024000",videoFile};


        //String[] command = {"ffmpeg","-i",file1,"-c","copy","-f","mpegts",file3};
        //String[] command = {"ffmpeg","-y","-i","concat:"+file4+"|"+file4,"-c","copy","-vcodec","copy",videoFile};
        //String[] command = {"ffmpeg","-y","-r","25","-i",file5,"-c:v","libx264","-c:a","libfaac","-r","25","-b:v","1024000",videoFile};
        try {

            Boolean merged = VideoKitInvoke.getInstance().process(command, context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
