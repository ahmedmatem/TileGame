package com.ahmedmatem.android.tilegame.models;

import com.ahmedmatem.android.tilegame.MatrixPosition;
import com.ahmedmatem.android.tilegame.config.Config;

import java.util.ArrayList;
import java.util.Random;

public class Shuffler {

    private int mRow;

    private int mCol;

    public Shuffler(int row, int col) {
        mRow = row;
        mCol = col;
    }

    public int[][] shuffle() {
        int[][] numbers = new int[mRow][mCol];
        for (int row = 0; row < mRow; row++) {
            for (int col = 0; col < mCol; col++) {
                numbers[row][col] = row * mCol + col + 1;
            }
        }
        numbers[mRow-1][mCol-1] = 0;
        // shuffle numbers as making [(row * col)^2/2 * shuffleDeep] times occasionally moves
        int movesCount = ((int) Math.pow(mRow * mCol, 2) / 2) *
                Config.SHUFFLE_DEEP_DEFAULT_VALUE;
        MatrixPosition destinationPosition =
                new MatrixPosition(mRow-1, mCol-1);
        for (int i = 0; i < movesCount; i++) {
            makeRandomMove(destinationPosition, numbers);
        }
        return numbers;
    }

    private void makeRandomMove(MatrixPosition destinationPosition, int[][] numbers) {
        MatrixPosition sourcePosition = getSourcePosition(destinationPosition, numbers);
        swapNumbers(destinationPosition, sourcePosition, numbers);
        destinationPosition.setRow(sourcePosition.getRow());
        destinationPosition.setCol(sourcePosition.getCol());
    }

    private MatrixPosition getSourcePosition(MatrixPosition destinationPosition, int[][] numbers) {
        ArrayList<MatrixPosition> sourcePositions = new ArrayList<>();
        // left
        if (destinationPosition.getCol() > 0) {
            sourcePositions.add(new MatrixPosition(destinationPosition.getRow(),
                    destinationPosition.getCol() - 1));
        }
        // right
        if (destinationPosition.getCol() < mCol - 1) {
            sourcePositions.add(new MatrixPosition(destinationPosition.getRow(),
                    destinationPosition.getCol() + 1));
        }
        // top
        if (destinationPosition.getRow() > 0) {
            sourcePositions.add(new MatrixPosition(destinationPosition.getRow() - 1,
                    destinationPosition.getCol()));
        }
        // bottom
        if (destinationPosition.getRow() < mRow - 1) {
            sourcePositions.add(new MatrixPosition(destinationPosition.getRow() + 1,
                    destinationPosition.getCol()));
        }
        return sourcePositions.get(new Random().nextInt(sourcePositions.size()));
    }

    private void swapNumbers(MatrixPosition destinationPosition,
                             MatrixPosition sourcePosition,
                             int[][] numbers) {
        numbers[destinationPosition.getRow()][destinationPosition.getCol()] =
                numbers[sourcePosition.getRow()][sourcePosition.getCol()];
        numbers[sourcePosition.getRow()][sourcePosition.getCol()] = 0;
    }
}
