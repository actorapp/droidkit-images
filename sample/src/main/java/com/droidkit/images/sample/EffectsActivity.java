package com.droidkit.images.sample;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.droidkit.images.ops.ImageEffects;


public class EffectsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);
        Bitmap original = ((BitmapDrawable) getResources().getDrawable(R.drawable.demo_girl)).getBitmap();
        Bitmap grayscale = ImageEffects.grayscale(original);
        ((ImageView) findViewById(R.id.grayscale)).setImageBitmap(grayscale);
    }
}
