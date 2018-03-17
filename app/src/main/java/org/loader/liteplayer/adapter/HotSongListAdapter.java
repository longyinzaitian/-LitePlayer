package org.loader.liteplayer.adapter;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.loader.liteplayer.R;
import org.loader.liteplayer.activity.WebViewActivity;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.pojo.MusicList;
import org.loader.liteplayer.pojo.Wiki;
import org.loader.liteplayer.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author longyinzaitian
 * @date 2017/12/27.
 */
public class HotSongListAdapter extends RecyclerView.Adapter<HotSongListAdapter.ItemSongViewHolder> {
    private static final String TAG = "HotSongListAdapter";
    private List<Wiki> hotSongs;

    @Override
    public HotSongListAdapter.ItemSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemSongViewHolder(View.inflate(BaseApplication.getContext(), R.layout.search_result_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final HotSongListAdapter.ItemSongViewHolder holder, int position) {
//        if (holder instanceof ItemSongViewHolder){
            final Wiki hotSong = hotSongs.get(holder.getAdapterPosition());
            if (hotSong == null){
                return;
            }

            ItemSongViewHolder itemSongViewHolder = (ItemSongViewHolder) holder;

        Glide.with(BaseApplication.getContext())
                .load(hotSong.getWiki_cover().getSmall())
                .into(itemSongViewHolder.img);

        String title = hotSong.getWiki_title();
        itemSongViewHolder.songName.setText(title);

        itemSongViewHolder.timeLength.setVisibility(View.GONE);
        itemSongViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener == null){
                    return;
                }
                LogUtil.l(TAG, "click hotSong:" + hotSong);
                Intent intent = new Intent(BaseApplication.getContext(), WebViewActivity.class);
                intent.putExtra("url", hotSong.getWiki_url());
                BaseApplication.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return hotSongs == null ? 0 : hotSongs.size();
    }

    public class ItemSongViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView songName;
        private TextView singerName;
        private TextView timeLength;
        private View rootView;
        ItemSongViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.search_song_img);
            songName = itemView.findViewById(R.id.search_song_name);
            singerName = itemView.findViewById(R.id.search_song_singer_name);
            timeLength = itemView.findViewById(R.id.search_song_time_length);
            rootView = itemView.findViewById(R.id.item_result_ll);
        }
    }

    public void setSongsData(List<Wiki> hotSongs){
        if (hotSongs == null){
            return;
        }
        if (this.hotSongs == null) {
            this.hotSongs = new ArrayList<>();
        }
        this.hotSongs.addAll(hotSongs);
        LogUtil.l(TAG, "hotSongs：" + hotSongs);
        notifyDataSetChanged();
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        /**
         * 点击
         * @param pos int
         * @param url string
         */
        void onItemClick(int pos, String url);
    }
}
