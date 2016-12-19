package com.example.romain.tabapplication;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFreeFragment extends Fragment {



    ListView listView;

    static ArrayAdapter<String> listAdapter;


    static ToggleButton btnPlay;


    public TopFreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_top_free, container, false);

        btnPlay = (ToggleButton)rootView.findViewById(R.id.playButton);

        listView = (ListView)rootView.findViewById( R.id.listView );

        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow) {};




        listView.setAdapter( listAdapter );
        addSongsToList();
        update();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();

                try {
                    if(MainActivity.isPlaying){
                        PlayerUtil.stop();
                    }
                    play();
                    MainActivity.play();
                    MainActivity.iPlay = listAdapter.getPosition(item);
                    PlayerUtil.start(listAdapter.getPosition(item));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), item, Toast.LENGTH_LONG).show();


            }
        });


        return rootView;
    }

    public static void unPlay(){
        btnPlay.setChecked(false);
    }

    public static void play(){
        btnPlay.setChecked(true);
    }


    public static void addSongsToList(){
        File audiosDirectory = new File(Environment.getExternalStorageDirectory()
                + "/Audios/");
        if(!audiosDirectory.exists()) {
            audiosDirectory.mkdirs();
        }
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(audiosDirectory.list()));
        listAdapter.clear();
        listAdapter.addAll(names);
    }

    public static void update(){
        listAdapter.notifyDataSetChanged();
    }



}

