package com.ahmedmatem.android.tilegame.models;

public abstract class Board {
    public int mTotalRow;
    public int mTotalCol;

    public Board(int totalRow, int totalCol) {
        mTotalRow = totalRow;
        mTotalCol = totalCol;
    }

    abstract void initialize();

    public int getTotalRow() {
        return mTotalRow;
    }

    public void setTotalRow(int row) {
        mTotalRow = row;
    }

    public int getTotalCol() {
        return mTotalCol;
    }

    public void setTotalCol(int col) {
        mTotalCol = col;
    }
}
