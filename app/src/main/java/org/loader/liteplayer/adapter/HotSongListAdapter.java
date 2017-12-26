package org.loader.liteplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author longyinzaitian
 * @date 2017/12/27.
 */
public class HotSongListAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class ItemSongViewHolder extends RecyclerView.ViewHolder{

        public ItemSongViewHolder(View itemView) {
            super(itemView);
        }
    }
}
