package com.example.romain.tabapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SecondActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    public static final String BIERS_UPDATE = "com.octip.cours.inf4042_11BIERS_UPDATE";
    private BieresAdapter mAdapter;
    private RecyclerView rvbiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        rvbiere = (RecyclerView)findViewById(R.id.rv_biere);
        mAdapter = new BieresAdapter(getBiersFromFile());

        rvbiere.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvbiere.setAdapter(mAdapter);
        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);

        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Informations");
        mActionBarToolbar.setTitle(Html.fromHtml("<font color='#ffffff'>Informations </font>"));

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Picasso.with(this)
                .load("https://puu.sh/sUC1s/062237b562.jpg")
                .into(imageView);
    }

    public class BierUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context , Intent intent){
            Log.d("download",getIntent().getAction());
            Toast.makeText(getApplicationContext(), getString(R.string.download), Toast.LENGTH_LONG).show();
            mAdapter.setNewBiere(getBiersFromFile());
            rvbiere.getAdapter();

        }
    }

    public JSONArray getBiersFromFile(){
        try{
            InputStream is = new FileInputStream(getCacheDir()+"/"+"bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer,"UTF-8"));
        }
        catch (IOException e){
            e.printStackTrace();
            return new JSONArray();
        }
        catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }



}
