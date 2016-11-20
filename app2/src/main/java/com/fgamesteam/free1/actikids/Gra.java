package com.fgamesteam.free1.actikids;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by pawel on 2016-05-15.
 */
public class Gra extends Activity {
    public final static String MESSAGE_KEY_INCREASE_DIALOG = "com.example.pawel.myapplication2.message_key4";
    public final static String MESSAGE_KEY = "com.example.pawel.myapplication2.message_key";
    public final static String MESSAGE_KEY_HELP = "com.example.pawel.myapplication2.message_key2";
    public final static String MESSAGE_KEY_SELECT_LEVEL = "com.example.pawel.myapplication2.message_key3";
    public final static String MESSAGE_KEY_SETTINGS="com.example.pawel.myapplication2.message_key5";
    public Dialog dialog;
    private ImageView mImageView;
    private ImageView mImageViewProtect;
    private ImageView FirstFrame;
    private ImageView przejscioweFrame;
    private ImageView pomoc;
    private ImageView graj;
    ImageView zadanie;
    TextView counter;
    TextView stoper;
    String displayLevel;
    CallbackManager callbackManager;
    LoginManager manager;
    public static Context contexxt;
    public static Activity activityy;
    BitmapWorkerTask task =null;
    PokazObrazAsyncTask pokazobraz = null;
    ArrayList<Integer> pula = new ArrayList<Integer>(110);
    ArrayList<Integer> Temppula = new ArrayList<Integer>(110);
    ArrayList<Integer> Temppula2 = new ArrayList<Integer>(110);
    ArrayList<Integer> Temppula3 = new ArrayList<Integer>(110);
    private float x1, x2;
    public int[] previousTask = new int[2];
    public Integer LastImage = null;
    public Integer LastTask = null;
    public Integer ActivityLeft1 =2;//aktywności do wykorzystania
    public Integer ActivityLeft2 =2;
    public Integer ActivityLeft3 =2;
    public Integer ActivityLeft4 =2;
    public Integer ActivityLeft5 =2;
    public Integer ImageCount = 10;
    public Integer PreviousImageCount = 10;
    public Integer StoperCzas = 30000;
    public Stoper stoperr;
    final StoperImageIndicator stoperImage = new StoperImageIndicator(500,500);
    public Integer DisableSoundd=0;
    public Integer DisableAlarmm=0;
    boolean Czystoperdziala = false;
    boolean CzystoperdzialaImage = false;
    boolean CzyZapisanoGre=true;//aby przed rozpoczęciem gry jak kliknę back nie krzyczało że nie zapisane
    MediaPlayer mp;
    MediaPlayer mp_zadan;
    int[] images;
    int[]zadania;
    int[]dzwieki_zadan;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    ScaleAnimation scaleAnim;
    private AdView mAdView;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gra);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        zadanie = (ImageView) findViewById(R.id.imageView3);
        counter = (TextView) findViewById(R.id.textView4licznik);
        stoper = (TextView) findViewById(R.id.textView5);
        pomoc = (ImageView) findViewById(R.id.imageView9);
         pomoc.setOnClickListener(new View.OnClickListener() {
             public void onClick(View view) {
                 android.content.Intent intentt = new android.content.Intent(Gra.this,Help.class);
                 startActivity(intentt);
             }
         });
         graj=(ImageView) findViewById(R.id.imageView6);
         scaleAnim = new ScaleAnimation(1.0f, 1.03f, 1.0f, 1.03f);//animacja skalowania
         scaleAnim.setRepeatCount(Animation.INFINITE);
         scaleAnim.setDuration(1000);
         graj.startAnimation(scaleAnim);

         mImageView = (ImageView) findViewById(R.id.imageView);
         mImageViewProtect = (ImageView) findViewById(R.id.imageViewProtect);
         FirstFrame= (ImageView) findViewById(R.id.imageViewFrame);
         przejscioweFrame= (ImageView) findViewById(R.id.imageViewprzejsciowe);
         MobileAds.initialize(this, String.valueOf(R.string.banner_ad_AppID));
         mAdView = (AdView) findViewById(R.id.ad_view);
         AdRequest adRequest = new AdRequest.Builder()
                // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                 .build();
         mAdView.loadAd(adRequest);
         activityy = this;
         contexxt=getApplicationContext();
         previousTask = new int[]{-1,-1};

         przejscioweFrame.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        try {
            String message3 = intent.getStringExtra(MESSAGE_KEY_SELECT_LEVEL);
            if (message3.equals("Poziom A")) {
                images = new int[]{R.drawable.a1 ,R.drawable.a2 ,R.drawable.a3 ,R.drawable.a4 ,R.drawable.a5 ,R.drawable.a6 ,R.drawable.a7 ,R.drawable.a8 ,R.drawable.a9 ,R.drawable.a10 ,R.drawable.a11 ,R.drawable.a12 ,R.drawable.a13 ,R.drawable.a14 ,R.drawable.a15 ,R.drawable.a16 ,R.drawable.a17 ,R.drawable.a18 ,R.drawable.a19 ,R.drawable.a20 ,R.drawable.a21 ,R.drawable.a22 ,R.drawable.a23 ,R.drawable.a24 ,R.drawable.a25 ,R.drawable.a26 ,R.drawable.a27 ,R.drawable.a28 ,R.drawable.a29 ,R.drawable.a30};
                displayLevel="Poziom A";
                Picasso.with(this).load(R.drawable.firstframe).into(FirstFrame);
                Picasso.with(this).load(R.drawable.protect).into(mImageViewProtect);
                Picasso.with(contexxt).load(R.drawable.przejsciowy).into(przejscioweFrame);

                Picasso.with(this).load(R.drawable.z0).into(zadanie);
                zadania=new int[]{R.drawable.z1,R.drawable.z2,R.drawable.z3};
            } else if (message3.equals("Poziom B")) {
                images = new int[]{R.drawable.b1 ,R.drawable.b2 ,R.drawable.b3 ,R.drawable.b4 ,R.drawable.b5 ,R.drawable.b6 ,R.drawable.b7 ,R.drawable.b8 ,R.drawable.b9 ,R.drawable.b10 ,R.drawable.b11 ,R.drawable.b12 ,R.drawable.b13 ,R.drawable.b14 ,R.drawable.b15 ,R.drawable.b16 ,R.drawable.b17 ,R.drawable.b18 ,R.drawable.b19 ,R.drawable.b20 ,R.drawable.b21 ,R.drawable.b22 ,R.drawable.b23 ,R.drawable.b24 ,R.drawable.b25 ,R.drawable.b26 ,R.drawable.b27 ,R.drawable.b28 ,R.drawable.b29 ,R.drawable.b30};
                displayLevel="Poziom B";
                Picasso.with(this).load(R.drawable.firstframeb).into(FirstFrame);
                Picasso.with(this).load(R.drawable.protectb).into(mImageViewProtect);
                Picasso.with(contexxt).load(R.drawable.przejsciowyb).into(przejscioweFrame);

                Picasso.with(this).load(R.drawable.z0m).into(zadanie);
                zadania=new int[]{R.drawable.z1m,R.drawable.z2m,R.drawable.z3m};
            } else if (message3.equals("Poziom C")) {
                images = new int[]{R.drawable.c1 ,R.drawable.c2 ,R.drawable.c3 ,R.drawable.c4 ,R.drawable.c5 ,R.drawable.c6 ,R.drawable.c7 ,R.drawable.c8 ,R.drawable.c9 ,R.drawable.c10 ,R.drawable.c11 ,R.drawable.c12 ,R.drawable.c13 ,R.drawable.c14 ,R.drawable.c15 ,R.drawable.c16 ,R.drawable.c17 ,R.drawable.c18 ,R.drawable.c19 ,R.drawable.c20 ,R.drawable.c21 ,R.drawable.c22 ,R.drawable.c23 ,R.drawable.c24 ,R.drawable.c25 ,R.drawable.c26 ,R.drawable.c27 ,R.drawable.c28 ,R.drawable.c29 ,R.drawable.c30};
                displayLevel="Poziom C";
                Picasso.with(this).load(R.drawable.firstframec).into(FirstFrame);
                Picasso.with(this).load(R.drawable.protectc).into(mImageViewProtect);
                Picasso.with(contexxt).load(R.drawable.przejsciowyc).into(przejscioweFrame);

                Picasso.with(this).load(R.drawable.z0h).into(zadanie);
                zadania=new int[]{R.drawable.z1h,R.drawable.z2h,R.drawable.z3h};
            }
        }
             catch (Exception e) {}
        try {
        String message = intent.getStringExtra(MESSAGE_KEY);

        if (message.equals("level A continue")) {
            images = new int[]{R.drawable.a1 ,R.drawable.a2 ,R.drawable.a3 ,R.drawable.a4 ,R.drawable.a5 ,R.drawable.a6 ,R.drawable.a7 ,R.drawable.a8 ,R.drawable.a9 ,R.drawable.a10 ,R.drawable.a11 ,R.drawable.a12 ,R.drawable.a13 ,R.drawable.a14 ,R.drawable.a15 ,R.drawable.a16 ,R.drawable.a17 ,R.drawable.a18 ,R.drawable.a19 ,R.drawable.a20 ,R.drawable.a21 ,R.drawable.a22 ,R.drawable.a23 ,R.drawable.a24 ,R.drawable.a25 ,R.drawable.a26 ,R.drawable.a27 ,R.drawable.a28 ,R.drawable.a29 ,R.drawable.a30};
            displayLevel="Poziom A";
            Picasso.with(this).load(R.drawable.protect).into(mImageViewProtect);
            Picasso.with(contexxt).load(R.drawable.przejsciowy).into(przejscioweFrame);

        //    Picasso.with(this).load(R.drawable.z0).into(zadanie);
            zadania=new int[]{R.drawable.z1,R.drawable.z2,R.drawable.z3};
        } else if (message.equals("level B continue")) {
            images = new int[]{R.drawable.b1 ,R.drawable.b2 ,R.drawable.b3 ,R.drawable.b4 ,R.drawable.b5 ,R.drawable.b6 ,R.drawable.b7 ,R.drawable.b8 ,R.drawable.b9 ,R.drawable.b10 ,R.drawable.b11 ,R.drawable.b12 ,R.drawable.b13 ,R.drawable.b14 ,R.drawable.b15 ,R.drawable.b16 ,R.drawable.b17 ,R.drawable.b18 ,R.drawable.b19 ,R.drawable.b20 ,R.drawable.b21 ,R.drawable.b22 ,R.drawable.b23 ,R.drawable.b24 ,R.drawable.b25 ,R.drawable.b26 ,R.drawable.b27 ,R.drawable.b28 ,R.drawable.b29 ,R.drawable.b30};
            displayLevel="Poziom B";
            Picasso.with(this).load(R.drawable.protectb).into(mImageViewProtect);
            Picasso.with(contexxt).load(R.drawable.przejsciowyb).into(przejscioweFrame);

     //       Picasso.with(this).load(R.drawable.z0m).into(zadanie);
            zadania=new int[]{R.drawable.z1m,R.drawable.z2m,R.drawable.z3m};
        } else if (message.equals("level C continue")) {
            images = new int[]{R.drawable.c1 ,R.drawable.c2 ,R.drawable.c3 ,R.drawable.c4 ,R.drawable.c5 ,R.drawable.c6 ,R.drawable.c7 ,R.drawable.c8 ,R.drawable.c9 ,R.drawable.c10 ,R.drawable.c11 ,R.drawable.c12 ,R.drawable.c13 ,R.drawable.c14 ,R.drawable.c15 ,R.drawable.c16 ,R.drawable.c17 ,R.drawable.c18 ,R.drawable.c19 ,R.drawable.c20 ,R.drawable.c21 ,R.drawable.c22 ,R.drawable.c23 ,R.drawable.c24 ,R.drawable.c25 ,R.drawable.c26 ,R.drawable.c27 ,R.drawable.c28 ,R.drawable.c29 ,R.drawable.c30};
            displayLevel="Poziom C";
            Picasso.with(this).load(R.drawable.protectc).into(mImageViewProtect);
            Picasso.with(contexxt).load(R.drawable.przejsciowyc).into(przejscioweFrame);

       //     Picasso.with(this).load(R.drawable.z0h).into(zadanie);
            zadania=new int[]{R.drawable.z1h,R.drawable.z2h,R.drawable.z3h};
        }}
        catch (Exception ee) { }
         try{
         if(Locale.getDefault().getLanguage().equals("pl")){
               dzwieki_zadan=new int[]{R.raw.draw_pl,R.raw.describe_pl,R.raw.show_pl};}
         else if (Locale.getDefault().getLanguage().equals("ru"))
         {dzwieki_zadan=new int[]{R.raw.draw_ru,R.raw.describe_ru,R.raw.show_ru};}
         else {dzwieki_zadan=new int[]{R.raw.draw_en,R.raw.describe_en,R.raw.show_en};
         }}
         catch(Exception e){
             dzwieki_zadan=new int[]{R.raw.draw_en,R.raw.describe_en,R.raw.show_en};
         }
//zdarzenia dotyku:
 //       mImageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
 //           public boolean onTouch(View v, MotionEvent event) {
 //             //  mImageViewProtect.setVisibility(View.INVISIBLE);
 //               int action = event.getActionMasked();
 //               if (action == MotionEvent.ACTION_DOWN) {   //PokazObraz(mImageView);
 //                   mImageViewProtect.setVisibility(View.INVISIBLE);
 //                   mImageView.setVisibility(View.VISIBLE);
 //                  return true;
 //               } else if (action == MotionEvent.ACTION_UP) {      // The touch just ended
 //                   mImageViewProtect.setVisibility(View.VISIBLE);
 //                   mImageView.setVisibility(View.INVISIBLE);
//                    return true;
//                }
 //               return true;
     //       }
 //       });
        mImageViewProtect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                  if (action == MotionEvent.ACTION_DOWN) {
              //      mImageViewProtect.setVisibility(View.INVISIBLE);
               //     mImageView.setVisibility(View.VISIBLE);
       //              PokazObrazThread();
 //                   PokazObraz();
                      if(FirstFrame.getDrawable()==null){ //aby nie wykonywać tego na pierwszym zdjęciu

                  //    Picasso.with(contexxt).load(R.drawable.przejsciowy).into(przejscioweFrame);
                          przejscioweFrame.setVisibility(View.VISIBLE);
                      stoperImage.start();}
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {      // The touch just ended
            //        mImageViewProtect.setVisibility(View.VISIBLE);
             //       mImageView.setVisibility(View.INVISIBLE);
     //                 PokazObrazThread();
   //                 PokazObraz();

                   //   przejscioweFrame.setVisibility(View.INVISIBLE);
                      UkryjprzejsciowyobrazThread();
                      stoperImage.cancel();
                      if(CzystoperdzialaImage==true){//jeżeli stoper (odkrycia obrazu pośredniego) nie zdążył dolecieć do końca to wywołaj metodę PokazObraz - która w tym przypadku zakryje grę z powrorem protect visible (zakrycie obrazka po puszczeniu ekranu)
                     if(pokazobraz!=null){
                     pokazobraz=null;}//w przypadku jeżeli szybko klikam to przerywam poprzednie zadanie
                    pokazobraz = new PokazObrazAsyncTask(mImageViewProtect,mImageView);
                    pokazobraz.execute();
                          CzystoperdzialaImage=false;}

                   return true;
                }
                return true;
            }
        });
        zadanie.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                x1 = event.getX();
                                break;
                            case MotionEvent.ACTION_UP:
                                x2 = event.getX();
                                float deltaX = x2 - x1;
                                if (deltaX < -100) {
                                    if (previousTask[0]>=0||previousTask[1]>=0) { //aby nie losowało na głównym obrazku startowym
                                    LosujZadania(true);//następne ma być inne niż obecne
                                    Toast.makeText(getApplicationContext(),R.string.NextTask, Toast.LENGTH_SHORT).show();}
                                } else if (deltaX > 100) {
                                    if (previousTask[0]>=0&&previousTask[1]>=0) { //aby po pierwszym losowaniu nie wywalić gry
                                        zadanie.setImageResource(zadania[previousTask[0]]);
                                    Toast.makeText(getApplicationContext(), R.string.PreviousTask, Toast.LENGTH_SHORT).show();
                                        if(DisableSoundd==0)
                                        {play_zadanie(previousTask[0]);}
                                     }
                                }
                                break;
                        }
                        return true;
                    }
                });

        //   Set<String> set = stanGry.getStringSet("Pula",null);
        //  List<String> newConvertList = new ArrayList<String>();
        //    Gson gson = new Gson();
        //    String jsonText = stanGry.getString("Pula", null);
        //   String[] text = gson.fromJson(jsonText, String[].class);  //EDIT: gso to gson
        // pula=tinydb.getListInt("Pula2");
       //Intent intent = getIntent();
        Intent intent4 = getIntent();

        try {
            String message = intent4.getStringExtra(MESSAGE_KEY);
            if (message.equals("level A continue")) {
                TinyDB tinydb = new TinyDB(this);
                Integer x = tinydb.getInt("Image");
              if(x != null &&x!=0)  { //sprawdzam czy już coś jest zapisane - jeśli nie to Toast oraz finish - użytkownik nawet nie powinien zobaczyć okna gry
                if (tinydb.getListInt("Pula").size() >= 0)//jeżeli wcześniej ktoś zapisze stan bez obrazka, to aby po kliknięciu graj dało się grać, a nie wczytało pustą tablicę puli
                {
                    pula = tinydb.getListInt("Pula");
                }
                if (x != null&&x!=0) {
                    mImageView.setImageResource(images[tinydb.getInt("Image") - 1]);
                }
                Integer y = tinydb.getInt("Zadanie");
                if(y != null){
                    zadanie.setImageResource(zadania[tinydb.getInt("Zadanie")]);
                }
                if(mImageView.getDrawable()!=null) {
                    FirstFrame.setVisibility(View.GONE);//tylko dla warunku w metodzie graj, abym nie mógł jej kliknąć jeśli nic nie odsłoniłem
                    FirstFrame.setImageDrawable(null);//aby warunek został spełniony przy dotknięciu (brak FirstFrame) i stoper zadziałał
                }
                mImageViewProtect.setVisibility(View.VISIBLE);//zasłaniam na dzień dobry obrazek
                mImageView.setVisibility(View.INVISIBLE);
                Czystoperdziala = false;//tylko dla warunku w metodzie graj, abym nie mógł jej kliknąć jeśli nic nie odsłoniłem
                // SharedPreferences stanGry = Gra.this.getSharedPreferences(getString(R.string.PREF_FILE),MODE_PRIVATE);
            }else {Toast.makeText(getApplicationContext(),R.string.ThereIsNoSavedGame,Toast.LENGTH_SHORT).show();//jeśli dla danego poziomu nie zapisano gry to pokaż komunikat i zamknij panel gry (powrót do select level)
              finish();}}

           else if (message.equals("level B continue")) {

                TinyDB tinydb = new TinyDB(this);
                Integer x = tinydb.getInt("Image2");
                if(x != null &&x!=0)  {
                if (tinydb.getListInt("Pula2").size() >= 0)//jeżeli wcześniej ktoś zapisze stan bez obrazka, to aby po kliknięciu graj dało się grać, a nie wczytało pustą tablicę puli, zero zostawiam, bo być może ktośś zapisał stan gry na ostatnim obrazku i pula =0
                {
                    pula = tinydb.getListInt("Pula2");
                }
                if (x != null&&x!=0) {
                    mImageView.setImageResource(images[tinydb.getInt("Image2") - 1]);
                }
                Integer y = tinydb.getInt("Zadanie2");
                if(y != null){
                    zadanie.setImageResource(zadania[tinydb.getInt("Zadanie2")]);
                }
                if(mImageView.getDrawable()!=null) {
                FirstFrame.setVisibility(View.GONE);
                FirstFrame.setImageDrawable(null);}
                mImageViewProtect.setVisibility(View.VISIBLE);//zasłaniam na dzień dobry obrazek
                mImageView.setVisibility(View.INVISIBLE);
                Czystoperdziala = false;
                // SharedPreferences stanGry = Gra.this.getSharedPreferences(getString(R.string.PREF_FILE),MODE_PRIVATE);
            }else {Toast.makeText(getApplicationContext(),R.string.ThereIsNoSavedGame,Toast.LENGTH_SHORT).show();
                finish();}}
           else if (message.equals("level C continue")) {
                TinyDB tinydb = new TinyDB(this);
                Integer x = tinydb.getInt("Image3");
                if(x != null &&x!=0)  {
                if (tinydb.getListInt("Pula3").size() >= 0)//jeżeli wcześniej ktoś zapisze stan bez obrazka, to aby po kliknięciu graj dało się grać, a nie wczytało pustą tablicę puli
                {
                    pula = tinydb.getListInt("Pula3");
                }
                if (x != null&&x!=0) {
                    mImageView.setImageResource(images[tinydb.getInt("Image3") - 1]);
                }
                Integer y = tinydb.getInt("Zadanie3");
                if(y != null){
                    zadanie.setImageResource(zadania[tinydb.getInt("Zadanie3")]);
                }
                if(mImageView.getDrawable()!=null) {
                FirstFrame.setVisibility(View.GONE);
                FirstFrame.setImageDrawable(null);}
                mImageViewProtect.setVisibility(View.VISIBLE);//zasłaniam na dzień dobry obrazek
                mImageView.setVisibility(View.INVISIBLE);
                Czystoperdziala = false;
                // SharedPreferences stanGry = Gra.this.getSharedPreferences(getString(R.string.PREF_FILE),MODE_PRIVATE);
                }else {Toast.makeText(getApplicationContext(),R.string.ThereIsNoSavedGame,Toast.LENGTH_SHORT).show();
                    finish();}}
        } catch (Exception e) {
        }
try{
    TinyDB tinydb = new TinyDB(this);
    Integer x1 = tinydb.getInt("ActivitiesLeft1");
    Integer x2 = tinydb.getInt("ActivitiesLeft2");
    Integer x3 = tinydb.getInt("ActivitiesLeft3");
    Integer x4 = tinydb.getInt("ActivitiesLeft4");
    Integer x5 = tinydb.getInt("ActivitiesLeft5");
    if(x1>0){
        ActivityLeft1 = x1; }
       if(x2>0){
        ActivityLeft2 = x2; }
     if(x3>0){
        ActivityLeft3 = x3; }
     if(x4>0){
        ActivityLeft4 = x4; }
     if(x5>0){
        ActivityLeft5 = x5; }
}
catch (Exception e){SaveLeftActivities(ActivityLeft1,ActivityLeft2,ActivityLeft3,ActivityLeft4,ActivityLeft5);}//stworzenie pierwszego klucza
        //  Intent intent2 = getIntent();
//zabezpieczam się, aby nie zmniejszyć sobie liczby karteczek, wczytując dotychczasową wartość z rejestru i potem porównując ją
        try {
            TinyDB tinydb2 = new TinyDB(this);
            Integer x = tinydb2.getInt("ImageCount");
            if(x != null && x >=5 ){
              //  IncreaseImageCount(5);
                PreviousImageCount =x;
            }
        } catch (Exception e) {
            IncreaseImageCount(5);//stworzenie pierwszego klucza "ImageCount"
            PreviousImageCount =5;
        }//stworzenie klucza ImageCount i wczytanie mu liczby 3 w przypadku gdy jeszcze klucz nie powstał (pierwsze uruch. app)
        try {
            String message2 = intent.getStringExtra(MESSAGE_KEY_HELP);
            if (message2.equals("Increase")) { //po jakieś aktywności zwiększam ilość obrazków na 5
                images = new int[]{R.drawable.a1 ,R.drawable.a2 ,R.drawable.a3 ,R.drawable.a4 ,R.drawable.a5 ,R.drawable.a6 ,R.drawable.a7 ,R.drawable.a8 ,R.drawable.a9 ,R.drawable.a10 ,R.drawable.a11 ,R.drawable.a12 ,R.drawable.a13 ,R.drawable.a14 ,R.drawable.a15 ,R.drawable.a16 ,R.drawable.a17 ,R.drawable.a18 ,R.drawable.a19 ,R.drawable.a20 ,R.drawable.a21 ,R.drawable.a22 ,R.drawable.a23 ,R.drawable.a24 ,R.drawable.a25 ,R.drawable.a26 ,R.drawable.a27 ,R.drawable.a28 ,R.drawable.a29 ,R.drawable.a30};
                IncreaseImageCount(5);
            }
           else if (message2.equals("Increase2")) { //po jakieś aktywności zwiększam ilość obrazków na 7
                images = new int[]{R.drawable.a1 ,R.drawable.a2 ,R.drawable.a3 ,R.drawable.a4 ,R.drawable.a5 ,R.drawable.a6 ,R.drawable.a7 ,R.drawable.a8 ,R.drawable.a9 ,R.drawable.a10 ,R.drawable.a11 ,R.drawable.a12 ,R.drawable.a13 ,R.drawable.a14 ,R.drawable.a15 ,R.drawable.a16 ,R.drawable.a17 ,R.drawable.a18 ,R.drawable.a19 ,R.drawable.a20 ,R.drawable.a21 ,R.drawable.a22 ,R.drawable.a23 ,R.drawable.a24 ,R.drawable.a25 ,R.drawable.a26 ,R.drawable.a27 ,R.drawable.a28 ,R.drawable.a29 ,R.drawable.a30};
                IncreaseImageCount(7);
            }
        } catch (Exception e) {
        }
       // IncreaseImageCount(3);
        try {
            TinyDB tinydb2 = new TinyDB(this);
            Integer y = tinydb2.getInt("ImageCount");
            if (y != null && y > PreviousImageCount) {
                IncreaseImageCount(y);
                ImageCount = tinydb2.getInt("ImageCount");
            } else { //jeżeli przed chwilą wczytana do rejestru wartość y jest mniejsza od PreviousImageCount, to z powrotem wczytaj PreviousImageCount do rejestru - zabezpieczenie przed zmniejszeniem ilości karteczek
                IncreaseImageCount(PreviousImageCount);
                ImageCount = tinydb2.getInt("ImageCount");
            }
        } catch (Exception e) {
            IncreaseImageCount(PreviousImageCount);
        }//stworzenie klucza ImageCount i wczytanie mu liczby 3 w przypadku gdy jeszcze klucz nie powstał (pierwsze uruch. app)
        FillList();

         RefreshCounter();
        mp = MediaPlayer.create(this, R.raw.notify2);
       //  Bitmap icon = BitmapFactory.decodeResource(contexxt.getResources(),R.drawable.protect);
       //  mImageViewProtect.setImageBitmap(icon);
         try {
             TinyDB tinydb = new TinyDB(this);
             Integer x = tinydb.getInt("DisableSoundd");
             Integer y = tinydb.getInt("StoperCzas");
             Integer z = tinydb.getInt("DisableAlarmm");
             if (x != null &&x>=0&& y != null&&y>0&&z != null &&z>=0)
             {
                 DisableSoundd=x;
                 StoperCzas=y;
                 Settings.StoperCzas=y/1000;//uaktualnienie zmiennej z klasy settings, która została zapisana w rejestrze na wypadek, jeżeli nie zmienimy czasu stopera i zapiszemy nie nadpisać defaultową wartością 30s - w klasie settings jest ona updatowana dopiero po zmianie położenia suwaka seekBara
                 DisableAlarmm=z;
             }
                //zapis ustawień
             String message3 = intent.getStringExtra(MESSAGE_KEY_SETTINGS);
             if(message3!=null&&message3.length()>1) {  //jeśli nie jest string pusty i jest dłuższy niż 1 znak
                 SharedPreferences stanGry = Gra.this.getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
                 SharedPreferences.Editor edytor = stanGry.edit();

                 String words[] = message3.split(",");
                 DisableSoundd = Integer.parseInt(words[0]);
                 StoperCzas = Integer.parseInt(words[1]);
                 DisableAlarmm=Integer.parseInt(words[2]);
                 tinydb.putInt("DisableSoundd", DisableSoundd);
                 tinydb.putInt("StoperCzas", StoperCzas*1000);
                 tinydb.putInt("DisableAlarmm", DisableAlarmm);
                 edytor.commit();
                 finish();
             }
             Integer StoperCzasWsec=StoperCzas/1000;//dodawanie 0 wiodącego jeśli stoper ma mniej niż 10s
             String stoperrrczas="";
             if(StoperCzasWsec.toString().length()==1)
             {  stoperrrczas="0"+StoperCzasWsec.toString();}
             else
             {stoperrrczas=StoperCzasWsec.toString();}
             stoper.setText(stoperrrczas+"s");
             stoperr = new Stoper(StoperCzas, 1000);//30s co 1s

             if(message3.equals("s"))
             {
               //   Settings.DisableSoundd=DisableSoundd;
              //   Settings.StoperCzas=StoperCzas;
                 //ustawienie kontrolek z panelu Settings:
                 Settings.IleStoper.setProgress((StoperCzas/1000)-5);//wcześniej było +5
                 Settings.Licznik.setText(String.valueOf(StoperCzas/1000+"s"));
                 if(DisableSoundd==0)
                 {//Settings.DisableSound.setChecked(false);
                     Settings.Polecenia_glosowe_on.setVisibility(View.VISIBLE);
                     Settings.Polecenia_glosowe_off.setVisibility(View.INVISIBLE);
                 }
                 else {//Settings.DisableSound.setChecked(true);
                     Settings.Polecenia_glosowe_on.setVisibility(View.INVISIBLE);
                     Settings.Polecenia_glosowe_off.setVisibility(View.VISIBLE);
                 }
                 if(DisableAlarmm==0)
                 {//Settings.DisableAlarm.setChecked(false);
                     Settings.Alarm_on.setVisibility(View.VISIBLE);
                     Settings.Alarm_off.setVisibility(View.INVISIBLE);}
                 else {//Settings.DisableAlarm.setChecked(true);
                     Settings.Alarm_on.setVisibility(View.INVISIBLE);
                     Settings.Alarm_off.setVisibility(View.VISIBLE);}
                 Gra.this.finish();
             }
         }
         catch (Exception e) {  }
try{
         String message3 = intent.getStringExtra(MESSAGE_KEY_INCREASE_DIALOG);//jeśli na głównym ekranie klikam zwiększ ilość karteczek, to po odpaleniu załadowaniu klasy Gra wywołaj okno dialogowe z opcjami zwiększenia il. kart.
         if (message3.equals("Increase"))
         {images = new int[]{R.drawable.a1 ,R.drawable.a2 ,R.drawable.a3 ,R.drawable.a4 ,R.drawable.a5 ,R.drawable.a6 ,R.drawable.a7 ,R.drawable.a8 ,R.drawable.a9 ,R.drawable.a10 ,R.drawable.a11 ,R.drawable.a12 ,R.drawable.a13 ,R.drawable.a14 ,R.drawable.a15 ,R.drawable.a16 ,R.drawable.a17 ,R.drawable.a18 ,R.drawable.a19 ,R.drawable.a20 ,R.drawable.a21 ,R.drawable.a22 ,R.drawable.a23 ,R.drawable.a24 ,R.drawable.a25 ,R.drawable.a26 ,R.drawable.a27 ,R.drawable.a28 ,R.drawable.a29 ,R.drawable.a30};
             DialogEndGame(true);}}
catch (Exception e){}
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onPause() {
        super.onPause();
      //  stoperr.cancel(); //to komentuje bo jeśli ktoś zadzwoni to skasuje mi się stoper
    }
    @Override
    protected void onStop() {
        super.onStop();
        //stoperr.cancel();
    }
    @Override
    public void onBackPressed()
    {
        if(CzyZapisanoGre==false) {//jeżeli gry nie zapisano to prośba o ponowne naciśnięcie back i przypomnienie oże niezapisano
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())//jeśli minie więcej niż TIME_INTERVAL, to warunek = false
            {
                super.onBackPressed();
                stoperr.cancel();
                return;
            } else {
                Toast.makeText(getBaseContext(), R.string.ExitGame2, Toast.LENGTH_SHORT).show();
            }
            mBackPressed = System.currentTimeMillis();
        }
        else{
            super.onBackPressed();//jeśli zapisano to juz po jedym kliknięciu back wychodzi z gry
            stoperr.cancel();
            return;}
    }
//    @Override
 //   public boolean onKeyDown(int keyCode, KeyEvent event) {
  //      if (keyCode == KeyEvent.KEYCODE_BACK) {
  //          msboxExit(this.getString(R.string.ExitGamee), this.getString(R.string.ExitGamee2));
   //         return true;
   //     }
  //      return super.onKeyDown(keyCode, event);
 //   }
    public void msboxExit(String str, String str2) {
        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton(R.string.ExitGamee, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                stoperr.cancel();
                finish();
            }
        });
        dlgAlert.setNegativeButton(R.string.ReturnToGame, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dlgAlert.create().dismiss();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    public void msbox(String str, String str2) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
    public void IncreaseImageCount(int Count) {
        try{
        SharedPreferences stanGry = Gra.this.getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
        SharedPreferences.Editor edytor = stanGry.edit();
        TinyDB tinydb = new TinyDB(this);
        tinydb.putInt("ImageCount", Count);
        edytor.commit();}
        catch (Exception e){}
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    public void FillList() {
        if(pula.size()==0){//jeżeli pula jest pusta (nie wczytano z opcji kontynuacji gry, to wczytaj pełną pulę) jest to pula nie wykorzystanych obrazków
        for (Integer i = 1; i <= ImageCount; i++) {
            pula.add(i);
        }}
      }
    public void LosujZadania(boolean CzyNastepneUnikalne) {
        try{
        Random rnd = new Random();
        Integer i = rnd.nextInt(zadania.length);

          if(CzyNastepneUnikalne==true){
        while(i==previousTask[1]){i = rnd.nextInt(zadania.length);}}
            else if (CzyNastepneUnikalne==false)
          { i = rnd.nextInt(zadania.length); }

    zadanie.setImageResource(zadania[i]);
        LastTask =i;

        if(previousTask[0]<0&&previousTask[1]<0)
        {previousTask[1]=i;}
        else {
            previousTask[0] = previousTask[1];//chcę pamiętać poprzednie zadanie
        previousTask[1] = i; }
            if(DisableSoundd==0)
            {play_zadanie(i);}
        }
        catch (Exception e){Toast.makeText(getApplicationContext(),"Problem with random tast"+ e.toString(), Toast.LENGTH_SHORT).show();}
    }

    private void PokazObrazThread(){
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
                        PokazObraz();
                    }
                });
            }
        };
        mythread.start();
    }
    private void UkryjprzejsciowyobrazThread(){
        Thread mythread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                     //   przejscioweFrame.setImageDrawable(null);
                        przejscioweFrame.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
        mythread.start();
    }
    public void PokazObraz() {
       // if (mImageViewProtect.getVisibility() == View.INVISIBLE && mImageView.getVisibility() == View.VISIBLE) { //było
                    if (mImageViewProtect.getVisibility() == View.INVISIBLE) {
                    mImageViewProtect.setVisibility(View.VISIBLE);
                        mImageView.setVisibility(View.INVISIBLE);
                } else {
                    mImageView.setVisibility(View.VISIBLE);
                    mImageViewProtect.setVisibility(View.INVISIBLE);
                }
         }

     public void Losuj(View view) {
        try {
            if (FirstFrame.getVisibility() == View.VISIBLE) {
                FirstFrame.setVisibility(View.GONE);
                Czystoperdziala = true;
                FirstFrame.setImageDrawable(null);//dla wydajności czyszczę pamięć
                }
            mImageViewProtect.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);

            if(Czystoperdziala == true&&FirstFrame.getDrawable()==null){ //jeżeli nie odkryję żadnej kart nie mogę klikać graj

            Random rnd = new Random();
            if (pula.size() != 0) {
                Integer i = pula.get(rnd.nextInt(pula.size()));
                LosujZadania(false);
                CzyZapisanoGre=false;//jeśli kliknę graj zmieniam flagę, aby przy wyjściu przypomniało o zapisanie;
              //  mImageView.setImageResource(images[i - 1]);
              //  mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), images[i - 1], 1080, 1080));
         //       if(task!=null){
         //       task.cancel(true);}//w przypadku jeżeli szybko klikam to przerywam poprzednie zadanie
          //     task = new BitmapWorkerTask(mImageView);
         //       task.execute(images[i - 1]);
          //      task=null;
                Picasso.with(this).load(images[i - 1]).into(mImageView);
                LastImage = i;//zapisuje ostatni obrazek, aby przy kontynuacji gry go pokazać
                pula.remove(i);//usuwam go z puli do wylosowania
            } else {
                // msbox("Koniec Gry", "Więcej? Kup wersję PRO.");
                if(ImageCount==30)
                {android.content.Intent intentttt = new android.content.Intent(contexxt,FinalActivity.class);
                startActivity(intentttt);

                }
                    else
                {DialogEndGame(false);} }
            }
            else{Toast.makeText(getApplicationContext(),R.string.FirstExposeCard, Toast.LENGTH_SHORT).show();}
            //   File imgFile = new  File("p1.png");
            //    if(imgFile.exists()){
            //      String jg=imgFile.getAbsolutePath()+"/p1.png";
            //      Bitmap myBitmap = BitmapFactory.decodeFile(jg);
            //       mImageView.setImageBitmap(myBitmap);
            //  }
            // mImageView.setImageResource(R.drawable.p1);
            RefreshCounter();
            Integer StoperCzasWsec=StoperCzas/1000;//dodawanie 0 wiodącego jeśli stoper ma mniej niż 10s
            String stoperrrczas="";
            if(StoperCzasWsec.toString().length()==1)
            {  stoperrrczas="0"+StoperCzasWsec.toString();}
            else
            {stoperrrczas=StoperCzasWsec.toString();}
            stoper.setText(stoperrrczas+"s");
            Czystoperdziala = false;
            stoperr.cancel();
            graj.clearAnimation();//usunięcie animacji z przycisku graj
        }
        catch(Exception e){Toast.makeText(getApplicationContext(),"Problem with Losuj"+ e.toString(), Toast.LENGTH_SHORT).show();}
    }
public void SaveLeftActivities(int x1,int x2,int x3,int x4,int x5){
    try{
    SharedPreferences stanGry = Gra.this.getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
    SharedPreferences.Editor edytor = stanGry.edit();

    if(x1>0)
    { TinyDB tinydb = new TinyDB(this);
        tinydb.putInt("ActivitiesLeft1", x1);
    edytor.commit();}
    if(x2>0)
    {
        TinyDB tinydb = new TinyDB(this);
        tinydb.putInt("ActivitiesLeft2", x2);
    edytor.commit();}
    if(x3>0)
    {
        TinyDB tinydb = new TinyDB(this);
        tinydb.putInt("ActivitiesLeft3", x3);
    edytor.commit();}
    if(x4>0)
    {
        TinyDB tinydb = new TinyDB(this);
        tinydb.putInt("ActivitiesLeft4", x4);
    edytor.commit();}
    if(x5>0)
    {
        TinyDB tinydb = new TinyDB(this);
        tinydb.putInt("ActivitiesLeft5", x5);
    edytor.commit();}}
    catch (Exception e){}
    }
    public void RefreshCounter(){
    String imageCount="";
    Integer currentimage=ImageCount - pula.size();
    String Currentimage="";
    if(ImageCount.toString().length()==1)
    imageCount="0"+ImageCount.toString();
    else
    imageCount=ImageCount.toString();
    if(currentimage.toString().length()==1)
    Currentimage="0"+currentimage.toString();
    else
    Currentimage=currentimage.toString();
    counter.setText(Currentimage + "/" + imageCount);}

     public void SaveSettings(View view) {
         try {
             SharedPreferences stanGry = Gra.this.getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
             SharedPreferences.Editor edytor = stanGry.edit();

             // Set<String> set = new HashSet<String>();
             //  List<String> newConvertList = new ArrayList<String>(pula.size());
             //   for (Integer myInt : pula) {
             //     newConvertList.add(String.valueOf(myInt));
             //   }
             //  set.addAll(newConvertList);

             //  Gson gson = new Gson();
             //  String jsonText = gson.toJson(pula);
             //  edytor.putString("Pula", jsonText);
             // tinydb.putListInt("Pula2", null);//czyszczenie klucza
             if(LastImage != null)
             {
             TinyDB tinydb = new TinyDB(this);
             if (displayLevel.equals("Poziom A")) {
                 tinydb.putListInt("Pula", pula);
                 tinydb.putInt("Zadanie", LastTask);
                 if (LastImage != null)
                     tinydb.putInt("Image", LastImage);
                 else
                     tinydb.clear();
             } else if (displayLevel.equals("Poziom B")) {
                 tinydb.putListInt("Pula2", pula);
                 tinydb.putInt("Zadanie2", LastTask);

                 if (LastImage != null)
                     tinydb.putInt("Image2", LastImage);
                 else
                     tinydb.clear();
             } else if (displayLevel.equals("Poziom C")) {
                 tinydb.putListInt("Pula3", pula);
                 tinydb.putInt("Zadanie3", LastTask);

                 if (LastImage != null)
                     tinydb.putInt("Image3", LastImage);
                 else
                     tinydb.clear();
             }
             edytor.commit();
                 CzyZapisanoGre=true;
             Toast.makeText(getApplicationContext(), R.string.GameSaved, Toast.LENGTH_SHORT).show();
         }
             else{Toast.makeText(getApplicationContext(),R.string.CannotSaveStartedGame, Toast.LENGTH_SHORT).show();}
        } catch (Exception e) {Toast.makeText(getApplicationContext(),"Problem with saving game."+ e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

     public class Stoper extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Stoper(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            try{
            long millis = millisUntilFinished;
            String hms = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millis));
            stoper.setText(hms + "s");}
            catch (Exception e){}
        }
        @Override
        public void onFinish() {
            try {
                stoper.setText("00s");
                //  mp.setLooping(true);
                //   for (int i=1; i<=5 ; i++){
                mp.setVolume(1.0f, 1.0f);
                if(DisableAlarmm==0)
                mp.start();
                //      while(mp.isPlaying());
                //  }
                // mp.reset();
            }
            catch (Exception e){}
        }
    }
    public class StoperImageIndicator extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public StoperImageIndicator(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            try{
                }
            catch (Exception e){}
        }
        @Override
        public void onFinish() {
            try {
                if(pokazobraz!=null){
                    pokazobraz=null;}//w przypadku jeżeli szybko klikam to przerywam poprzednie zadanie
                pokazobraz = new PokazObrazAsyncTask(mImageViewProtect,mImageView);
                pokazobraz.execute(); //odkrycie obrazka gry

                if(Czystoperdziala==false&&FirstFrame.getDrawable()==null)//aby uniknąć odpalenia stopera na tym obrazku początkowym
                {stoperr.start();//uruchomienie stopera głównego 30s
                    Czystoperdziala=true;
                }
                CzystoperdzialaImage=true;
                // przejscioweFrame.setImageDrawable(null);
                UkryjprzejsciowyobrazThread();
               // przejscioweFrame.setVisibility(View.INVISIBLE);
            }
            catch (Exception e){}
        }
    }


    public void DialogEndGame(final boolean CzyPowrocicDoMainActivity) {
         try{
             RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
             anim.setInterpolator(new LinearInterpolator());
             anim.setRepeatCount(Animation.INFINITE);
             anim.setDuration(700);
             ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.03f, 1.0f, 1.03f);
             scaleAnim.setRepeatCount(Animation.INFINITE);
             scaleAnim.setDuration(1000);

             SharedPreferences stanGry = Gra.this.getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
             final SharedPreferences.Editor edytor = stanGry.edit();
             final TinyDB tinydb = new TinyDB(getBaseContext());
             if (tinydb.getListInt("Pula").size() >= 0)
             {Temppula = tinydb.getListInt("Pula"); }
             if (tinydb.getListInt("Pula2").size() >= 0)
             {Temppula2 = tinydb.getListInt("Pula2");}
             if (tinydb.getListInt("Pula3").size() >= 0)
             {Temppula3 = tinydb.getListInt("Pula3");}
             ///set up dial
             dialog = new Dialog(Gra.this,android.R.style.Theme_Black_NoTitleBar);//android.R.style.Theme_Black_NoTitleBar_Fullscreen - aby okno dialogowe było fullscreen
        dialog.setContentView(R.layout.endgamedialog);
     //   dialog.setTitle(R.string.EndGame);//nagłówek
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
             //grafika statyczna
             final ImageView Share = (ImageView) dialog.findViewById(R.id.imageView30);
             final ImageView Share2 = (ImageView) dialog.findViewById(R.id.imageView31);
             try{  //wywalić src z pliku xmla po ułożeniu layouta!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                 if(Locale.getDefault().getLanguage().equals("pl")){
                     Picasso.with(contexxt).load(R.drawable.share_pl).into(Share);
                     Picasso.with(contexxt).load(R.drawable.share2_pl).into(Share2);}
                 else if (Locale.getDefault().getLanguage().equals("ru"))
                 { Picasso.with(contexxt).load(R.drawable.share_ru).into(Share);
                     Picasso.with(contexxt).load(R.drawable.share2_ru).into(Share2); }
                 else {Picasso.with(contexxt).load(R.drawable.share).into(Share);
                     Picasso.with(contexxt).load(R.drawable.share2).into(Share2); }
             }
             catch(Exception e){Picasso.with(this).load(R.drawable.share).into(Share);
                 Picasso.with(this).load(R.drawable.share2).into(Share2);}

        final TextView text = (TextView) dialog.findViewById(R.id.TextView01);
       // text.setText(R.string.EndGameDialog);
             text.setText(ImageCount.toString()+"/30");//licznik total

            //sposób na metodę wewnętrzną, uruchamiam odświeżenie licznika w nowym wątku bo chcę opóźnić jego odświeżenie
                 class Refresh {
                private void RefreshDialogcounter() {
                    Thread mythread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    text.setText(ImageCount.toString() + "/30");
                                }
                            });
                        }
                    };
                    mythread.start();
                }
            }
             final Refresh refr = new Refresh();
             //set up buttons
         ImageView btnFacebook = (ImageView) dialog.findViewById(R.id.imageView33);//facebook
             btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);//po każdym kliknięciu musi powstać nowy obiekt ponieważ ktoś może właczyć net w czasie życia dialog
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ActiKidsGame"));
                startActivity(browserIntent);
           if(Gra.this.ActivityLeft1==2&&ImageCount+5<=images.length){ //zabezpieczam się, aby nie zwiększyć liczby karteczek poza ilość w tablicy obrazków
               Gra.this.SaveLeftActivities(1,0,0,0,0);
               Gra.this.IncreaseImageCount(ImageCount+5);
               Integer PreviousImageCount=Gra.this.ImageCount;
               Gra.this.ImageCount=PreviousImageCount+5;//zwiększenie ilości obrazków o 1
               for (int i=PreviousImageCount+1; i <= Gra.this.ImageCount; i++) {
                   pula.add(i);
                   Temppula.add(i);//dodanie nowej puli także dla zapisanych gier dla 3 poziomów
                   Temppula2.add(i);
                   Temppula3.add(i);}
               tinydb.putListInt("Pula", Temppula);
               tinydb.putListInt("Pula2", Temppula2);
               tinydb.putListInt("Pula3", Temppula3);
               edytor.commit();

               refr.RefreshDialogcounter();
               RefreshCounter();//uaktualnienie licznika

               ActivityLeft1=1;
                 }
            } else {Toast.makeText(getApplicationContext(), R.string.EnableNet, Toast.LENGTH_SHORT).show();}
            }
        });
             btnFacebook.startAnimation(scaleAnim);
        ImageView btnVotePlayStore = (ImageView) dialog.findViewById(R.id.imageView35);
             btnVotePlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);//po każdym kliknięciu musi powstać nowy obiekt ponieważ ktoś może właczyć net w czasie życia dialog
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fgamesteam.free1.actikids"));
                startActivity(browserIntent);
                if(ActivityLeft2==2&&ImageCount+5<=images.length){
                    Gra.this.SaveLeftActivities(0,1,0,0,0);
                    Gra.this.IncreaseImageCount(ImageCount+5);
                    Integer PreviousImageCount=Gra.this.ImageCount;
                    Gra.this.ImageCount=PreviousImageCount+5;
                    for (int i=PreviousImageCount+1; i <= Gra.this.ImageCount; i++) {
                        pula.add(i);
                        Temppula.add(i);//dodanie nowej puli także dla zapisanych gier dla 3 poziomów
                        Temppula2.add(i);
                        Temppula3.add(i);}
                    tinydb.putListInt("Pula", Temppula);
                    tinydb.putListInt("Pula2", Temppula2);
                    tinydb.putListInt("Pula3", Temppula3);
                    edytor.commit();
                    RefreshCounter();//uaktualnienie licznika
                    refr.RefreshDialogcounter();//i drugiego
                    ActivityLeft2=1;
                    }
                } else {Toast.makeText(getApplicationContext(), R.string.EnableNet, Toast.LENGTH_SHORT).show();}
            }
        });
             btnVotePlayStore.startAnimation(scaleAnim);
      // LikeView btnLike = (LikeView) dialog.findViewById(R.id.like_view);
      //       btnLike.setObjectIdAndType("http://inthecheesefactory.com/blog/understand-android-activity-launchmode/en",LikeView.ObjectType.OPEN_GRAPH);
       // btnLike.setOnClickListener(new View.OnClickListener() {
          //  @Override
         //   public void onClick(View v) {
         //         if(ActivityLeft3==2&&ImageCount+1<=images.length){
        //            Gra.this.SaveLeftActivities(0,0,1,0,0);
        //            Gra.this.IncreaseImageCount(ImageCount+1);
         //           Integer PreviousImageCount=Gra.this.ImageCount;
         //           Gra.this.ImageCount=PreviousImageCount+1;
         //           for (int i=PreviousImageCount+1; i <= Gra.this.ImageCount; i++) {
          //              pula.add(i);}
           //           RefreshCounter();//uaktualnienie licznika
           //         ActivityLeft3=1;
           //          }
         //     }
          // });
        ImageView btnVK = (ImageView) dialog.findViewById(R.id.imageView32);
        btnVK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);//po każdym kliknięciu musi powstać nowy obiekt ponieważ ktoś może właczyć net w czasie życia dialog
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/club129282319"));
                startActivity(browserIntent);

                if(ActivityLeft4==2&&ImageCount+5<=images.length){
                    Gra.this.SaveLeftActivities(0,0,0,1,0);
                    Gra.this.IncreaseImageCount(ImageCount+5);
                    Integer PreviousImageCount=Gra.this.ImageCount;
                    Gra.this.ImageCount=PreviousImageCount+5;
                    for (int i=PreviousImageCount+1; i <= Gra.this.ImageCount; i++) {
                        pula.add(i);
                        Temppula.add(i);//dodanie nowej puli także dla zapisanych gier dla 3 poziomów
                        Temppula2.add(i);
                        Temppula3.add(i);}
                    tinydb.putListInt("Pula", Temppula);
                    tinydb.putListInt("Pula2", Temppula2);
                    tinydb.putListInt("Pula3", Temppula3);
                    edytor.commit();
                    RefreshCounter();//uaktualnienie licznika
                    refr.RefreshDialogcounter();
                    ActivityLeft4=1;
                }
                } else {Toast.makeText(getApplicationContext(), R.string.EnableNet, Toast.LENGTH_SHORT).show();}
            }
        });
             btnVK.startAnimation(scaleAnim);
        ImageView btnLikeYT = (ImageView) dialog.findViewById(R.id.imageView34);
        btnLikeYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);//po każdym kliknięciu musi powstać nowy obiekt ponieważ ktoś może właczyć net w czasie życia dialog
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=DjtWZmRRAcU"));
                startActivity(browserIntent);
                if(ActivityLeft5==2&&ImageCount+5<=images.length){
                    Gra.this.SaveLeftActivities(0,0,0,0,1);
                    Gra.this.IncreaseImageCount(ImageCount+5);
                    Integer PreviousImageCount=Gra.this.ImageCount;
                    Gra.this.ImageCount=PreviousImageCount+5;
                    for (int i=PreviousImageCount+1; i <= Gra.this.ImageCount; i++) {
                        pula.add(i);
                        Temppula.add(i);//dodanie nowej puli także dla zapisanych gier dla 3 poziomów
                        Temppula2.add(i);
                        Temppula3.add(i);}
                    tinydb.putListInt("Pula", Temppula);
                    tinydb.putListInt("Pula2", Temppula2);
                    tinydb.putListInt("Pula3", Temppula3);
                    edytor.commit();
                    RefreshCounter();//uaktualnienie licznika
                    refr.RefreshDialogcounter();
                    ActivityLeft5=1;
                }
                } else {Toast.makeText(getApplicationContext(), R.string.EnableNet, Toast.LENGTH_SHORT).show();}
            }
        });
             btnLikeYT.startAnimation(scaleAnim);

             ImageView pack1 = (ImageView) dialog.findViewById(R.id.imageView23);
             pack1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fgamesteam.pack1.actikids"));
                     startActivity(browserIntent);
                 }
             });
             ImageView pack2 = (ImageView) dialog.findViewById(R.id.imageView24);
             pack2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fgamesteam.pack2.actikids"));
                     startActivity(browserIntent);
                 }
             });
             ImageView pack3 = (ImageView) dialog.findViewById(R.id.imageView25);
             pack3.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fgamesteam.pack3.actikids"));
                     startActivity(browserIntent);
                 }
             });

             dialog.setOnKeyListener(new Dialog.OnKeyListener() { //dodanie obsługi przycisku back - aby zamknął nie tylko dialog ale też Gra class
                 @Override
                 public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                     if (keyCode == KeyEvent.KEYCODE_BACK) {

                         if(CzyPowrocicDoMainActivity==true){
                             Gra.this.finish();
                             dialog.cancel();
                             }
                         else{dialog.cancel();}//zwykłe zamknięcie okna
                     }
                      return true;
                 }
             });
             AdView mAdView = (AdView) dialog.findViewById(R.id.ad_view);
             AdRequest adRequest = new AdRequest.Builder()
                    // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                     .build();
             mAdView.loadAd(adRequest);
        dialog.show();}
         catch (Exception e){Toast.makeText(getApplicationContext(),"Problem with end game dialog"+ e.toString(), Toast.LENGTH_SHORT).show();}
    }

public void play_zadanie(int numerZadania){
    try{
    mp_zadan = MediaPlayer.create(this, dzwieki_zadan[numerZadania]);
    mp_zadan.setVolume(1.0f, 1.0f);
    mp_zadan.start();}
    catch (Exception e){}
   }



}


