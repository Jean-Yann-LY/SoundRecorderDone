package com.example.romain.tabapplication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 *
 */
public class GetBiersServices extends IntentService {

    public GetBiersServices() {
        super("GetBiersServices");
    }


    public static void startActionBeer(Context context) {
        Intent intent = new Intent(context, GetBiersServices.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        handleActionBeer();
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBeer() {
        Log.d("threading", "Thread service name:" + Thread.currentThread().getName());
        URL url = null;
        try{
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
                copyInputStreamToFile(conn.getInputStream(),
                        new File(getCacheDir(), "bieres.json"));
                Log.d("Thread", "Bieres json downloaded !");
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SecondActivity.BIERS_UPDATE));

        //LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);
    }

    private void copyInputStreamToFile(InputStream in, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }


}
