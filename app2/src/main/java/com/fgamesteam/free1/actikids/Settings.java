package com.fgamesteam.free1.actikids;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class Settings extends AppCompatActivity {
    public final static String MESSAGE_KEY_SETTINGS="com.example.pawel.myapplication2.message_key5";
    public static CheckBox DisableSound;
    public static CheckBox DisableAlarm;
    public static SeekBar IleStoper;
    private ImageView ZapiszUstawienia;
    private ImageView Settings0;
    private ImageView Settings1;
    public static ImageView Polecenia_glosowe_on;
    public static ImageView Polecenia_glosowe_off;
    public static ImageView Alarm_on;
    public static ImageView Alarm_off;
    public static TextView Licznik;
    public static Integer StoperCzas=30;
    public static Integer DisableSoundd=0;//0- wyłączone, 1 - włączone
    public static Integer DisableAlarmm=0;
    public boolean CzyJuzByłoRecreate=false;
    public Context Settingskontekst;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingss);

      //  DisableSound=(CheckBox) findViewById(R.id.checkBox);
     //   DisableAlarm=(CheckBox) findViewById(R.id.checkBox2);
        IleStoper=(SeekBar) findViewById(R.id.seekBar);
        ZapiszUstawienia=(ImageView) findViewById(R.id.imageView11);
        Licznik=(TextView) findViewById(R.id.textView7);
        Settings0=(ImageView) findViewById(R.id.imageView37);
        Settings1=(ImageView) findViewById(R.id.imageView38);
        Polecenia_glosowe_on=(ImageView) findViewById(R.id.imageView40);
        Polecenia_glosowe_off=(ImageView) findViewById(R.id.imageView41);
        Alarm_on=(ImageView) findViewById(R.id.imageView42);
        Alarm_off=(ImageView) findViewById(R.id.imageView43);
        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_AppID));
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
             //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        try{ 
            if(Locale.getDefault().getLanguage().equals("pl")){
                Picasso.with(this).load(R.drawable.settingsscreen0_pl).into(Settings0);
                Picasso.with(this).load(R.drawable.settingsscreen1_pl).into(Settings1);}
            else if (Locale.getDefault().getLanguage().equals("ru"))
            { Picasso.with(this).load(R.drawable.settingsscreen0_ru).into(Settings0);
                Picasso.with(this).load(R.drawable.settingsscreen1_ru).into(Settings1); }
            else {Picasso.with(this).load(R.drawable.settingsscreen0).into(Settings0);
                Picasso.with(this).load(R.drawable.settingsscreen1).into(Settings1); }
        }
        catch(Exception e){Picasso.with(this).load(R.drawable.settingsscreen0).into(Settings0);
            Picasso.with(this).load(R.drawable.settingsscreen1).into(Settings1);}


        //opening Gra activity to read info from register about actual settings
        android.content.Intent intentt = new android.content.Intent(this,Gra.class);
        String message="s";
        intentt.putExtra(MESSAGE_KEY_SETTINGS,message);
        startActivity(intentt);

         IleStoper.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(),R.string.ReaminderSettingsSave, Toast.LENGTH_SHORT).show();//przypomnienie o konieczności naciśnięcia przycisku zapisz
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                StoperCzas = progress+5;
                Licznik.setText(String.valueOf(StoperCzas+"s"));
            }
        });
        ZapiszUstawienia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveSettings();
            }
        });
    }
    public void  ZmienObrazek(View view){
        if(Polecenia_glosowe_on.getVisibility() == View.INVISIBLE)
        {Polecenia_glosowe_off.setVisibility(View.INVISIBLE);
            Polecenia_glosowe_on.setVisibility(View.VISIBLE);
        }
        else{Polecenia_glosowe_off.setVisibility(View.VISIBLE);
            Polecenia_glosowe_on.setVisibility(View.INVISIBLE);
        }
        Toast.makeText(getApplicationContext(),R.string.ReaminderSettingsSave, Toast.LENGTH_SHORT).show();//przypomnienie o konieczności naciśnięcia przycisku zapisz
    }
    public void  ZmienObrazek2(View view){
        if(Alarm_on.getVisibility() == View.INVISIBLE)
        {Alarm_off.setVisibility(View.INVISIBLE);
            Alarm_on.setVisibility(View.VISIBLE);
        }
        else{Alarm_off.setVisibility(View.VISIBLE);
            Alarm_on.setVisibility(View.INVISIBLE);
        }
        Toast.makeText(getApplicationContext(),R.string.ReaminderSettingsSave, Toast.LENGTH_SHORT).show();//przypomnienie o konieczności naciśnięcia przycisku zapisz
    }
        void SaveSettings(){
            android.content.Intent intentt = new android.content.Intent(this,Gra.class);
       //     if(DisableSound.isChecked())
       //     {DisableSoundd=1;}
       //     else {DisableSoundd=0;}
       //    if(DisableAlarm.isChecked())
        //    {DisableAlarmm=1;}
       //    else {DisableAlarmm=0;}

            if(Polecenia_glosowe_on.getVisibility() == View.INVISIBLE)
            {DisableSoundd=1;}
            else {DisableSoundd=0;}
            if(Alarm_on.getVisibility() == View.INVISIBLE)
            {DisableAlarmm=1;}
            else {DisableAlarmm=0;}

            String message=DisableSoundd.toString()+","+StoperCzas.toString()+","+DisableAlarmm.toString();
            intentt.putExtra(MESSAGE_KEY_SETTINGS,message);
            startActivity(intentt);
            Toast.makeText(getApplicationContext(),R.string.SettingsSaved,Toast.LENGTH_SHORT).show();
        }


}
