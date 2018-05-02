package com.ahmedmatem.android.tilegame.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.ahmedmatem.android.tilegame.config.Config;

import java.util.ArrayList;
import java.util.Random;

public class TileBoard extends Board implements Parcelable {

    private Tile[][] mTiles;

    public TileBoard(int totalRow, int totalCol) {
        super(totalRow, totalCol);
        initialize();
    }

    public TileBoard(Parcel in) {
        super(in.readInt(), in.readInt());
        for (int row = 0; row < getTotalRow(); row++) {
            mTiles[row] = in.createTypedArray(Tile.CREATOR);
        }
    }

    public static final Creator<TileBoard> CREATOR = new Creator<TileBoard>() {
        @Override
        public TileBoard createFromParcel(Parcel in) {
            return new TileBoard(in);
        }

        @Override
        public TileBoard[] newArray(int size) {
            return new TileBoard[size];
        }
    };

    @Override
    public void initialize() {
        mTiles = new Tile[getTotalRow()][getTotalCol()];
        shuffleTiles();
    }

    public boolean isTileEmpty(Tile tile) {
        if (tile.getText().equals(Config.EMPTY_TILE_TEXT)) {
            return true;
        }
        return false;
    }

    public boolean isMovable(Tile tile) {
        ArrayList<Tile> movableTiles = findMovableTiles();
        if (movableTiles.contains(tile)) {
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        int position = 0;
        for (int row = 0; row < getTotalRow(); row++) {
            for (int col = 0; col < getTotalCol(); col++) {
                position++;
                if (position == (getTotalCol() * getTotalRow())) {
                    return true;
                }
                if (!mTiles[row][col].getText().equals(String.valueOf(position))) {
                    return false;
                }
            }
        }
        return true;
    }

    public Tile[][] getTiles() {
        return mTiles;
    }

    public Tile getTileByPosition(int position) {
        int row = position / getTotalCol();
        int col = position % getTotalCol();
        return mTiles[row][col];
    }

    public void move(Tile tile) {
        Tile emptyTile = findEmptyTile();
        swapTiles(tile, emptyTile);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        for (int row = 0; row < getTotalRow(); row++) {
            dest.writeInt(getTotalRow());
            dest.writeInt(getTotalCol());
            dest.writeTypedArray(mTiles[row], 0);
        }
    }

    private void shuffleTiles() {
        int[][] shuffledNumbers = new Shuffler(getTotalRow(), getTotalCol()).shuffle();
        String name = null;
        for (int row = 0; row < getTotalRow(); row++) {
            for (int col = 0; col < getTotalCol(); col++) {
                if (shuffledNumbers[row][col] == 0) {
                    name = String.valueOf(Config.EMPTY_TILE_TEXT);
                } else {
                    name = String.valueOf(shuffledNumbers[row][col]);
                }
                mTiles[row][col] = new Tile(name, row, col);
            }
        }
    }

    private Tile findEmptyTile() {
        Tile result = null;
        for (int row = 0; row < getTotalRow(); row++) {
            for (int col = 0; col < getTotalCol(); col++) {
                if (mTiles[row][col].getText().equals(Config.EMPTY_TILE_TEXT)) {
                    return mTiles[row][col];
                }
            }
        }
        return result;
    }

    private ArrayList<Tile> findMovableTiles() {
        ArrayList<Tile> movableTiles = new ArrayList<>();
        Tile emptyTile = findEmptyTile();
        // Check tiles around empty tile
        // left
        if (emptyTile.getCol() > 0) {
            movableTiles.add(mTiles[emptyTile.getRow()][emptyTile.getCol() - 1]);
        }
        // right
        if (emptyTile.getCol() < getTotalCol() - 1) {
            movableTiles.add(mTiles[emptyTile.getRow()][emptyTile.getCol() + 1]);
        }
        //top
        if (emptyTile.getRow() > 0) {
            movableTiles.add(mTiles[emptyTile.getRow() - 1][emptyTile.getCol()]);
        }
        // bottom
        if (emptyTile.getRow() < getTotalRow() - 1) {
            movableTiles.add(mTiles[emptyTile.getRow() + 1][emptyTile.getCol()]);
        }

        return movableTiles;
    }

    private void swapTiles(Tile tile1, Tile tile2) {
        String tile2Text = tile2.getText();
        tile2.setText(tile1.getText());
        tile1.setText(tile2Text);
    }
}
