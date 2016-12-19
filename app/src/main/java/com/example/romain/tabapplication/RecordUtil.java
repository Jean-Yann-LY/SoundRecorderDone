package com.example.romain.tabapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class RecordUtil {
    private static MediaRecorder mediaRecorder;
    private static String format ;

    public static void start(String filename) throws Exception {
        File audiosDirectory = new File(Environment.getExternalStorageDirectory()
                + "/Audios/");
        if(!audiosDirectory.exists()) {
            audiosDirectory.mkdirs();
        }


        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        System.out.println(TopPaidFragment.output);
        switch (TopPaidFragment.output){
            case ".AMR_NB":
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                format = ".amr";
                break;
            case ".AMR_WB":
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
                format = ".awb";
                break;
            case ".MPEG_4":
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                format = ".mp4";
                break;
            case ".THREE_GPP":
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                format = ".3gp";
                break;
            default:
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                format =".amr";
                break;

        }
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        String filePath =  "audio" + filename + "lit" + format;



        while(TopFreeFragment.listAdapter.getPosition(filePath) != -1){

            filename = String.valueOf(  Integer.valueOf( filename ) + 1  );
            filePath = "audio" + filename  + "lit" + format;

        }
        System.out.println(filePath);
        MainActivity.iRec = Integer.valueOf(filename);
        filePath = audiosDirectory + "/" + filePath;

        File savedFile = new File(filePath);

        mediaRecorder.setOutputFile(filePath);
        mediaRecorder.prepare();
        mediaRecorder.start();
    }



    public static void stop(){
        if (mediaRecorder != null)
        {
            mediaRecorder.stop();
            killMediaRecorder();
        }


    }

    public static void delete(String filename){
        String filePath = Environment.getExternalStorageDirectory() + "/Audios/audio" + filename + "lit" + format;
        File savedFile = new File(filePath);
        savedFile.delete();

    }

    private static void killMediaRecorder(){
        if (mediaRecorder != null)
        {
            mediaRecorder.release();
        }
    }
}
