package org.loader.liteplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.loader.liteplayer.R;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.pojo.ContentList;

import java.util.ArrayList;
import java.util.List;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ContentList.ContentItem> mSearchResult;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    
    public SearchResultAdapter(ArrayList<ContentList.ContentItem> searchResult) {
        mSearchResult = searchResult;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Type.ITEM.ordinal()){
            return new ViewHolderItem(View.inflate(BaseApplication.getContext(),
                    R.layout.search_result_item, null));
        }else{
            TextView footerView = new TextView(BaseApplication.getContext());
            footerView.setText("加载下一页...");
            footerView.setGravity(Gravity.CENTER);
            footerView.setVisibility(View.GONE);
            return new SearchResultAdapter.ViewHolderBottom(footerView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderItem){
            ViewHolderItem viewHolderItem = (ViewHolderItem)holder;
            String singerName = mSearchResult.get(holder.getAdapterPosition()).getSinger_name();
            String songName = mSearchResult.get(holder.getAdapterPosition()).getTitle();
//            long length = mSearchResult.get(holder.getAdapterPosition()).getSeconds();

            viewHolderItem.title.setText(songName);
            viewHolderItem.artist.setText(singerName);
//            viewHolderItem.length.setText(MusicUtils.getFileLength(length));

            viewHolderItem.llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(null,
                            null,
                            holder.getAdapterPosition(),
                            holder.getAdapterPosition());
                }
            });
        }else if (holder instanceof SearchResultAdapter.ViewHolderBottom){
            SearchResultAdapter.ViewHolderBottom viewHolderBottom =
                    (SearchResultAdapter.ViewHolderBottom)holder;
        }
    }

    @Override
    public int getItemCount() {
        return mSearchResult==null?0:mSearchResult.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1){
            return Type.BOTTOM.ordinal();
        }else {
            return Type.ITEM.ordinal();
        }
    }

    private enum Type{
        /**item view */
        ITEM,
        /**底部view     */
        BOTTOM
    }

    private class ViewHolderItem extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView artist;
        private TextView length;
        private LinearLayout llRoot;

        ViewHolderItem(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.search_song_img);
            title = itemView.findViewById(R.id.search_song_name);
            artist = itemView.findViewById(R.id.search_song_singer_name);
            length = itemView.findViewById(R.id.search_song_time_length);
            llRoot = itemView.findViewById(R.id.item_result_ll);
        }
    }

    private class ViewHolderBottom extends RecyclerView.ViewHolder {

        ViewHolderBottom(View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setSearchResult(List<ContentList.ContentItem> searchResults){
        mSearchResult = searchResults;
        notifyDataSetChanged();
    }
}
