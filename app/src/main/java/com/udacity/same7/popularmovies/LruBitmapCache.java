package com.udacity.same7.popularmovies;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * Created by same7
 */
public class LruBitmapCache extends LruCache<String,Bitmap> implements ImageCache
      {
          public static int getDefaultLruCacheSize() {
              final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
              return maxMemory / 8;
          }

          public LruBitmapCache() {
              this(getDefaultLruCacheSize());
          }

          public LruBitmapCache(int sizeInKiloBytes) {
              super(sizeInKiloBytes);
          }

          @Override
          public Bitmap getBitmap(String url) {
              return get(url);
          }

          @Override
          public void putBitmap(String url, Bitmap bitmap) {
              put(url, bitmap);
          }
      }
