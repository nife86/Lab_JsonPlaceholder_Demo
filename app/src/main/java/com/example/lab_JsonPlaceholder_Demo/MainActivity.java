package com.example.lab_JsonPlaceholder_Demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lab_JsonPlaceholder_Demo.model.JsonPlaceholderPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        JphPhotoFragment.OnListFragmentInteractionListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    private final String TAG = "@@@@@";
    private final int THREAD_AMOUNT = 5;

    private int mLoaderId = 1;
    LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    JphPhotoFragment mJphPhotoFragment;
    private static final int sColumnCount = 4;
    ShowJphPhotoFragment mShowJphPhotoFragment;

    // array init
    private List<JsonPlaceholderPhoto> mJphPhotoList = new ArrayList<>();

    // volley val init
    private List<RequestQueue> mRequestQueueList = new ArrayList<>();
    private RequestQueue mRequestQueue;
    private String mUrl;

    // init view
    private TextView tv_jphPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");

//        Log.d(TAG, "onCreate: " + this.getClass().getName());
//        Log.d(TAG, "onCreate: " + MainActivity.this.getClass().getName());
//        Log.d(TAG, "onCreate: " + getApplicationContext().getClass().getName());
//        Log.d(TAG, "onCreate: " + this.getApplicationContext().getClass().getName());

        // volley request queue
        initVolleyRequestQueue();

        // init view
        initView();

        // volley for String
//        url = "https://jsonplaceholder.typicode.com/photos/1";
//        url = "https://api.guildwars2.com/v2/wvw/matches/overview?world=1008";
//        volleyRequestQueueForString();

        // volley for JsonObject
//        url = "https://jsonplaceholder.typicode.com/photos/1";
//        volleyRequestQueueForJsonObject();

        // volley for JsonArray
        mUrl = "https://jsonplaceholder.typicode.com/photos";
//        mUrl = "https://jsonplaceholder.typicode.com/albums/1/photos";
        volleyRequestQueueForJsonArray();

        // volley for image
//        url = "https://via.placeholder.com/150/92c952";
//        url = "https://via.placeholder.com/600/92c952";
//        url = "https://source.unsplash.com/random/150x150";
//        volleyRequestQueueForImage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tv_jphPhoto.setEnabled(false);
        ProgressDialogUtil.showProgressDialog(
                MainActivity.this,
                getResources().getString(R.string.downloading_json)
        );
    }

    private void initVolleyRequestQueue() {
        Log.d(TAG, "initVolleyRequestQueue: ");
        mRequestQueue = Volley.newRequestQueue(this.getApplicationContext());

        for (int i = 0; i < THREAD_AMOUNT; i++) {
            mRequestQueueList.add(i, Volley.newRequestQueue(this.getApplicationContext()));
        }
    }

    private void initView() {
        Log.d(TAG, "initView: ");
        tv_jphPhoto = findViewById(R.id.tv_jphPhoto);
        tv_jphPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragment();
            }
        });
    }

    private void initLoader() {
        Log.d(TAG, "initLoader: ");
        loaderCallbacks = this;
        LoaderManager.getInstance(this).initLoader(mLoaderId, null, loaderCallbacks);
    }

    private void initFragment() {
        Log.d(TAG, "init_itemFragment: ");

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mJphPhotoFragment = JphPhotoFragment.newInstance(sColumnCount, mJphPhotoList);
        mFragmentTransaction.add(R.id.ll_main, mJphPhotoFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private void showJphPhotoFragment(int id, String title, Bitmap bitmap) {
        Log.d(TAG, "showJphPhotoFragment: ");
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mShowJphPhotoFragment = ShowJphPhotoFragment.newInstance(id, title, bitmap);
        mFragmentTransaction.replace(R.id.ll_main, mShowJphPhotoFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private void volleyRequestQueueForString() {
//        Log.d(TAG, "volleyRequestQueueForString: " + this.getClass().getName());
        Log.d(TAG, "volleyRequestQueueForString: ");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response.length());
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });
        mRequestQueue.add(stringRequest);
    }

    private void volleyRequestQueueForJsonObject() {
        Log.d(TAG, "volleyRequestQueueForJsonObject: ");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: ");
                        Log.d(TAG, response.toString());

//                        JsonPlaceHolderPhoto jsonPlaceHolderPhoto = null;
//                        try {
//                            jsonPlaceHolderPhoto = new JsonPlaceHolderPhoto(
//                                    response.getInt("albumId"),
//                                    response.getInt("id"),
//                                    response.getString("title"),
//                                    response.getString("url"),
//                                    response.getString("thumbnailUrl")
//                            );
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

//                        JsonPlaceHolderPhoto jsonPlaceHolderPhoto = new JsonPlaceHolderPhoto();
                        //                        try {
//                            jsonPlaceHolderPhoto.setAlbumId(response.getInt("albumId"));
//                            jsonPlaceHolderPhoto.setId(response.getInt("id"));
//                            jsonPlaceHolderPhoto.setTitle(response.getString("title"));
//                            jsonPlaceHolderPhoto.setUrl(response.getString("url"));
//                            jsonPlaceHolderPhoto.setThumbnailUrl(response.getString("thumbnailUrl"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: ");
                    }
                }
        );
        mRequestQueue.add(jsonObjectRequest);
    }

    private void volleyRequestQueueForJsonArray() {
        Log.d(TAG, "volleyRequestQueueForJsonArray: ");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "volleyRequestQueueForJsonArray.onResponse: ");
                        Log.d(TAG, "response length: " + response.length());

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                if (response.getJSONObject(i) instanceof JSONObject) {
                                    mJphPhotoList.add(i, new JsonPlaceholderPhoto(
                                            response.getJSONObject(i).getInt("albumId"),
                                            response.getJSONObject(i).getInt("id"),
                                            response.getJSONObject(i).getString("title"),
                                            response.getJSONObject(i).getString("url"),
                                            response.getJSONObject(i).getString("thumbnailUrl")
                                    ));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.d(TAG, "mJphPhotoList init finished");

                        // init download loader
                        initLoader();

                        ProgressDialogUtil.dismissProgressDialog();

                        tv_jphPhoto.setEnabled(true);

//                        for (JsonPlaceholderPhoto jphPhoto : mJphPhotoList) {
//                            Log.d(TAG, String.format(
//                                    "albumId: %d\n id: %d\n title: %s\n url: %s\n thumbnailUrl: %s",
//                                    jphPhoto.getAlbumId(),
//                                    jphPhoto.getId(),
//                                    jphPhoto.getTitle(),
//                                    jphPhoto.getUrl(),
//                                    jphPhoto.getThumbnailUrl())
//                            );
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: ");
                    }
                }
        );
        // add the json array request to the RequestQueue
        mRequestQueue.add(jsonArrayRequest);
    }

    private void volleyRequestQueueForThumbnailImage(final JsonPlaceholderPhoto jphPhoto) {
//        Log.d(TAG, "volleyRequestQueueForThumbnailImage: ");

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
        mRequestQueue.add(imageRequest);
    }

    private void volleyRequestQueueForImage(final JsonPlaceholderPhoto jphPhoto) {
        Log.d(TAG, "volleyRequestQueueForImage: ");

        ImageRequest imageRequest = new ImageRequest(
                jphPhoto.getUrl(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Log.d(TAG, "volleyRequestQueueForImage.onResponse: " + response.getByteCount());
                        showJphPhotoFragment(
                                jphPhoto.getId(),
                                jphPhoto.getTitle(),
                                response);

                        ProgressDialogUtil.dismissProgressDialog();
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_INSIDE,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "VolleyError: " + error);
                        Log.d(TAG, "networkResponse.statusCode: " + error.networkResponse.statusCode);
                        Log.d(TAG, "networkResponse.data.length: " + error.networkResponse.data.length);
                        Log.d(TAG, "networkResponse.data: \n" + new String(error.networkResponse.data));
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
        mRequestQueue.add(imageRequest);
    }

    @Override
    public void onListFragmentInteraction(JsonPlaceholderPhoto jphPhoto) {
        Log.d(TAG, "onListFragmentInteraction: ");
        ProgressDialogUtil.showProgressDialog(
                MainActivity.this,
                getResources().getString(R.string.downloading_photo)
        );
        volleyRequestQueueForImage(jphPhoto);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyCursorLoader(this, mJphPhotoList, mRequestQueueList, THREAD_AMOUNT);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
