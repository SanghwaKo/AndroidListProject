package com.test.mobile.ksh;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KSH on 2016-11-14.
 */

public class Item implements Parcelable, Comparable {
    private String title;
    private String dateOfPic;
    private int width;
    private int height;
    private String url;
    private int isHasPic; // 0 - the item has a photo, 1 - does not have

    public Item(){
        width = 0;
        height = 0;
        url = "";
        isHasPic = 1;
    }

    public Item(Parcel in){
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(title);
        dest.writeString(dateOfPic);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(url);
        dest.writeInt(isHasPic);
    }

    public void readFromParcel(Parcel in){
        title = in.readString();
        dateOfPic = in.readString();
        width = in.readInt();
        height = in.readInt();
        url = in.readString();
        isHasPic = in.readInt();
    }

    public static final Creator CREATOR = new Creator() {

        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }

    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateOfPic() {
        return dateOfPic;
    }

    public void setDateOfPic(String dateOfPic) {
        this.dateOfPic = dateOfPic;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        isHasPic = 0;
        // The item has a photo
    }

    public int getIsHasPic() {
        return isHasPic;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(obj instanceof Item){
            return toString().equalsIgnoreCase(obj.toString());
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return Constant.TAG_DATE + dateOfPic + Constant.TAG_TITLE + title + " " + Constant.TAG_WIDTH + width
                + " " + Constant.TAG_HEIGHT + height + " " + Constant.TAG_URL + url;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Item){
            return this.toString().compareTo(o.toString());
        }else{
            return -1;
        }
    }
}
