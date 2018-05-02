package com.ahmedmatem.android.tilegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.ahmedmatem.android.tilegame.adapters.BoardAdapter;
import com.ahmedmatem.android.tilegame.config.Config;
import com.ahmedmatem.android.tilegame.models.Tile;
import com.ahmedmatem.android.tilegame.models.TileBoard;

public class MainActivity extends AppCompatActivity {

    private static final String BOARD_KEY = "board_key";

    // parameters
    private static TileBoard mBoard;
    private BoardAdapter mBoardAdapter;

    // views
    private GridView mBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mBoard = (TileBoard) savedInstanceState.getParcelable(BOARD_KEY);
        } else {
            mBoard = new TileBoard(Config.BOARD_DEFAULT_ROWS, Config.BOARD_DEFAULT_COLS);
        }

        mBoardAdapter = new BoardAdapter(this, mBoard);

        mBoardView = (GridView) findViewById(R.id.board_grid_view);
        mBoardView.setAdapter(mBoardAdapter);
        mBoardView.setNumColumns(mBoard.getTotalCol());
        mBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tile clickedTile = mBoard.getTileByPosition(position);
                if (mBoard.isMovable(clickedTile)) {
                    mBoard.move(clickedTile);
                    mBoardAdapter.notifyDataSetChanged();
                    if (mBoard.isGameOver()) {
                        gameOver();
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.illegal_move, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BOARD_KEY, mBoard);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            startNewGame();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startNewGame() {
        mBoard.initialize();
        mBoardAdapter.notifyDataSetChanged();
        mBoardView.setEnabled(true);
    }

    private void gameOver() {
        mBoardView.setEnabled(false);
        Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show();
    }
}
