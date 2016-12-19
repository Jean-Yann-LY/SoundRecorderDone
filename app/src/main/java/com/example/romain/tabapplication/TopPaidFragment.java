package com.example.romain.tabapplication;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopPaidFragment extends Fragment {

    static String output = "THREE_GPP";

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    public TopPaidFragment() {
        // Required empty public constructor
    }

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {

        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_top_paid);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Picasso.with(this)
                .load("https://cms-assets.tutsplus.com/uploads/users/21/posts/19431/featured_image/CodeFeature.jpg")
                .into(imageView);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_top_paid, container, false);



        spinner = (Spinner)rootView.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getContext(),R.array.encoding,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(/*R.layout.spinner_item*/ android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                output = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //System.out.println(rootView);
        return rootView;
    }

}
