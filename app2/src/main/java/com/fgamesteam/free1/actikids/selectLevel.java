package com.fgamesteam.free1.actikids;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class selectLevel extends AppCompatActivity {
public final static String MESSAGE_KEY="com.example.pawel.myapplication2.message_key";
    public final static String MESSAGE_KEY_SELECT_LEVEL="com.example.pawel.myapplication2.message_key3";

    private ImageView level1new;
    private ImageView level2new;
    private ImageView level3new;
    private ImageView levels;
    private ImageView levels2;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        level1new = (ImageView) findViewById(R.id.imageView14);
        level2new= (ImageView) findViewById(R.id.imageView15);
        level3new= (ImageView) findViewById(R.id.imageView16);
        levels= (ImageView) findViewById(R.id.imageView13);
        levels2= (ImageView) findViewById(R.id.imageView39);
        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_AppID));
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
               // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        try{  
            if(Locale.getDefault().getLanguage().equals("pl")){
                Picasso.with(this).load(R.drawable.level1new_pl).into(level1new);
                Picasso.with(this).load(R.drawable.level2new_pl).into(level2new);
                Picasso.with(this).load(R.drawable.level3new_pl).into(level3new);
                Picasso.with(this).load(R.drawable.levelscreen_pl).into(levels);
                Picasso.with(this).load(R.drawable.levelscreen4_pl).into(levels2);}
            else if (Locale.getDefault().getLanguage().equals("ru"))
            {  Picasso.with(this).load(R.drawable.level1new_ru).into(level1new);
                Picasso.with(this).load(R.drawable.level2new_ru).into(level2new);
                Picasso.with(this).load(R.drawable.level3new_ru).into(level3new);
                Picasso.with(this).load(R.drawable.levelscreen_ru).into(levels);
                Picasso.with(this).load(R.drawable.levelscreen4_ru).into(levels2);}
            else { Picasso.with(this).load(R.drawable.level1new).into(level1new);
                Picasso.with(this).load(R.drawable.level2new).into(level2new);
                Picasso.with(this).load(R.drawable.level3new).into(level3new);
                Picasso.with(this).load(R.drawable.levelscreen).into(levels);
                Picasso.with(this).load(R.drawable.levelscreen4_en).into(levels2);}
        }
        catch(Exception e){Picasso.with(this).load(R.drawable.level1new).into(level1new);
            Picasso.with(this).load(R.drawable.level2new).into(level2new);
            Picasso.with(this).load(R.drawable.level3new).into(level3new);
            Picasso.with(this).load(R.drawable.levelscreen).into(levels);
            Picasso.with(this).load(R.drawable.levelscreen4_en).into(levels2);}

    }

    public void OpenLevel1Activity(View view){
        String button_tekst;
        button_tekst=((Button)view).getText().toString();
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);

        if(button_tekst.equals("Poziom A"))
        {
             String message="Poziom A";
            intentt.putExtra(MESSAGE_KEY_SELECT_LEVEL,message);
            startActivity(intentt);
        }
         else if(button_tekst.equals("Poziom B"))
            {String message="Poziom B";
                intentt.putExtra(MESSAGE_KEY_SELECT_LEVEL,message);
                startActivity(intentt);}
        else if(button_tekst.equals("Poziom C"))
            {String message="Poziom C";
                intentt.putExtra(MESSAGE_KEY_SELECT_LEVEL,message);
                startActivity(intentt);}

        else if(button_tekst.equals("Kontynuuj ostatnią grę A"))
        {
            String message="level A continue";
            intentt.putExtra(MESSAGE_KEY,message);
            startActivity(intentt);
        }
        else if(button_tekst.equals("Kontynuuj ostatnią grę B"))
        {
            String message="level B continue";
            intentt.putExtra(MESSAGE_KEY,message);
            startActivity(intentt);
        }
        else if(button_tekst.equals("Kontynuuj ostatnią grę C"))
        {
               String message="level C continue";
               intentt.putExtra(MESSAGE_KEY,message);
               startActivity(intentt);
        }
    }
    public void OpenGraA(View view) {
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="Poziom A";
        intentt.putExtra(MESSAGE_KEY_SELECT_LEVEL,message);
        startActivity(intentt);
    }
    public void OpenGraB(View view) {
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="Poziom B";
        intentt.putExtra(MESSAGE_KEY_SELECT_LEVEL,message);
        startActivity(intentt);
    }
    public void OpenGraC(View view) {
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="Poziom C";
        intentt.putExtra(MESSAGE_KEY_SELECT_LEVEL,message);
        startActivity(intentt);
    }
    public void ContinueGraA(View view) {
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="level A continue";
        intentt.putExtra(MESSAGE_KEY,message);
        startActivity(intentt);
    }
    public void ContinueGraB(View view) {
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="level B continue";
        intentt.putExtra(MESSAGE_KEY,message);
        startActivity(intentt);
    }
    public void ContinueGraC(View view) {
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="level C continue";
        intentt.putExtra(MESSAGE_KEY,message);
        startActivity(intentt);
    }
}

