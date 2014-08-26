package com.droidkit.images.cache;

import android.graphics.Bitmap;
import com.droidkit.images.loading.log.Log;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by ex3ndr on 26.08.14.
 */
public class MemoryCache {
    private LinkedList<Bitmap> freeBitmaps = new LinkedList<Bitmap>();
    private HashMap<String, BaseBitmapReference> references = new HashMap<String, BaseBitmapReference>();

    public synchronized BitmapReference referenceBitmap(String key, Bitmap bitmap) {
        if (references.containsKey(key)) {
            return references.get(key).createReference();
        }
        BaseBitmapReference baseBitmapReference = new BaseBitmapReference(this, key, bitmap);
        references.put(key, baseBitmapReference);
        return baseBitmapReference.createReference();
    }

    /* package */
    synchronized void onReferenceDie(BaseBitmapReference reference) {
        Log.d("onFreeBitmapAvailable");
        references.remove(reference.key);
        freeBitmaps.add(reference.bitmap);
    }
}