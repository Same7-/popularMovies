package com.udacity.same7.popularmovies;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by same7.
 */
public class VolleySingleton {

    private static VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private ImageLoader mImageLoader;

    private VolleySingleton(Context context){
        this.mCtx = context;
    }
    public static VolleySingleton  getInstance(Context context){
        if(mVolleySingleton == null){
            mVolleySingleton = new VolleySingleton(context);
        }
        return mVolleySingleton;
    }
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null){
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addRequest(Request<T> request){
       getRequestQueue().add(request);
    }


}
