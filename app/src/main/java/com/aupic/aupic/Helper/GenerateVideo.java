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

                // Working commnand
                String[] command = {"ffmpeg","-y","-loop","1","-i",imagePath,"-i",audioPath,
                "-strict","experimental","-r","25","-aspect","16:9",
                "-ab","48000","-ac","2","-b:v","1024000","-ar","22050","-vcodec","mpeg4",
                "-shortest",videoFile};

        File storageDir = Environment.getExternalStoragePublicDirectory(StringConstants.DIRECTORY +
                "/"+StringConstants.VIDEO);

        String file1 = storageDir + "/Video_20160220_193639.mp4";
        String file2 = storageDir + "/Video_20160220_131708.mp4";
        String file3 = generateFileNames.getVideoFileNameMpg();
        String file4 = storageDir + "/Video_20160221_121820.ts";
        String file5 = storageDir + "/Video_20160221_121957.ts";
        String file6 = storageDir + "/Video_20160221_131647.mpg";
        String file7 = storageDir + "/Video_20160221_135354.ts";



//        String[] command = {"ffmpeg","-y","-i",file1,"-i",file2,"-strict","experimental","-filter_complex",
//                            "[0:0][0:1][1:0][1:1]concat=n=2:v=1:a=1","-r","25","-aspect","16:9",
//                "-ab","48000","-ac","2","-b:v","1024000","-ar","22050","-vcodec","mpeg4",
//                "-shortest",videoFile};


        String[] command2 = {"ffmpeg","-y","-i","concat:"+file2+"|"+file2,"-r","25","-aspect","16:9",
                            "-b:v","1024000","-vcodec","mpeg4","-shortest",videoFile};

        //String[] command1 = {"ffmpeg","-i","concat:"+file1+"|"+file2,"-codec","copy",videoFile};

        String[] command1 = {"ffmpeg","-i",file1,"-i",file1,
        "-filter_complex","concat=n=2:v=1:a=1 "
        ,videoFile};

        String[] command3 = {"ffmpeg","-i","concat:"+file4+"|"+file5,"-c","copy","-bsf:a","aac_adtstoasc",videoFile};

        String[] command4 = {"ffmpeg","-i",file1,"-c","copy","-bsf:v","h264_mp4toannexb","-f","mpegts",file3};


        String[] command5 = {"ffmpeg","-i",file1,"-i",file1,"-filter_complex",
        "[0:0] [0:1] [1:0] [1:1] concat=n=2:v=1:a=1 [v] [a]",
                "-map", "[v]", "-map", "[a]","-r","25","-aspect","16:9",
        "-ab","48000","-ac","2","-b:v","1024000","-ar","22050","-vcodec","mpeg4",
                "-shortest",videoFile};

//        String[] command = {"ffmpeg","-y","-i",file1,
//                "-i",file2,
//                "-filter_complex",
//                "[0:v]scale=640x480,setsar=1:1[v0];[1:v]scale=640x480,setsar=1:1[v1];[v0][0:a][v1][1:a] concat=n=2:v=1:a=1",
//                "-ab","48000","-ac","2","-ar","22050","-s","640x480","-r","25","-vcodec","mpeg4","-b:v","1024000",videoFile};


        //String[] command = {"ffmpeg","-i",file1,"-c","copy","-f","mpegts",file3};
        String[] command6 = {"ffmpeg","-y","-i","concat:"+file1+"|"+file1,"-c","copy","-vcodec","copy",videoFile};
        String[] command7 = {"ffmpeg","-i",file1,file3};
        String[] command8 = {"cat",file6,file6,"|","ffmpeg","-f","mpeg","-i","-","-vcodec","mpeg4",videoFile};
        //String[] command = {"ffmpeg","-y","-r","25","-i",file5,"-c:v","libx264","-c:a","libfaac","-r","25","-b:v","1024000",videoFile};

        String[] command9 = {"ffmpeg","-i",file7,"-vcodec","copy","-acodec","copy",file3};

        String[] command10 = {"ffmpeg","-i","concat:"+file7+"|"+file7,"-c","copy",videoFile};


        try {

            Boolean merged = VideoKitInvoke.getInstance().process(command, context);
//            String file6 = storageDir + "/vid.ts";
//            String[] command5 = {"ffmpeg","-i",file2,"-c","copy","-bsf:v","h264_mp4toannexb","-f","mpegts",file6};
//            Boolean merged1 = VideoKitInvoke.getInstance().process(command5, context);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
