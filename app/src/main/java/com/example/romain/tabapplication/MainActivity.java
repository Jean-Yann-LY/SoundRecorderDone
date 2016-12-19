package com.example.romain.tabapplication;

import android.Manifest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.Menu;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {


    int count = 0;

    //STATES
    public static boolean isPlaying = false;
    private boolean isStopped = true;
    private boolean isRecording = false;
    private Handler mHandler = new Handler();

    ListView listView;
    ArrayAdapter<String> listAdapter;

    Integer recced = 0;
    static Integer iRec = 0;
    static Integer iPlay = 0;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;


    //test
    ToggleButton btnPlay;

    private static final String TAG = "MainActivity";

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = (ArrayAdapter<CharSequence>) ArrayAdapter.createFromResource(this, R.array.encoding, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


//        spinner.setAdapter(adapter);

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        verifyStoragePermissions(this);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new HomeFragment(), "Record");
        viewPagerAdapter.addFragments(new TopFreeFragment(), "Play");
        viewPagerAdapter.addFragments(new TopPaidFragment(), "Settings");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);


        btnPlay = (ToggleButton) findViewById(R.id.playButton);
        // myTextView = (TextView)findViewById(R.id.myTextView);


//timer


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public static void verifyStoragePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 200);
    }

    public void startRecordOnClick(View v) {
        if (!isRecording) {
            try {
                System.out.println("Start recording");
                Toast.makeText(this, "Start recording", Toast.LENGTH_SHORT).show();
                iRec = 0;
                RecordUtil.start(String.valueOf(iRec));
                System.out.println("record start");
                //Toast.makeText(this, "record start",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isRecording = true;
            HomeFragment.cancelAppear();
            notification("Sound Recorder application is recording.", R.drawable.record_button_unpressed_small);

        } else {
            RecordUtil.stop();
            System.out.println("record stop");
            Toast.makeText(this, "Record stopped", Toast.LENGTH_SHORT).show();
            isRecording = false;
            HomeFragment.cancelDisappear();
            TopFreeFragment.addSongsToList();
            TopFreeFragment.update();
        }
    }


    //case record cancel
    public void cancelRecordOnClick(View v) {
        if (isRecording) {
            RecordUtil.stop();
            RecordUtil.delete(String.valueOf(iRec));
            System.out.println("record cancel");
            Toast.makeText(this, "Record cancelled", Toast.LENGTH_SHORT).show();
            isRecording = false;
            iRec--;
            HomeFragment.unRecord();
            HomeFragment.cancelDisappear();
        } else {
            System.out.println("no record to cancel");
            Toast.makeText(this, "No record to cancel", Toast.LENGTH_SHORT).show();
        }
    }

    // case play start
    public void startPlayOnClick(View v) {
        if (!isPlaying && PlayerUtil.exist(iPlay)) {
            try {
                isPlaying = true;
                isStopped = false;
                System.out.println("player start");
                Toast.makeText(this, "Player started", Toast.LENGTH_SHORT).show();
                PlayerUtil.start(iPlay);
            } catch (Exception e) {
                e.printStackTrace();
            }

            notification("Sound Recorder application is playing.", R.drawable.play_button_unpressed);
        } else if (!PlayerUtil.exist(iPlay)) {
            TopFreeFragment.unPlay();
            System.out.println("No file to play");
            Toast.makeText(this, "No file to play", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("player pause");
            Toast.makeText(this, "Player paused", Toast.LENGTH_SHORT).show();
            PlayerUtil.pause();
            isPlaying = false;
        }
    }


    //case play stop

    public void stopPlayOnClick(View v) {
        if (!isStopped) {
            System.out.println("player stop");
            Toast.makeText(this, "Player stopped", Toast.LENGTH_SHORT).show();
            PlayerUtil.stop();
            isPlaying = false;
            isStopped = true;
            TopFreeFragment.unPlay();
        } else {
            System.out.println("not playing");
            Toast.makeText(this, "Not playing", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOnClick(View v) {
        if (isPlaying) {

            PlayerUtil.stop();
            isPlaying = false;
        }
        if (PlayerUtil.exist(iPlay)) {
            System.out.println("Audio deleted");
            Toast.makeText(this, "Audio deleted", Toast.LENGTH_SHORT).show();
            PlayerUtil.delete(iPlay);
            while (!PlayerUtil.exist(iPlay) && iPlay != 0) {
                System.out.println("Pas bon");
                iPlay--;

            }
            System.out.println("C'est bon");
        } else {
            System.out.println("No audio to delete");
            Toast.makeText(this, "No audio to delete", Toast.LENGTH_SHORT).show();
        }
    }

    public void previousOnClick(View v) throws Exception {

        if (isPlaying) {
            PlayerUtil.stop();
            isPlaying = false;
            TopFreeFragment.unPlay();
        }
        if (PlayerUtil.previous(iPlay - 1)) {
            iPlay--;
            System.out.println("previous");
            Toast.makeText(this, "Previous audio", Toast.LENGTH_SHORT).show();
            PlayerUtil.start(iPlay);
            isPlaying = true;
            isStopped = false;
            TopFreeFragment.play();

        } else {
            System.out.println("no previous");
            Toast.makeText(this, "No previous audio", Toast.LENGTH_SHORT).show();
        }

    }

    public void nextOnClick(View v) throws Exception {

        if (isPlaying) {
            PlayerUtil.stop();
            isPlaying = false;
            TopFreeFragment.unPlay();
        }
        if (PlayerUtil.next(iPlay + 1)) {
            iPlay++;
            System.out.println("next");
            Toast.makeText(this, "Next audio", Toast.LENGTH_SHORT).show();
            PlayerUtil.start(iPlay);
            isPlaying = true;
            isStopped = false;
            TopFreeFragment.play();
        } else {
            System.out.println("no next");
            Toast.makeText(this, "No next audio", Toast.LENGTH_SHORT).show();
        }

    }

    public void notification(String msg, Integer icon) {
        Intent intent = new Intent();
        PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Sound Recorder")
                .setContentText(msg)
                .setSmallIcon(icon)
                .setContentIntent(pendIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);

    }

    public static void unPlay() {
        isPlaying = false;
    }

    public static void play() {
        isPlaying = true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.infos:
                Intent in = new Intent(this, SecondActivity.class);
                startActivity(in);
                GetBiersServices.startActionBeer(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



