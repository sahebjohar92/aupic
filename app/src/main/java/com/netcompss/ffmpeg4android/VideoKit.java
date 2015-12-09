package com.netcompss.ffmpeg4android;

/**
 * Created by saheb on 7/12/15.
 */
public final class VideoKit {
    static {
        System.loadLibrary("videokit");
    }

    private static VideoKit _instance = null;

    public static VideoKit getInstance() {
        if (_instance == null) {
            _instance = new VideoKit();
        }

        return _instance;
    }

    private VideoKit() {}

    /**
     * Call FFmpeg with specified arguments
     * @param args FFmpeg arguments
     * @return true if success, false otherwise
     */
    public boolean process(String[] args) {
        //String[] params = new String[args.length + 1];
        //params[0] = "ffmpeg";
        //System.arraycopy(args, 0, params, 1, args.length);

        return run(args);
    }

    private native boolean run(String[] args);
}