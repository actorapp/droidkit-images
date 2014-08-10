package com.droidkit.images.sample;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.droidkit.images.common.ImageLoadException;
import com.droidkit.images.common.ImageSaveException;
import com.droidkit.images.ops.ImageLoading;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] files = new String[]{
                "/sdcard/test/girl1",
                "/sdcard/test/girl2",
                "/sdcard/test/girl3",
                "/sdcard/test/girl4",

                "/sdcard/test/gradient1",
                "/sdcard/test/gradient2",
                "/sdcard/test/gradient3",
        };

        for (int i = 0; i < files.length; i++) {
            try {
                Bitmap img = ImageLoading.loadBitmap(files[i] + ".jpg");
                // ImageLoading.saveBmp(img, files[i] + "_orig.bmp");
                for (int q = 80; q > 60; q--) {
                    ImageLoading.saveJpeg(img, files[i] + "_" + q + ".jpg", q);
                }
            } catch (ImageLoadException e) {
                e.printStackTrace();
            } catch (ImageSaveException e) {
                e.printStackTrace();
            }
        }


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
