package com.droidkit.images.sample;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.droidkit.images.ops.ImageDrawing;
import com.droidkit.images.ops.ImageEffects;
import com.droidkit.images.ops.ImageScaling;


public class EffectsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);
        Bitmap original = ((BitmapDrawable) getResources().getDrawable(R.drawable.demo_girl)).getBitmap();

        Bitmap grayscale = ImageEffects.grayscale(original);
        Bitmap scaleFill = ImageScaling.scaleFill(original, 400, 400);
        Bitmap scaleFit = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        ImageScaling.scaleFit(original, scaleFit, Color.GRAY);
        Bitmap round = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        ImageDrawing.drawInRound(scaleFill, round);
        Bitmap roundedCorners = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        ImageDrawing.drawRoundedCorners(scaleFill, roundedCorners, 40, 0);
        ((ImageView) findViewById(R.id.grayscale)).setImageBitmap(grayscale);
        ((ImageView) findViewById(R.id.scaleFill)).setImageBitmap(scaleFill);
        ((ImageView) findViewById(R.id.scaleFit)).setImageBitmap(scaleFit);
        ((ImageView) findViewById(R.id.round)).setImageBitmap(round);
        ((ImageView) findViewById(R.id.rounded)).setImageBitmap(roundedCorners);
    }
}
