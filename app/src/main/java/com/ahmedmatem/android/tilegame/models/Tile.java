package com.ahmedmatem.android.tilegame.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Tile implements Parcelable {

    private String mText;

    private int mRow;

    private int mCol;

    public Tile(String text, int row, int col) {
        mText = text;
        mRow = row;
        mCol = col;
    }

    protected Tile(Parcel in) {
        mText = in.readString();
        mRow = in.readInt();
        mCol = in.readInt();
    }

    public static final Creator<Tile> CREATOR = new Creator<Tile>() {
        @Override
        public Tile createFromParcel(Parcel in) {
            return new Tile(in);
        }

        @Override
        public Tile[] newArray(int size) {
            return new Tile[size];
        }
    };

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getRow() {
        return mRow;
    }

    public void setRow(int row) {
        mRow = row;
    }

    public int getCol() {
        return mCol;
    }

    public void setCol(int col) {
        mCol = col;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mText);
        dest.writeInt(mRow);
        dest.writeInt(mCol);
    }
}
