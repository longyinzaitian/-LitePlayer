package org.loader.liteplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.loader.liteplayer.R;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.pojo.HotSong;
import org.loader.liteplayer.utils.MusicUtils;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2017/12/27.
 */
public class HotSongListAdapter extends RecyclerView.Adapter {
    private List<HotSong> hotSongs = null;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemSongViewHolder(View.inflate(BaseApplication.getContext(), R.layout.search_result_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemSongViewHolder){
            HotSong hotSong = hotSongs.get(holder.getAdapterPosition());
            if (hotSong == null){
                return;
            }

            ItemSongViewHolder itemSongViewHolder = (ItemSongViewHolder) holder;

            itemSongViewHolder.songName.setText(hotSong.getSongname());
            itemSongViewHolder.singerName.setText(hotSong.getSingername());
            itemSongViewHolder.timeLength.setText(MusicUtils.getSongTimeLength(hotSong.getSeconds()));
        }
    }

    @Override
    public int getItemCount() {
        return hotSongs == null ? 0 : hotSongs.size();
    }

    private class ItemSongViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView songName;
        private TextView singerName;
        private TextView timeLength;
        ItemSongViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.search_song_img);
            songName = itemView.findViewById(R.id.search_song_name);
            singerName = itemView.findViewById(R.id.search_song_singer_name);
            timeLength = itemView.findViewById(R.id.search_song_time_length);
        }
    }

    public void setSongsData(List<HotSong> hotSongs){
        if (hotSongs == null){
            return;
        }

        this.hotSongs = hotSongs;
        notifyDataSetChanged();
    }
}
