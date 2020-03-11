package com.example.lab_JsonPlaceholder_Demo.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;

public class JsonPlaceholderPhoto implements Parcelable {
    private int mAlbumId;
    private int mId;
    private String mTitle;
    private String mUrl;
    private String mThumbnailUrl;
    private Bitmap mBitmap;

    public JsonPlaceholderPhoto() {
    }

    public JsonPlaceholderPhoto(int albumId, int id, String title, String url, String thumbnailUrl) {
        this.mAlbumId = albumId;
        this.mId = id;
        this.mTitle = title;
        this.mUrl = url;
        this.mThumbnailUrl = thumbnailUrl;
    }

    protected JsonPlaceholderPhoto(Parcel in) {
        mAlbumId = in.readInt();
        mId = in.readInt();
        mTitle = in.readString();
        mUrl = in.readString();
        mThumbnailUrl = in.readString();
        mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<JsonPlaceholderPhoto> CREATOR = new Creator<JsonPlaceholderPhoto>() {
        @Override
        public JsonPlaceholderPhoto createFromParcel(Parcel in) {
            return new JsonPlaceholderPhoto(in);
        }

        @Override
        public JsonPlaceholderPhoto[] newArray(int size) {
            return new JsonPlaceholderPhoto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mAlbumId);
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mUrl);
        dest.writeString(mThumbnailUrl);
        dest.writeParcelable(mBitmap, flags);
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public int getId() { return mId; }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setAlbumId(int albumId) {
        this.mAlbumId = albumId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setUrl(String Url) {
        this.mUrl = Url;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.mThumbnailUrl = thumbnailUrl;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
