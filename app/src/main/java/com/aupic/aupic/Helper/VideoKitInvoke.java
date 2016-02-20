package com.aupic.aupic.Helper;

import android.content.Context;

/**
 * Created by saheb on 20/2/16.
 */
public final class VideoKitInvoke {

        static {
            System.loadLibrary("videoKitInvoke");
        }

        private static VideoKitInvoke _instance = null;

        public static VideoKitInvoke getInstance() {
            if (_instance == null) {
                _instance = new VideoKitInvoke();
            }

            return _instance;
        }

        private VideoKitInvoke() {}

        /**
         * Call FFmpeg with specified arguments
         * @param args FFmpeg arguments
         * @return true if success, false otherwise
         */
        public boolean process(String[] args, Context appContext) {

            String nativeLibPath = appContext.getApplicationInfo().nativeLibraryDir + "/libvideokit.so";
            return run(nativeLibPath, args);
        }

    private native boolean run(String nativeLibPath, String[] args);
}
