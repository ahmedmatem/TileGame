package com.ahmedmatem.android.tilegame.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ahmedmatem.android.tilegame.R;
import com.ahmedmatem.android.tilegame.config.Config;
import com.ahmedmatem.android.tilegame.models.Tile;
import com.ahmedmatem.android.tilegame.models.TileBoard;

public class BoardAdapter extends BaseAdapter {

    private Context mContext;
    private TileBoard mBoard;

    public BoardAdapter(Context context, TileBoard board) {
        mContext = context;
        mBoard = board;
    }

    @Override
    public int getCount() {
        return mBoard.getTotalRow() * mBoard.getTotalCol();
    }

    @Override
    public Tile getItem(int position) {
        return mBoard.getTiles()[position / mBoard.getTotalCol()][position % mBoard.getTotalCol()];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TileViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tile_view,
                    parent, false);
            viewHolder = new TileViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TileViewHolder) convertView.getTag();
        }

        Tile tile = getItem(position);
        viewHolder.mText.setText(tile.getText());

        Drawable background = null;
        if (mBoard.isTileEmpty(tile)) {
            background = mContext.getResources()
                    .getDrawable(Config.BACKGROUND_HOLO_ORANGE_LIGHT);
        } else {
            background = mContext.getResources()
                    .getDrawable(Config.BACKGROUND_HOLO_ORANGE_DARK);
        }
        viewHolder.mTileContainer.setBackground(background);

        return convertView;
    }

    public class TileViewHolder {

        private FrameLayout mTileContainer;
        public TextView mText;

        public TileViewHolder(View view) {
            View rootView = view;
            mTileContainer = (FrameLayout) rootView.findViewById(R.id.tile_container);
            mText = rootView.findViewById(R.id.tile_text);
        }
    }
}
