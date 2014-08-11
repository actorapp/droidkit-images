package com.droidkit.images.sample;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.common.ImageSaveException;
import com.droidkit.images.libjpeg.ImageLoadingEx;
import com.droidkit.images.ops.ImageLoading;
import com.droidkit.images.ops.ImageScaling;

import java.util.logging.Logger;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] files = new String[]{
                "/sdcard/test/girl1",
                "/sdcard/test/girl2",
                "/sdcard/test/girl3",
                "/sdcard/test/girl4",

                "/sdcard/test/red1",
                "/sdcard/test/gradient2",
                "/sdcard/test/gradient3",
        };

        for (int i = 0; i < files.length; i++) {
            try {
                Bitmap img = ImageLoading.loadBitmap(files[i] + ".jpg");
                Bitmap scaled = ImageScaling.scaleFit(img, 300, 300);
                ImageLoading.saveBmp(scaled, files[i] + "_scaled2.bmp");
            } catch (ImageLoadException e) {
                e.printStackTrace();
            } catch (ImageSaveException e) {
                e.printStackTrace();
            }
        }

//        long start = System.currentTimeMillis();
//        for (int i = 0; i < files.length; i++) {
//            try {
//                Bitmap img = ImageLoading.loadBitmap(files[i] + ".jpg");
//            } catch (ImageLoadException e) {
//                e.printStackTrace();
//            }
//        }
//        long defaultDuration = System.currentTimeMillis() - start;
//        start = System.currentTimeMillis();
//        for (int i = 0; i < files.length; i++) {
//            try {
//                Bitmap img = ImageLoadingEx.loadLibJpeg(files[i] + ".jpg");
//            } catch (ImageLoadException e) {
//                e.printStackTrace();
//            }
//        }
//        long libjpegDuration = System.currentTimeMillis() - start;
//
//        Log.d("LIBJPEG", "Default duration: " + defaultDuration + " ms");
//        Log.d("LIBJPEG", "Libjpeg duration: " + libjpegDuration + " ms");

        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
