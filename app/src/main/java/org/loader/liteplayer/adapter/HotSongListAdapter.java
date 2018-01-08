package org.loader.liteplayer.adapter;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.loader.liteplayer.R;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.network.DownFileCallback;
import org.loader.liteplayer.network.NetWorkUtil;
import org.loader.liteplayer.pojo.HotSong;
import org.loader.liteplayer.utils.LogUtil;
import org.loader.liteplayer.utils.MusicUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author longyinzaitian
 * @date 2017/12/27.
 */
public class HotSongListAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HotSongListAdapter";
    private List<HotSong> hotSongs = new ArrayList<>();
    private String directory = Environment.getExternalStorageDirectory() + "/liteplayer/song";

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemSongViewHolder(View.inflate(BaseApplication.getContext(), R.layout.search_result_item, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof ItemSongViewHolder){
            final HotSong hotSong = hotSongs.get(holder.getAdapterPosition());
            if (hotSong == null){
                return;
            }

            ItemSongViewHolder itemSongViewHolder = (ItemSongViewHolder) holder;

            Glide.with(BaseApplication.getContext()).load(hotSong.getAlbumpic_small()).into(itemSongViewHolder.img);
            itemSongViewHolder.songName.setText(hotSong.getSongname());
            itemSongViewHolder.singerName.setText(hotSong.getSingername());
            itemSongViewHolder.timeLength.setText("时长："+MusicUtils.getSongTimeLength(hotSong.getSeconds()));
            itemSongViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener == null){
                        return;
                    }
                    LogUtil.l(TAG, "click hotSong:" + hotSong);
                    final String fileName = hotSong.getSongname() + "."+hotSong.getSongid()+".mp3";
                    NetWorkUtil.startDownFile(hotSong.getDownUrl(), directory, fileName, new DownFileCallback() {
                        @Override
                        public void updateProgress(int pro) {
                            LogUtil.l(TAG, "update progress. pro:" + pro);
                        }

                        @Override
                        public void onResponseFile(File file) {
                            LogUtil.l(TAG, "name:" + file.getName() + ", path:" + file.getAbsolutePath());
                        }
                    });
                }
            });
//        }
    }

    @Override
    public int getItemCount() {
        return hotSongs.size();
    }

    private class ItemSongViewHolder extends RecyclerView.ViewHolder{
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

    public void setSongsData(List<HotSong> hotSongs){
        if (hotSongs == null){
            return;
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
