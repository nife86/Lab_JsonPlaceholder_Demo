package com.example.lab_JsonPlaceholder_Demo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ShowJphPhotoFragment extends Fragment {

    private final String TAG = "@@@@@";

    private static final String ARG_ID = "arg-id";
    private static final String ARG_TITLE = "arg-title";
    private static final String ARG_PHOTO = "arg-photo";

    private int mId;
    private String mTitle;
    private Bitmap mBitmap;

    private ImageView iv_showJphPhoto;
    private TextView tv_showJphPhoto;

    public ShowJphPhotoFragment() {
        // Required empty public constructor
    }

    public static ShowJphPhotoFragment newInstance(
            int id,
            String title,
            Bitmap bitmap) {
        ShowJphPhotoFragment fragment = new ShowJphPhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_TITLE, title);
        args.putParcelable(ARG_PHOTO, bitmap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ShowJphPhotoFragment.onCreate: ");
        if (getArguments() != null) {
            mId = getArguments().getInt(ARG_ID);
            mTitle = getArguments().getString(ARG_TITLE);
            mBitmap = getArguments().getParcelable(ARG_PHOTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "ShowJphPhotoFragment.onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_jph_photo, container, false);

        iv_showJphPhoto = view.findViewById(R.id.iv_showJphPhoto);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(view.getResources(),mBitmap);
        iv_showJphPhoto.setBackground(bitmapDrawable);

        tv_showJphPhoto = view.findViewById(R.id.tv_showJphPhoto);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id: ").append(mId).append("\n\ntitle: ").append(mTitle);
        tv_showJphPhoto.setText(stringBuilder);

        return view;
    }
}
