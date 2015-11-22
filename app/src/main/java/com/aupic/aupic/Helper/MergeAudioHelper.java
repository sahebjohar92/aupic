package com.aupic.aupic.Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;

/**
 * Created by saheb on 19/11/15.
 */
public class MergeAudioHelper {

    public boolean getMergedAudioFile(String firstFile, String secondFile, String mergedFile) {

        try {

            FileInputStream firstStream = new FileInputStream(firstFile);
            FileInputStream secondStream = new FileInputStream(secondFile);

            SequenceInputStream siStream = new SequenceInputStream(firstStream, secondStream);
            FileOutputStream foStream = new FileOutputStream(mergedFile);

            int temp;

            while ((temp = siStream.read()) != -1) {

                foStream.write(temp);   // to write to file
            }
            foStream.close();
            siStream.close();
            firstStream.close();
            secondStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
