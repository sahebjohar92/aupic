package com.aupic.aupic.Helper;

/**
 * Created by saheb on 3/12/15.
 */

import android.app.Activity;

import com.aupic.aupic.Constant.StringConstants;
import com.aupic.aupic.R;
import com.netcompss.ffmpeg4android.GeneralUtils;
        import com.netcompss.ffmpeg4android.Prefs;
import com.netcompss.ffmpeg4android.VideoKit;
import com.netcompss.loader.LoadJNI;
        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.PowerManager;
        import android.os.PowerManager.WakeLock;

        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.Toast;

/**
 *  To run this Demo Make sure you have on your device this folder:
 *  /sdcard/videokit,
 *  and you have in this folder a video file called in.mp4
 * @author elih
 *
 */
public class Sample_FFmpeg extends Activity {

    String workFolder = null;
    String demoVideoFolder = null;
    String demoVideoPath = null;
    String demoAudioPath = null;
    String demoAudioPath1 = null;
    String imagePath = null;
    String vkLogPath = null;
    String useVideoPath1 = null;
    String useVideoPath2 = null;
    private boolean commandValidationFailedFlag = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ffmpeg_demo_client_1);

        demoVideoFolder = "" + Environment.getExternalStoragePublicDirectory("DCIM/Camera");

        demoVideoPath = demoVideoFolder + "/outputConcat.mp4";
        demoAudioPath = demoVideoFolder + "/Audio_20151123_224344.mp3";
        demoAudioPath1 = demoVideoFolder + "/audio.mp3";
        imagePath     = demoVideoFolder + "/IMG_20150826_143022.jpg";
        useVideoPath1 = demoVideoFolder + "/test1.mp4";
        useVideoPath2 = demoVideoFolder + "/output.mp4";


        Log.i(Prefs.TAG, getString(R.string.app_name) + " version: " + GeneralUtils.getVersionName(getApplicationContext()) );
        workFolder = getApplicationContext().getFilesDir().getAbsolutePath() + "/";
        Log.i(Prefs.TAG, "workFolder (license and logs location) path: " + workFolder);
        vkLogPath = workFolder + "vk.log";
        Log.i(Prefs.TAG, "vk log (native log) path: " + vkLogPath);

        //GeneralUtils.copyLicenseFromAssetsToSDIfNeeded(this, workFolder);
        GeneralUtils.copyDemoVideoFromAssetsToSDIfNeeded(this, demoVideoFolder);

        Button invoke =  (Button)findViewById(R.id.invokeButton);
        invoke.setOnClickListener(new OnClickListener() {
            public void onClick(View v){
                Log.i(Prefs.TAG, "run clicked.");
                //if (GeneralUtils.checkIfFileExistAndNotEmpty(demoVideoPath)) {
                    new TranscdingBackground(Sample_FFmpeg.this).execute();
                //}
                //else {
                  //  Toast.makeText(getApplicationContext(), demoVideoPath + " not found", Toast.LENGTH_LONG).show();
                //}
            }
        });

//        int rc = GeneralUtils.isLicenseValid(getApplicationContext(), workFolder);
//        Log.i(Prefs.TAG, "License check RC: " + rc);
    }

    public class TranscdingBackground extends AsyncTask<String, Integer, Integer>
    {

        ProgressDialog progressDialog;
        Activity _act;

        public TranscdingBackground (Activity act) {
            _act = act;
        }



        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(_act);
            progressDialog.setMessage("FFmpeg4Android Transcoding in progress...");
            progressDialog.show();

        }

        protected Integer doInBackground(String... paths) {
            Log.i(Prefs.TAG, "doInBackground started...");

            // delete previous log
            //GeneralUtils.deleteFileUtil(workFolder + "/vk.log");

            PowerManager powerManager = (PowerManager)_act.getSystemService(Activity.POWER_SERVICE);
            WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "VK_LOCK");
            Log.d(Prefs.TAG, "Acquire wake lock");
            wakeLock.acquire();



            ///////////// Set Command using code (overriding the UI EditText) /////
            //String commandStr = "ffmpeg -y -i "+demoVideoPath+" -strict experimental -s 320x240 -r 30 -aspect 3:4 -ab 48000 -ac 2 -ar 22050 -vcodec mpeg4 -b 2097152 "+demoVideoFolder+"/out.mp4";
            //String[] complexCommand = {"ffmpeg", "-y" ,"-i", "/sdcard/videokit/in.mp4","-strict","experimental","-s", "160x120","-r","25", "-vcodec", "mpeg4", "-b", "150k", "-ab","48000", "-ac", "2", "-ar", "22050", "/sdcard/videokit/out.mp4"};
            ///////////////////////////////////////////////////////////////////////

            String commandStr = "ffmpeg -y -loop 1 -i "+imagePath+" -i "+demoAudioPath+" -strict experimental -s 1270x720 -r 25 -aspect 16:9 -vcodec mpeg4 -vcodec mpeg4 -ab 48000 -ac 2 -b 2097152 -ar 22050 -shortest "+demoVideoPath;
            commandStr = "ffmpeg -t 10 -i "+demoAudioPath1+" -acodec copy "+demoVideoFolder+"/outputfile.mp3";
            //commandStr = "ffmpeg -i "+imagePath+" -vf scale=320:-1 "+demoVideoFolder+"output_320.png";
            //LoadJNI vk = new LoadJNI();
            //commandStr = "ffmpeg -y -i "+demoAudioPath+" -i "+demoAudioPath1+" -filter_complex amix=inputs=2:duration=first:dropout_transition=3 "+demoVideoFolder+"/output.mp3";
            commandStr = "ffmpeg -y -i "+useVideoPath1+" -i "+useVideoPath2+" -strict experimental -filter_complex [0:0][0:1][1:0][1:1]concat=n=2:v=1:a=1 "+demoVideoPath;
            try {

                // complex command
                //vk.run(complexCommand, workFolder, getApplicationContext());

                //vk.run(GeneralUtils.utilConvertToComplex(commandStr), workFolder, getApplicationContext());
                VideoKit.getInstance().process(GeneralUtils.utilConvertToComplex(commandStr));




                // running without command validation
                //vk.run(complexCommand, workFolder, getApplicationContext(), false);

                // copying vk.log (internal native log) to the videokit folder
                //GeneralUtils.copyFileToFolder(vkLogPath, demoVideoFolder);

//			} catch (CommandValidationException e) {
//					Log.e(Prefs.TAG, "vk run exeption.", e);
//					commandValidationFailedFlag = true;

            } catch (Exception e) {
                Log.e(Prefs.TAG, "vk run exeption.", e);
            }
            finally {
                if (wakeLock.isHeld())
                    wakeLock.release();
                else{
                    Log.i(Prefs.TAG, "Wake lock is already released, doing nothing");
                }
            }
            Log.i(Prefs.TAG, "doInBackground finished");
            return Integer.valueOf(0);
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onCancelled() {
            Log.i(Prefs.TAG, "onCancelled");
            //progressDialog.dismiss();
            super.onCancelled();
        }


        @Override
        protected void onPostExecute(Integer result) {
            Log.i(Prefs.TAG, "onPostExecute");
            progressDialog.dismiss();
            super.onPostExecute(result);

            // finished Toast
//            String rc = null;
//            if (commandValidationFailedFlag) {
//                rc = "Command Vaidation Failed";
//            }
//            else {
//                rc = GeneralUtils.getReturnCodeFromLog(vkLogPath);
//            }
//            final String status = rc;
//            Sample_FFmpeg.this.runOnUiThread(new Runnable() {
//                public void run() {
//                    Toast.makeText(Sample_FFmpeg.this, status, Toast.LENGTH_LONG).show();
//                    if (status.equals("Transcoding Status: Failed")) {
//                        Toast.makeText(Sample_FFmpeg.this, "Check: " + vkLogPath + " for more information.", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
        }

    }


}