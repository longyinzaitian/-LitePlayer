package org.loader.liteplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.loader.liteplayer.R;
import org.loader.liteplayer.activity.WebViewActivity;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.application.Const;
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
    private List<Wiki> hotSongs = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public HotSongListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public HotSongListAdapter.ItemSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtil.l(TAG, "onCreateViewHolder");
        return new ItemSongViewHolder(mLayoutInflater.inflate(R.layout.search_result_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final HotSongListAdapter.ItemSongViewHolder holder, int position) {
        LogUtil.l(TAG, "onBindViewHolder");
//        if (holder instanceof ItemSongViewHolder){
        final Wiki hotSong = hotSongs.get(holder.getAdapterPosition());
        if (hotSong == null) {
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
                LogUtil.l(TAG, "click hotSong:" + hotSong);
                Intent intent = new Intent(BaseApplication.getContext(), WebViewActivity.class);
                intent.putExtra(Const.INTENT_KEY_URL, hotSong.getWiki_url());
                BaseApplication.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        LogUtil.l(TAG, "getItemCount");
        return hotSongs == null ? 0: hotSongs.size();
    }

    @Override
    public long getItemId(int position) {
        LogUtil.l(TAG, "getItemId");
        return position;
    }

    public class ItemSongViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView songName;
        private TextView singerName;
        private TextView timeLength;
        private View rootView;
        ItemSongViewHolder(View itemView) {
            super(itemView);
            LogUtil.l(TAG, "ItemSongViewHolder");
            img = itemView.findViewById(R.id.search_song_img);
            songName = itemView.findViewById(R.id.search_song_name);
            singerName = itemView.findViewById(R.id.search_song_singer_name);
            timeLength = itemView.findViewById(R.id.search_song_time_length);
            rootView = itemView.findViewById(R.id.item_result_ll);
        }
    }

    public void setSongsData(List<Wiki> hotSongs) {
        if (hotSongs == null) {
            return;
        }

        this.hotSongs.addAll(hotSongs);
        notifyDataSetChanged();
        LogUtil.l(TAG, "setSongsData");
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
