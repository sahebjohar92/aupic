package com.aupic.aupic.Helper;

/**
 * Created by saheb on 7/12/15.
 */
public final class VideoKit {
//    static {
//        System.loadLibrary("videokit");
//    }

//    private static VideoKit _instance = null;
//
//    public static VideoKit getInstance() {
//        if (_instance == null) {
//            _instance = new VideoKit();
//        }
//
//        return _instance;
//    }

    public VideoKit() {
        System.out.print("hello");
    }

    /**
     * Call FFmpeg with specified arguments
     * @param args FFmpeg arguments
     * @return true if success, false otherwise
     */
    public boolean process(String[] args) {

        return run(args);
    }

    private native boolean run(String[] args);
}
