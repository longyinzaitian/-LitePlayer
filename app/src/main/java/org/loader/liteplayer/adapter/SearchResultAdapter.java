package org.loader.liteplayer.adapter;

import java.util.ArrayList;

import org.loader.liteplayer.R;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.pojo.SearchResult;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyin
 */
public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private ArrayList<SearchResult> mSearchResult;
	private AdapterView.OnItemClickListener mOnItemClickListener;
	
	public SearchResultAdapter(ArrayList<SearchResult> searchResult) {
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
			String artist = mSearchResult.get(holder.getAdapterPosition()).getArtist();
			String album = mSearchResult.get(holder.getAdapterPosition()).getAlbum();

			viewHolderItem.title.setText(mSearchResult.get(holder.getAdapterPosition()).getMusicName());

			if(!TextUtils.isEmpty(artist)) {
				viewHolderItem.artist.setText(artist);
			}else{
				viewHolderItem.artist.setText("未知艺术家");
			}

			if(!TextUtils.isEmpty(album)){
				viewHolderItem.album.setText(album);
			}else {
				viewHolderItem.album.setText("未知专辑");
			}

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
		return mSearchResult.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == getItemCount()-1){
			return Type.BOTTOM.ordinal();
		}else {
			return Type.ITEM.ordinal();
		}
	}

	enum Type{
		/**item view */
		ITEM,
		/**底部view	 */
		BOTTOM
	}

	class ViewHolderItem extends RecyclerView.ViewHolder {
		private TextView title;
		private TextView artist;
		private TextView album;
		private LinearLayout llRoot;

		ViewHolderItem(View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.tv_search_result_title);
			artist = itemView.findViewById(R.id.tv_search_result_artist);
			album = itemView.findViewById(R.id.tv_search_result_album);
			llRoot = itemView.findViewById(R.id.item_result_ll);
		}
	}

	class ViewHolderBottom extends RecyclerView.ViewHolder {

		ViewHolderBottom(View itemView) {
			super(itemView);
		}
	}

	public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
		this.mOnItemClickListener = onItemClickListener;
	}
}
