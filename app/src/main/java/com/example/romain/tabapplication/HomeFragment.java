package com.example.romain.tabapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    static ImageButton btnCancel;

    static ToggleButton btnRecord;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        btnRecord = (ToggleButton)rootView.findViewById(R.id.recordButton);
        btnCancel = (ImageButton)rootView.findViewById(R.id.cancelButton);




        // Inflate the layout for this fragment
        return rootView;
    }

    public static void unRecord(){
        btnRecord.setChecked(false);
    }

    public static void cancelAppear(){
        btnCancel.setClickable(true);
        btnCancel.setVisibility(View.VISIBLE);
    }

    public static void cancelDisappear(){

        btnCancel.setClickable(false);
        btnCancel.setVisibility(View.INVISIBLE);
    }
}
