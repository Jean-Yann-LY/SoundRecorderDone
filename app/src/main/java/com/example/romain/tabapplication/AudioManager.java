package com.example.romain.tabapplication;

import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AudioManager {

    EditText ed;
    TextView result;

    protected void createText(){
        //ed = (EditText) findViewById(R.id.contenttxt);
        //result = (TextView) findViewById(R.id.resulttxt);

        try {
            // this will create a new name everytime and unique
            File root = new File(Environment.getExternalStorageDirectory(), "Audios");
            // if external memory exists and folder with name Notes
            if (!root.exists()) {
                root.mkdirs(); // this will create folder.
            }
            File filepath = new File(root,".txt");  // file path to save
            FileWriter writer = new FileWriter(filepath);
            writer.append(ed.getText().toString());
            writer.flush();
            writer.close();
            String m = "File generated with name " + ".txt";
            result.setText(m);

        } catch (IOException e) {
            e.printStackTrace();
            result.setText(e.getMessage().toString());
        }
    }

    public void WriteSettings(Context context, String data){
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;

        try{
            //fOut = context.openFileOutput("audios.txt",MODE_APPEND);
            osw = new OutputStreamWriter(fOut);
            osw.write(data);
            osw.flush();
            //popup surgissant pour le résultat
            Toast.makeText(context, "Audio saved",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Toast.makeText(context, "Audio not saved",Toast.LENGTH_SHORT).show();
        }
        finally {
            try {
                osw.close();
                fOut.close();
            } catch (IOException e) {
                Toast.makeText(context, "Audio not saved",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
