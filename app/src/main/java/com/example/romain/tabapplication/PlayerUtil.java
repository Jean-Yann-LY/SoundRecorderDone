package com.example.romain.tabapplication;

import android.media.MediaPlayer;
import android.os.Environment;
import android.view.View;
import android.widget.ToggleButton;

import java.io.File;


public class PlayerUtil extends MainActivity {
    private static MediaPlayer mediaPlayer;
    public static ToggleButton playBtn;
    static MainActivity main = new MainActivity();


    public static void start(Integer filename) throws Exception{




        if(mediaPlayer == null) {
            File audiosDirectory = new File(Environment.getExternalStorageDirectory()
                    + "/Audios/");
            String filePath = audiosDirectory + "/"+ TopFreeFragment.listAdapter.getItem(filename);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
//        killMediaPlayer();
            System.out.println(mediaPlayer);
        }else{
            mediaPlayer.start();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                MainActivity.unPlay();
                System.out.println("song over");
                TopFreeFragment.unPlay();
                PlayerUtil.stop();
            }
        });


    }

    public static void pause() {
        if (mediaPlayer != null)
        {
            mediaPlayer.pause();
            //mediaPlayer.start();
        }
    }

    public static void stop(){
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            killMediaPlayer();
            mediaPlayer = null;
        }
    }

    private static void killMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
        }

    }

    protected static void delete(Integer i){
        if(!TopFreeFragment.listAdapter.isEmpty()) {//TopFreeFragment.listAdapter.getPosition(MainActivity.iPlay) != -1
            String filePath = Environment.getExternalStorageDirectory() + "/Audios/" + TopFreeFragment.listAdapter.getItem(MainActivity.iPlay);
            File savedFile = new File(filePath);
            if (savedFile.exists()) {
                savedFile.delete();
                TopFreeFragment.addSongsToList();
                TopFreeFragment.update();
            }
        }
    }

    protected static boolean previous(Integer i) {
        if(i >= 0){
            mediaPlayer = null;
            return true;}

        return false;
    }

    protected static boolean exist(Integer i){
        if(TopFreeFragment.listAdapter.getCount() > i ){

            return true;}

        return false;
    }

    protected static boolean next(Integer i) {
        if(TopFreeFragment.listAdapter.getCount() > i){
            mediaPlayer = null;
            return true;}

        return false;
    }

    protected static String duration(){
        String duration = "";
        if(mediaPlayer != null) {
            duration = String.valueOf(mediaPlayer.getDuration());
        }
        return duration;
    }

    protected static String position(){
        String position = "";
        if(mediaPlayer != null) {
            position = String.valueOf(mediaPlayer.getDuration());
        }
        return position;
    }
}
