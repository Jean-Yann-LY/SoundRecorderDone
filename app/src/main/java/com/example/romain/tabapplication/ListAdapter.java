package com.example.romain.tabapplication;


import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<String> {

    //ArrayList<String> tabTitles = new ArrayList<>();

    public ListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
    }

    /*public void add(String title){
        tabTitles.add(title);
    }*/
}
