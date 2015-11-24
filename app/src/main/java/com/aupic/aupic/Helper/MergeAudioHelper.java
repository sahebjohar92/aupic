package com.aupic.aupic.Helper;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            FileOutputStream foStream = new FileOutputStream(mergedFile, true);

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


//        byte[] data1;
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        InputStream is;
//        try {
//            is = new FileInputStream(firstFile);
//
//            byte[] temp = new byte[firstFile.length()];
//            int read;
//
//            while ((read = is.read(temp)) >= 0) {
//                buffer.write(temp, 0, read);
//            }
//        } catch (FileNotFoundException e1) {
//
//            e1.printStackTrace();
//            return false;
//        } catch (IOException e) {
//
//            e.printStackTrace();
//            return false;
//        }
//        data1 = buffer.toByteArray();
//
//        byte[] data2;
//        ByteArrayOutputStream buffer2 = new ByteArrayOutputStream();
//        InputStream is2;
//        try {
//            is2 = new FileInputStream(firstFile);
//
//            byte[] temp2 = new byte[secondFile.length()];
//            int read;
//
//            while ((read = is2.read(temp2)) >= 0) {
//                buffer2.write(temp2, 0, read);
//            }
//        } catch (FileNotFoundException e1) {
//
//            e1.printStackTrace();
//            return false;
//        } catch (IOException e) {
//
//            e.printStackTrace();
//            return false;
//        }
//        data2 = buffer2.toByteArray();
//
//        byte[] combined;
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        try {
//            outputStream.write(data1);
//            outputStream.write(data2);
//            combined = outputStream.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        try {
//            FileOutputStream fos;
//
//            File outputFile = new File(mergedFile);
//            fos = new FileOutputStream(outputFile);
//            fos.write(combined);
//            fos.close();
//
//        } catch (Exception e) {
//            return false;
//        }
//
//        return true;
    }
}
