package org.loader.liteplayer.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.loader.liteplayer.R;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.utils.ImageTools;
import org.loader.liteplayer.utils.MusicIconLoader;
import org.loader.liteplayer.utils.MusicUtils;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * 歌曲列表适配器
 * @author longyinzaitian
 */
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {
    private int mPlayingPosition;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private AdapterView.OnItemLongClickListener mOnItemLongClickListener;

    public void setPlayingPosition(int position) {
        mPlayingPosition = position;
    }
    
    public int getPlayingPosition() {
        return mPlayingPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(BaseApplication.getContext(),
                R.layout.music_list_item, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mPlayingPosition == holder.getAdapterPosition()) {
            holder.mark.setVisibility(View.VISIBLE);
        }else {
            holder.mark.setVisibility(View.INVISIBLE);
        }

        Bitmap icon = MusicIconLoader.getInstance()
                        .load(MusicUtils.sMusicList.get(holder.getAdapterPosition())
                        .getImage());
        holder.icon.setImageBitmap(icon == null ?
                ImageTools.scaleBitmap(R.drawable.ic_launcher)
                : ImageTools.scaleBitmap(icon));

        holder.title.setText(MusicUtils.sMusicList.get(holder.getAdapterPosition())
                .getTitle());
        holder.artist.setText(MusicUtils.sMusicList.get(holder.getAdapterPosition())
                .getArtist());

        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(null,
                        null,
                        holder.getAdapterPosition(),
                        holder.getAdapterPosition());
            }
        });

        holder.itemRoot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemLongClickListener.onItemLongClick(null,
                        null,
                        holder.getAdapterPosition(),
                        holder.getAdapterPosition());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return MusicUtils.sMusicList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView title;
        TextView artist;
        View mark;
        LinearLayout itemRoot;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.music_list_icon);
            title = itemView.findViewById(R.id.tv_music_list_title);
            artist = itemView.findViewById(R.id.tv_music_list_artist);
            mark = itemView.findViewById(R.id.music_list_selected);
            itemRoot = itemView.findViewById(R.id.item_ll);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}
