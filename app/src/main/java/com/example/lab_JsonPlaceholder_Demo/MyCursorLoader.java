package com.example.lab_JsonPlaceholder_Demo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.lab_JsonPlaceholder_Demo.model.JsonPlaceholderPhoto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCursorLoader extends CursorLoader {

    private final String TAG = "@@@@@";

    private List<JsonPlaceholderPhoto> mJphPhotoList;
    private List<RequestQueue> mRequestQueueList;
    private int mThreadAmount;

    public MyCursorLoader(
            @NonNull Context context,
            List<JsonPlaceholderPhoto> jphPhotoList,
            List<RequestQueue> requestQueueList,
            int threadAmount
    ) {
        super(context);
        mJphPhotoList = jphPhotoList;
        mRequestQueueList = requestQueueList;
        mThreadAmount = threadAmount;
    }

    @Nullable
    @Override
    protected Cursor onLoadInBackground() {
        int threadCount;
        for (final JsonPlaceholderPhoto jphPhoto : mJphPhotoList) {
            threadCount = jphPhoto.getId();
            ImageRequest imageRequest = new ImageRequest(
                    jphPhoto.getThumbnailUrl(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
//                        Log.d(TAG, jphPhoto.getId() + " : " + jphPhoto.getThumbnailUrl());
                            jphPhoto.setBitmap(response);
//                        mJphPhotoFragment.showThumbnailImage(jphPhoto);
                        }
                    },
                    0,
                    0,
                    ImageView.ScaleType.CENTER_INSIDE,
                    Bitmap.Config.RGB_565,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Accept", "text/html, application/xhtml+xml, application/xml; q=0.9, */*; q=0.8");
                    headers.put("Accept-Encoding", "gzip, deflate, br");
                    headers.put("Accept-Language", "zh-Hant-TW, zh-Hant; q=0.8, en-US; q=0.5, en; q=0.3");
                    headers.put("Cache-Control", "no-cache");
                    headers.put("Connection", "Keep-Alive");
                    headers.put("Host", "via.placeholder.com");
                    headers.put("Upgrade-Insecure-Requests", "1");
                    headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.18363");
                    return headers;
                }
            };
            // add thumbnail image download request to the RequestQueue
            mRequestQueueList.get(threadCount % mThreadAmount).add(imageRequest);
        }
        return null;
    }
}
