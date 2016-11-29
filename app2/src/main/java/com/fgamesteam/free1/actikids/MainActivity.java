package com.fgamesteam.free1.actikids;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKWallPostResult;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.dialogs.VKShareDialog;
import com.vk.sdk.dialogs.VKShareDialogBuilder;
import com.vk.sdk.util.VKUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String MESSAGE_KEY_INCREASE_DIALOG = "com.example.pawel.myapplication2.message_key4";
    private ShareButton shareButton;
    private ShareButton shareLinkButton;
    private ImageView pomoc;
    private ImageView settings;
    private ImageView play;
    private ImageView addPics;
    private AdView mAdView;
    private LikeView like;
    private Bitmap image;
    private int counter = 0;
    public static Context contexxt;
    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS
    };
    CallbackManager callbackManager;
    LoginManager manager;
    ShareDialog dialog;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //R to resource
        FacebookSdk.sdkInitialize(getApplicationContext());
        final String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
      //  VKUIHelper.getApplicationContext();
       // VKSdk.initialize(getApplicationContext());
        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_AppID));
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
               // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        contexxt=getApplicationContext();
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);

//List<String> permissionsNeeds = Arrays.asList("publish_actions");
        dialog = new ShareDialog(this);

        pomoc = (ImageView) findViewById(R.id.imageView9);
        pomoc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent intentt = new android.content.Intent(MainActivity.this,Help.class);
                startActivity(intentt);
            }
        });
        settings= (ImageView) findViewById(R.id.imageView10);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent intentt = new android.content.Intent(MainActivity.this,Settings.class);
                startActivity(intentt);
            }
        });
        play=(ImageView) findViewById(R.id.imageView20);
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.03f, 1.0f, 1.03f);//animacja skalowania
        scaleAnim.setRepeatCount(Animation.INFINITE);
        scaleAnim.setDuration(1000);
        play.startAnimation(scaleAnim);
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                android.content.Intent intentt = new android.content.Intent(MainActivity.this,selectLevel.class);
                startActivity(intentt);
            }
        });
        addPics=(ImageView) findViewById(R.id.imageView21);
        addPics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final android.content.Intent intenttt = new android.content.Intent(getBaseContext(), Gra.class);
                String message = "Increase";
                intenttt.putExtra(MESSAGE_KEY_INCREASE_DIALOG, message);
                startActivity(intenttt);
            }
        });
      //  VKtest=(Button) findViewById(R.id.button17);
        //VKtest.setOnClickListener(new View.OnClickListener() {
      //      public void onClick(View view) {
      //          VKSdk.login(MainActivity.this, sMyScope);
       //         VkPutImageScreenshootToWall2();
      //         }
      //  });
      //  like = (LikeView) findViewById(R.id.like_view);//nieaktywny(usunięty z głównego layoutu)
      //  like.setObjectIdAndType(
         //       "http://inthecheesefactory.com/blog/understand-android-activity-launchmode/en",
          //      LikeView.ObjectType.OPEN_GRAPH);

     //   shareLinkButton= (ShareButton) findViewById(R.id.share_link);
   //     shareLinkButton.setOnClickListener(new View.OnClickListener() {
  //          public void onClick(View view) {
  //           SharingLinkContentonFacebook();
 //           }
  //      });
        //share button
    //    shareButton = (ShareButton) findViewById(R.id.share_btn);
      //  shareButton.setOnClickListener(new View.OnClickListener() {
       //     public void onClick(View view) {
               // postPicture();
                // postFBlink();
                //  like.setObjectIdAndType("https://www.facebook.com/FacebookDevelopers",LikeView.ObjectType.PAGE);
                // sharePhoto();
                //SharingLinkContentonFacebook();
       //         final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      //          final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
     //           if (activeNetwork != null && activeNetwork.isConnected()) {
      //              sharePhotoToFacebook2();
      //          } else {
       //             Toast.makeText(getApplicationContext(), R.string.EnableNet, Toast.LENGTH_SHORT).show();
        //        }
       //     }
    //    });
    }
    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), R.string.ExitGame, Toast.LENGTH_SHORT).show(); }
        mBackPressed = System.currentTimeMillis();
    }

private void VkPutImageScreenshootToWall(){
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.ikona);
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        // creates immutable clone of image
        image = Bitmap.createBitmap(rootView.getDrawingCache());
        // destroy
        rootView.destroyDrawingCache();
        VKRequest request = VKApi.uploadWallPhotoRequest(new VKUploadImage(image, VKImageParameters.jpgImage(0.9f)), 0, 60479154);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKApiPhoto photoModel = ((VKPhotoArray) response.parsedModel).get(0);
                makePost(new VKAttachments(photoModel),null);
                // makePost(null, "Hello, friends!");
            }
            @Override
            public void onError(VKError error) {
                //Do error stuff
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                
            }
        });
    }
    private void VkPutImageScreenshootToWall2(){
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        // creates immutable clone of image
        image = Bitmap.createBitmap(rootView.getDrawingCache());
        final Bitmap[] b = {BitmapFactory.decodeResource(getResources(), R.drawable.ikona)};
        VKPhotoArray photos = new VKPhotoArray();
        photos.add(new VKApiPhoto("photo-47200925_314622346"));
        new VKShareDialogBuilder()
                .setText("I created this post with VK Android SDK\nSee additional information below\n#vksdk")
                .setUploadedPhotos(photos)
                .setAttachmentImages(new VKUploadImage[]{
                        new VKUploadImage(image, VKImageParameters.pngImage())
                })
                .setAttachmentLink("VK Android SDK information", "https://vk.com/dev/android_sdk")
                .setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
                    @Override
                    public void onVkShareComplete(int postId) {
                        image =null;
                    }

                    @Override
                    public void onVkShareCancel() {
                        image =null;
                    }

                    @Override
                    public void onVkShareError(VKError error) {
                        image =null;
                    }
                })
                .show(getFragmentManager(), "VK_SHARE_DIALOG");
    }
    private void makePost(VKAttachments attachments, String message) {
        VKRequest post = VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, "-" + 60479154, VKApiConst.ATTACHMENTS, attachments, VKApiConst.MESSAGE, message));
        post.setModelClass(VKWallPostResult.class);
        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                    VKWallPostResult result = (VKWallPostResult) response.parsedModel;
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://vk.com/wall-%d_%s", 60479154, result.post_id)));
                    startActivity(i);
            }
            @Override
            public void onError(VKError error) {
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
//obsługa VK
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
            }
            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        }))
            super.onActivityResult(requestCode, resultCode, data);
    }

     private void sharePhotoToFacebook2() {
         List<String> permissionsNeeds = Arrays.asList("publish_actions");
         manager = LoginManager.getInstance();
         manager.logInWithPublishPermissions(this, permissionsNeeds);
         manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

             @Override
             public void onSuccess(LoginResult loginResult) {
               //  WifiManager wifi =(WifiManager)getSystemService(Context.WIFI_SERVICE);
               //  if(wifi.isWifiEnabled())
              //   else
                sharePhotoToFacebook();
             }
             @Override
             public void onCancel() {
             }
             @Override
             public void onError(FacebookException error) {
             }
         });
     }
    public void Dialog() {
        ///set up dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.maindialog);
        dialog.setTitle("Share Screen Shot.");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView text = (TextView) dialog.findViewById(R.id.TextView01);

        text.setText("ScrollView01ScrollView01ScrollView01ScrollView01ScrollView01ScrocrollView01ScrollView01ScrollView01ScrollView01Scroll1");
        //set up image view
        ImageView img = (ImageView) dialog.findViewById(R.id.ImageView01);
        img.setImageResource(R.drawable.a3);
        //set up button
        Button button = (Button) dialog.findViewById(R.id.Button01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });
        dialog.show();
    }
         private void sharePhotoToFacebook(){
             ImageView image = new ImageView(this);

            // image.setImageResource(R.drawable.ic_launcher);
             image.setImageResource(R.mipmap.ikonka);
            // image.setLayoutParams(new LinearLayoutCompat.LayoutParams(200,200));

             AlertDialog.Builder shareDialog = new AlertDialog.Builder(this);
             shareDialog.setTitle("Share Screen Shot");
             shareDialog.setMessage("Share image to Facebook?");
             shareDialog.setView(image);
             shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                     Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ikona);
                     SharePhoto photo = new SharePhoto.Builder()
                             .setBitmap(image)
                             .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
                             .build();
                     SharePhotoContent content = new SharePhotoContent.Builder()
                             .addPhoto(photo)
                             .build();
                     ShareApi.share(content, null);}
             });
             shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
                 }
             });
              shareDialog.show();
    }
    private void sharePhotoToFacebookOryginalVersion(){
             Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ikonka);
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
                        .build();
                 if (dialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
          //      ShareApi.share(content, null);
          //        dialog.show(content);

                     dialog.show(content, ShareDialog.Mode.AUTOMATIC);
                 }
          }

    public void postPicture() {

        if (counter == 0) {
            //save the screenshot
            View rootView = findViewById(android.R.id.content).getRootView();
            rootView.setDrawingCacheEnabled(true);
            // creates immutable clone of image
            image = Bitmap.createBitmap(rootView.getDrawingCache());
            // destroy
            rootView.destroyDrawingCache();
            //share dialog
            AlertDialog.Builder shareDialog = new AlertDialog.Builder(this);
            shareDialog.setTitle("Share Screen Shot");
            shareDialog.setMessage("Share image to Facebook?");
            shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //share the image to Facebook
                    SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();
                    SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                    shareButton.setShareContent(content);
                    counter = 1;
                    shareButton.performClick();
                    //img.setImageBitmap(image);//teścik z image view
                   // ShareApi.share(content, null);
                }
            });
            shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            shareDialog.show();
        } else {
            counter = 0;
            shareButton.setShareContent(null);
        }
    }
    private void shareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);
        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/*");
        // Make sure you put example png image named myImage.png in your
        // directory
        String imagePath = Environment.getExternalStorageDirectory()
                + "/myImage.png";
        File imageFileToShare = new File(imagePath);
        Uri uri = Uri.fromFile(imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image!"));
    }
    public void SharingLinkContentonFacebook(){
        ShareDialog shareDialog = new ShareDialog(this);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("How to integrate Linkedin from your app")
                    .setImageUrl(Uri.parse("https://www.numetriclabz.com/wp-content/uploads/2015/11/114.png"))
                    .setContentDescription(
                            "simple LinkedIn integration")
                    .setContentUrl(Uri.parse("https://www.numetriclabz.com/android-linkedin-integration-login-tutorial/"))
                    .build();
            shareDialog.show(linkContent);  // Show facebook ShareDialog
        }
    }
    public void postFBlink() {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
        shareButton.setShareContent(content);
    }
    private void shareTextUrl() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");
        startActivity(Intent.createChooser(share, "Share link!"));
    }

       public void OpenGraActivity(View view) {  //opening Gra activity to open dialog for increasing images count
        final android.content.Intent intenttt = new android.content.Intent(this, Gra.class);
        String message = "Increase";
        intenttt.putExtra(MESSAGE_KEY_INCREASE_DIALOG, message);
        startActivity(intenttt);

    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
