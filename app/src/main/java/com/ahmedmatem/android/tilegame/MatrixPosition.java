package com.ahmedmatem.android.tilegame;

public class MatrixPosition {

    private int mRow;

    private int mCol;

    public MatrixPosition(int row, int col) {
        mRow = row;
        mCol = col;
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
}
