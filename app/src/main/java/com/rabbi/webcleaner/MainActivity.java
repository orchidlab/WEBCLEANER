package com.rabbi.webcleaner;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebIconDatabase;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity
{
    InterstitialAd mInterstitialAd;
    public TextView   tv1, tv2, tv3;
    ImageView imgv1, imgv2,imgv3;
    private ProgressBar prb;
    private Handler handler = new Handler();
    public Context context;
    private int pStatus = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.clean_ram:
                    progress();

                    TypeWriterTextView animating = (TypeWriterTextView) findViewById(R.id.bottomtxt);
                    animating.setText("");
                    animating.setCharacterDelay(120);
                    animating.displayTextWithAnimation("Cleaning....");
                    cashDelete();
                    deleteCache(context);
                    return true;
                case R.id.clean_web:
                    progress2();

                    TypeWriterTextView animtxt2 = (TypeWriterTextView) findViewById(R.id.bottomtxt);
                    animtxt2.setText("");
                    animtxt2.setCharacterDelay(120);
                    animtxt2.displayTextWithAnimation("Cleaning....");
                    cashDelete();
                    deleteCache(context);
                    return true;
                case R.id.navigation_notifications:
                    finish();
                    System.exit(0);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        MobileAds.initialize(getApplicationContext(), "ca-app-pub-0954165513199363/1407029181");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-0954165513199363/2883762384");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed()
            {
                requestNewInterstitial();

            }
        });

        requestNewInterstitial();



        imgv1 =(ImageView) findViewById(R.id.imgv1);
        imgv2 =(ImageView) findViewById(R.id.imgv2);
        imgv2 =(ImageView) findViewById(R.id.imgv2);



        tv1 = (TextView) findViewById(R.id.toptxt);
        tv2 = (TextView) findViewById(R.id.maintxt);

        int x =0;
        x = getUsedMemorySize(x);
        TypeWriterTextView animate = (TypeWriterTextView) findViewById(R.id.toptxt);
        animate.setText("");
        animate.setCharacterDelay(120);
        animate.displayTextWithAnimation("Total WEB Junk Files " +  Integer.toString(x) + " KB");

        TypeWriterTextView anim = (TypeWriterTextView) findViewById(R.id.bottomtxt);
        anim.setText("");
        anim.setCharacterDelay(120);
        anim.displayTextWithAnimation("Now Clean Web Junk Files and RAM");
    }


    public void progress()
    {
        prb = (ProgressBar) findViewById(R.id.progressBar);
        pStatus= 0;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (pStatus<100)
                {
                    pStatus+=1;
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            prb.setProgress(pStatus);
                            tv2.setText(+ pStatus + "/" + prb.getMax());



                            if (pStatus>=40 && pStatus<=90)
                            {
                                int x =0;
                                x = getUsedMemorySize(x);

                              tv1.setText("RAM CLEANED " +Integer.toString(x) + " KB");

                                TypeWriterTextView anim = (TypeWriterTextView) findViewById(R.id.bottomtxt);
                                anim.setText("");
                                anim.setCharacterDelay(120);
                                anim.displayTextWithAnimation("RAM Cleane successfully Completed");

                            }


                            else if (pStatus==100)
                            {
                                vibrating();

                                if (mInterstitialAd.isLoaded())
                                {
                                    mInterstitialAd.show();
                                }
                            }


                        }
                    });
                    try
                    {
                        Thread.sleep(100);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }


        }).start();

    }

    public void progress2()
    {
        prb = (ProgressBar) findViewById(R.id.progressBar);
        pStatus= 0;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (pStatus<100)
                {
                    pStatus+=1;
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            prb.setProgress(pStatus);
                            tv2.setText(+ pStatus + "/" + prb.getMax());



                            if (pStatus>=40 && pStatus<=90)
                            {
                                int x =0;
                                x = getUsedMemorySize(x);
                                x=x/1024;

                                tv1.setText("WEB JUNK CLEANED " +Integer.toString(x) + " MB");

                                TypeWriterTextView anim = (TypeWriterTextView) findViewById(R.id.bottomtxt);
                                anim.setText("");
                                anim.setCharacterDelay(120);
                                anim.displayTextWithAnimation("WEB Junk Cleane successfully Completed");

                            }



                            else if (pStatus==100)
                            {
                                vibrating();
                                if (mInterstitialAd.isLoaded())
                                {
                                    mInterstitialAd.show();
                                }
                            }


                        }
                    });
                    try
                    {
                        Thread.sleep(100);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }


        }).start();

    }
    

    // cash delete mathode

    public  void cashDelete()
    {
        PackageManager pm = getPackageManager();
        // Get all methods on the PackageManager
        Method[] methods = pm.getClass().getDeclaredMethods();
        for(Method m: methods)
        {
            if (m.getName().equals("freeStorage")) {
                // Found the method I want to use
                try {
                    long desiredFreeStorage = 8 * 1024 * 1024 * 1024; // Request for 8GB of free space
                    m.invoke(pm, desiredFreeStorage , null);
                } catch (Exception e) {
                    // Method invocation failed. Could be a permission problem
                }
                break;
            }
        }
    }

    // get used cash memory size

    public int getUsedMemorySize(int usedSize)
    {

        long freeSize = 0L;
        long totalSize = 0L;
        usedSize = (int) -1L;
        try {
            Runtime info = Runtime.getRuntime();
            freeSize = info.freeMemory();
            totalSize = info.totalMemory();
            usedSize = (int) (totalSize - freeSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usedSize;

    }

    // This Mathode is use for delete cash

    public static void deleteCache(Context context)
    {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    public void vibrating()
    {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
        v.vibrate(400);
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("99B9F6D0B7FAF8583BD7CF437F9CD510")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    // clear web history
    private void clearHistoryChrome()
    {
        ContentResolver cr = getContentResolver();
        if (canClearHistory(cr)) {
            deleteHistoryWhere(cr, null);
        }
    }

    private void deleteHistoryWhere(ContentResolver cr, String whereClause) {
        String CONTENT_URI = "content://com.android.chrome.browser/history";
        Uri URI = Uri.parse(CONTENT_URI);
        Cursor cursor = null;
        try {
            cursor = cr.query(URI, new String[]{"url"}, whereClause,
                    null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    final WebIconDatabase iconDb = WebIconDatabase.getInstance();
                    do {
                        // Delete favicons
                        // TODO don't release if the URL is bookmarked
                        iconDb.releaseIconForPageUrl(cursor.getString(0));
                    } while (cursor.moveToNext());
                    cr.delete(URI, whereClause, null);
                }
            }
        } catch (IllegalStateException e) {
            Log.i("DEBUG_", "deleteHistoryWhere IllegalStateException: " + e.getMessage());
            return;
        } finally {
            if (cursor != null) cursor.close();
        }
        Log.i("DEBUG_", "deleteHistoryWhere: GOOD");
    }

    public boolean canClearHistory(ContentResolver cr) {
        String CONTENT_URI = "content://com.android.chrome.browser/history";
        Uri URI = Uri.parse(CONTENT_URI);
        String _ID = "_id";
        String VISITS = "visits";
        Cursor cursor = null;
        boolean ret = false;
        try {
            cursor = cr.query(URI,
                    new String[]{_ID, VISITS},
                    null, null, null);
            if (cursor != null) {
                ret = cursor.getCount() > 0;
            }
        } catch (IllegalStateException e) {
            Log.i("DEBUG_", "canClearHistory IllegalStateException: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        Log.i("DEBUG_", "canClearHistory: " + ret);
        return ret;
    }


}
