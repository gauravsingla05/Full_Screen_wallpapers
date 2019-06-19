package wallpaper.com.fullscreenstatus;

import android.Manifest;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Full_screen extends AppCompatActivity {

    MediaController mediaController;
    ImageView videoView;
   // ProgressDialog progressDialog;
    ProgressBar video_loading;
    private InterstitialAd mInterstitialAd;


    Boolean isPLaying;
    int duration;
    int current;
    String URL;
    FloatingActionButton downloadBtn,shareBtn,repost,setwallpaper;
    String directoryName = "Status App";
    String title,finaltitle;
    public static final String VIDEO_URL_KEY = "key";
    public static final String SINGER_KEY = "singer_key";
    public static final String VIDEO_TITLE_KEY = "title_key";
    Boolean per=false;
    public static final int STORAGE_PERMISSION_REQUEST_CODE= 1;
    File file;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        mediaController = new MediaController(this);
        videoView = (ImageView) findViewById(R.id.full_screen_video_activty_id);

        video_loading = (ProgressBar) findViewById(R.id.videoLoading);
        downloadBtn = (FloatingActionButton) findViewById(R.id.download_ic);
        shareBtn = (FloatingActionButton) findViewById(R.id.share_ic);
        repost = (FloatingActionButton) findViewById(R.id.repost);
        setwallpaper = (FloatingActionButton) findViewById(R.id.setWallpaper);

        askPermissions();
        Intent intent = getIntent();


        URL = intent.getStringExtra(VIDEO_URL_KEY);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7509171001087812~6069902991");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7509171001087812/7770707916");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        title = intent.getStringExtra(VIDEO_TITLE_KEY);

        finaltitle=title+".jpeg";


         file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), finaltitle);



        if (URL != null) {


            Picasso.get().load(URL).into(videoView, new Callback() {
                @Override
                public void onSuccess() {
                    video_loading.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Exception e) {

                }
            });



           repost.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {


                   if(file.exists())
                   {

                       //File Exists};
                       Toast.makeText(getApplicationContext(),"Choose Any App",Toast.LENGTH_LONG).show();

                       shareIntent(file);

                   }

                   else {


                       downloadFile(URL,finaltitle);
                       shareIntent(file);
                       //Toast.makeText(getApplicationContext(),"File is downloding, Click again once completed",Toast.LENGTH_LONG).show();


                   }




               }
           });


            downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   if(per){

                       Toast.makeText(getApplicationContext(),"DOWNLOADING",Toast.LENGTH_LONG).show();
                       downloadFile(URL,finaltitle);

                       if (mInterstitialAd.isLoaded()) {
                           mInterstitialAd.show();
                       } else {
                           Log.d("TAG", "The interstitial wasn't loaded yet.");
                       }


                     }

                   else {
                       Toast.makeText(getApplicationContext(),"ALLOW STORAGE ACCESS",Toast.LENGTH_LONG).show();

                   }

                }


            });


            setwallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

               if(file.exists()){
                   setWallaper();
               }


               else {

                   downloadFile(URL,finaltitle);

                   Toast.makeText(getApplicationContext(),"Please Tap Again Once Completed",Toast.LENGTH_SHORT).show();

               }

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }

                }








            });

            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), finaltitle);
                    if(file.exists())
                    {

                        //File Exists};

                        shareIntent(file);

                    }

                    else {


                        downloadFile(URL,finaltitle);
                        shareIntent(file);
                       //Toast.makeText(getApplicationContext(),"File is downloding, Click again once completed",Toast.LENGTH_LONG).show();


                    }

                }
            });


        }////////////ENDING
        else {

            Toast.makeText(getApplicationContext(), "Oops..", Toast.LENGTH_LONG).show();

        }






    }









    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();



    }



    public void downloadFile(String url, String filename){

        DownloadManager.Request dmr = new DownloadManager.Request(Uri.parse(url));


       String fileName = filename;
       dmr.setTitle(fileName);
       dmr.setDescription("Share The Quotes With Your Friends");
       dmr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
       dmr.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
       dmr.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
       DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
       manager.enqueue(dmr);




   }


public void shareIntent(File file){

    String s = String.valueOf(file);


    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
    intent.setType("video/*");
    String shareBodyText = "Download Status Videos ";
    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(s));
    startActivity(Intent.createChooser(intent, "Choose sharing method"));



}

    private void askPermissions() {

        int permissionCheckStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // we already asked for permisson & Permission granted, call camera intent
        if (permissionCheckStorage == PackageManager.PERMISSION_GRANTED)
        {

            //do what you want
            per=true;

        } else {

            // if storage request is denied
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("You need to give permission to access storage in order to work this feature.");
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("GIVE PERMISSION", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        // Show permission request popup
                        ActivityCompat.requestPermissions(Full_screen.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_REQUEST_CODE);
                    }
                });
                builder.show();

            } //asking permission for first time
            else {
                // Show permission request popup for the first time
                         ActivityCompat.requestPermissions(Full_screen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // check whether storage permission granted or not.
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //do what you want;
                        per=true;
                    }
                }
                break;
            default:
                break;
        }
    }



public void setWallaper(){
    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
    Toast.makeText(getApplicationContext(),""+file.getAbsolutePath(),Toast.LENGTH_LONG).show();
    DisplayMetrics metrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metrics);
    // get the height and width of screen
    int height = metrics.heightPixels;
    int width = metrics.widthPixels;

    WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
    try {
        wallpaperManager.setBitmap(bitmap);

        //wallpaperManager.suggestDesiredDimensions(width, height);
        Toast.makeText(getApplicationContext(), "Wallpaper Set", Toast.LENGTH_SHORT).show();

    } catch (IOException e) {
        e.printStackTrace();
    }

}




}
















