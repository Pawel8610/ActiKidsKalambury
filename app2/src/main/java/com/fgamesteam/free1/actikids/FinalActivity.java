package com.fgamesteam.free1.actikids;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by pawel on 2016-09-01.
 */
public class FinalActivity extends Activity {
    public final static String MESSAGE_KEY="com.example.pawel.myapplication2.message_key";
    public final static String MESSAGE_KEY_SELECT_LEVEL="com.example.pawel.myapplication2.message_key3";
    private ImageView finalscreen;
    private ImageView finalscreen2;
    private ImageView pack1;
    private ImageView pack2;
    private ImageView pack3;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.finall);
        finalscreen=(ImageView) findViewById(R.id.imageView22);
        finalscreen2=(ImageView) findViewById(R.id.imageView29);
        pack1=(ImageView) findViewById(R.id.imageView23);
        pack1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fgamesteam.pack1.actikids"));
                startActivity(browserIntent);
            }
        });
        pack2=(ImageView) findViewById(R.id.imageView24);
        pack2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fgamesteam.pack2.actikids"));
                startActivity(browserIntent);
            }
        });
        pack3=(ImageView) findViewById(R.id.imageView25);
        pack3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fgamesteam.pack3.actikids"));
                startActivity(browserIntent);
            }
        });

        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_AppID));
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        try{  
            if(Locale.getDefault().getLanguage().equals("pl")){
                Picasso.with(this).load(R.drawable.finalscreen_pl).into(finalscreen);
                Picasso.with(this).load(R.drawable.finalscreen2_pl).into(finalscreen2);}
            else if (Locale.getDefault().getLanguage().equals("ru"))
            { Picasso.with(this).load(R.drawable.finalscreen_ru).into(finalscreen);
                Picasso.with(this).load(R.drawable.finalscreen2_ru).into(finalscreen2); }
            else {Picasso.with(this).load(R.drawable.finalscreen).into(finalscreen);
                Picasso.with(this).load(R.drawable.finalscreen2).into(finalscreen2); }
        }
        catch(Exception e){Picasso.with(this).load(R.drawable.finalscreen).into(finalscreen);
            Picasso.with(this).load(R.drawable.finalscreen2).into(finalscreen2);}
    }

    public void OpenGraA(View view) {
        Gra.activityy.finish();
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="Poziom A";
        intentt.putExtra(MESSAGE_KEY_SELECT_LEVEL,message);
        startActivity(intentt);
    }
    public void OpenGraB(View view) {
        Gra.activityy.finish();
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="Poziom B";
        intentt.putExtra(MESSAGE_KEY_SELECT_LEVEL,message);
        startActivity(intentt);
    }
    public void OpenGraC(View view) {
        Gra.activityy.finish();
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="Poziom C";
        intentt.putExtra(MESSAGE_KEY_SELECT_LEVEL,message);
        startActivity(intentt);
    }
    public void ContinueGraA(View view) {
        Gra.activityy.finish();
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="level A continue";
        intentt.putExtra(MESSAGE_KEY,message);
        startActivity(intentt);
    }
    public void ContinueGraB(View view) {
        Gra.activityy.finish();
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="level B continue";
        intentt.putExtra(MESSAGE_KEY,message);
        startActivity(intentt);
    }
    public void ContinueGraC(View view) {
        Gra.activityy.finish();
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="level C continue";
        intentt.putExtra(MESSAGE_KEY,message);
        startActivity(intentt);
    }

}
