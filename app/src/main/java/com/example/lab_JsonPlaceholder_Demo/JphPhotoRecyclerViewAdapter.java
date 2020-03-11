package com.example.lab_JsonPlaceholder_Demo;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab_JsonPlaceholder_Demo.JphPhotoFragment.OnListFragmentInteractionListener;
import com.example.lab_JsonPlaceholder_Demo.model.JsonPlaceholderPhoto;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class JphPhotoRecyclerViewAdapter extends RecyclerView.Adapter<JphPhotoRecyclerViewAdapter.ViewHolder> {

    private final String TAG = "@@@@@";

    //    private final List<DummyItem> mValues;
    private List<JsonPlaceholderPhoto> mJphPhotoList;
    private int mSizeOfView;
    private final OnListFragmentInteractionListener mListener;
    private StringBuilder mStringBuilder = new StringBuilder();

    public JphPhotoRecyclerViewAdapter(List<JsonPlaceholderPhoto> jsonPlaceholderPhotoList, int sizeOfView, OnListFragmentInteractionListener listener) {
        mJphPhotoList = jsonPlaceholderPhotoList;
        mListener = listener;
        mSizeOfView = sizeOfView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_jph_photo, parent, false);
        int sizeOfView = parent.getMeasuredWidth() / mSizeOfView;
        view.setMinimumWidth(sizeOfView);
        view.setMinimumHeight(sizeOfView);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mJphPhotoList.get(position);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(
                holder.mView.getResources(),
                holder.mItem.getBitmap());
        holder.mIvView.setBackground(bitmapDrawable);

        mStringBuilder.delete(0, mStringBuilder.length())
                .append("\n")
                .append(holder.mItem.getId())
                .append("\n\n")
                .append(holder.mItem.getTitle())
                .append("\n");

        holder.mTvView.setText(mStringBuilder.toString());

//        Log.d(TAG, "onBindViewHolder: getId - " + holder.mItem.getId());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mJphPhotoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIvView;
        public final TextView mTvView;
        public JsonPlaceholderPhoto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIvView = view.findViewById(R.id.iv_jphPhoto);
            mTvView = view.findViewById(R.id.tv_jphPhoto);
        }
    }
}
