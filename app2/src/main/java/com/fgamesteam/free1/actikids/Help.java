package com.fgamesteam.free1.actikids;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by pawel on 2016-05-24.
 */
public class Help extends Activity {
    public final static String MESSAGE_KEY_HELP="com.example.pawel.myapplication2.message_key2";

    VideoView video;
    public String EditTextValue;
    private ImageView pomoc;
    private ImageView pomoc2;
    Context contexxt;
    Boolean CzyjuzZaladowano2obrazek = false;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
       // video = (VideoView) findViewById(R.id.videoView);
        pomoc = (ImageView) findViewById(R.id.imageView10);
        pomoc2 = (ImageView) findViewById(R.id.imageView11);
        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_AppID));
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
              //  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        contexxt=getApplicationContext();
        try{
            if(Locale.getDefault().getLanguage().equals("pl")){
                Picasso.with(contexxt).load(R.drawable.helpscreen1_pl).into(pomoc); }
            else if (Locale.getDefault().getLanguage().equals("ru"))
            { Picasso.with(contexxt).load(R.drawable.helpscreen1_ru).into(pomoc); }
            else {Picasso.with(contexxt).load(R.drawable.helpscreen1).into(pomoc); }
            }
        catch(Exception e){Picasso.with(contexxt).load(R.drawable.helpscreen1).into(pomoc); }


     }

    private void LoadImages(){
        Thread mythread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                       // pomoc2.setVisibility(View.INVISIBLE);
                          Picasso.with(contexxt).load(R.drawable.helpscreen2).into(pomoc2);

                        try{
                            if(Locale.getDefault().getLanguage().equals("pl")){
                                Picasso.with(contexxt).load(R.drawable.helpscreen2_pl).into(pomoc2); }
                            else if (Locale.getDefault().getLanguage().equals("ru"))
                            { Picasso.with(contexxt).load(R.drawable.helpscreen2_ru).into(pomoc2);}
                            else {Picasso.with(contexxt).load(R.drawable.helpscreen2).into(pomoc2); }
                        }
                        catch(Exception e){Picasso.with(contexxt).load(R.drawable.helpscreen2).into(pomoc2); }
                    }
                });
            }
        };
        mythread.start();
    }

    public void  ZmienObrazek(View view){
        if(CzyjuzZaladowano2obrazek==false)//opóźnienie załadowania drugiego obrazka
        { LoadImages();
            CzyjuzZaladowano2obrazek=true;
        }
        if(pomoc2.getVisibility() == View.INVISIBLE)
        {pomoc.setVisibility(View.INVISIBLE);
           pomoc2.setVisibility(View.VISIBLE);
        }
else{pomoc.setVisibility(View.VISIBLE);
            pomoc2.setVisibility(View.INVISIBLE);
        }
       }
    public void OpenLinkToFilm(View view){
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
        Uri uri=Uri.parse("rtsp://r11---sn-4g57kn7l.googlevideo.com/Cj0LENy73wIaNAnU-KHQfDEdpRMYDSANFC3VoURXMOCoAUIASARgnpLyn-fSs5pXigELSmYtNk4tN3FWS1UM/678FC2B9DE98C2F19AFCE9E037B378EB61FA4F8C.390EBA4F6932826AFC09A1C19E22A31E8C33CB37/yt6/1/video.3gp");
        video.setMediaController(mediaController);
        video.setVideoURI(uri);
       // video.requestFocus();
        video.start();
    }

    public void OpenHelpVideo(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=UGIAZFjQj2c"));
        startActivity(browserIntent);}
    public void OpenVideo(View view) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(video);
       // Uri videouri = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.clip); //do not add any extension
        video.setMediaController(mediaController);
      //  video.setVideoURI(videouri);
        video.start();
    }

    public void Increase(final View view) {
        final android.content.Intent intenttt = new android.content.Intent(this, Gra.class);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setMessage("Wprowadź 4-cyfrowy klucz");
        alert.setTitle("Zwiększ ilość karteczek");
        alert.setView(edittext);
        alert.setPositiveButton("Zatwierdź", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                EditTextValue = edittext.getText().toString();

                if(EditTextValue.equals("1234")){
                    String message = "Increase";
                    intenttt.putExtra(MESSAGE_KEY_HELP, message);
                    startActivity(intenttt);}
                else if(EditTextValue.equals("12345")){
                    String message = "Increase2";
                    intenttt.putExtra(MESSAGE_KEY_HELP, message);
                    startActivity(intenttt);}
                else {
                    Toast.makeText(getApplicationContext(),"Niepoprawny klucz.", Toast.LENGTH_SHORT).show();}
            }
        });
        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
        alert.show();    }


    }








