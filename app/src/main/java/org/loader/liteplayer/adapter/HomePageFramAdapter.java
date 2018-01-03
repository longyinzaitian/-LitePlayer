package org.loader.liteplayer.adapter;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.loader.liteplayer.R;
import org.loader.liteplayer.application.BaseApplication;

/**
 * @author husyin
 * @date 2018/1/3
 */

public class HomePageFramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0){
            return new HeaderViewHolder(View.inflate(BaseApplication.getContext(), R.layout.item_home_page_top, null));
        }else if (viewType == 1){
            return new BottomViewHolder(View.inflate(BaseApplication.getContext(), R.layout.item_home_page_bottom, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            setHeaderViewHolderData(headerViewHolder);
        }else if (holder instanceof BottomViewHolder){
            BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;
            setBottomViewHolderData(bottomViewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        ImageView topImg;
        ImageView centerStartImg,centerMiddleImg,centerEndImg;
        ImageView bottomImg;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            topImg = itemView.findViewById(R.id.item_home_page_top_iv);
            centerStartImg = itemView.findViewById(R.id.item_home_page_center_start_iv);
            centerMiddleImg = itemView.findViewById(R.id.item_home_page_center_middle_iv);
            centerEndImg = itemView.findViewById(R.id.item_home_page_center_end_iv);
            bottomImg = itemView.findViewById(R.id.item_home_page_bottom_iv);
        }
    }

    private class BottomViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public BottomViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.home_page_local_songs_list);
        }
    }

    /**
     * 设置页面上部数据
     */
    private void setHeaderViewHolderData(HeaderViewHolder headerViewHolder){
        headerViewHolder.topImg.setOnClickListener(onClickListener);
        headerViewHolder.centerStartImg.setOnClickListener(onClickListener);
        headerViewHolder.centerMiddleImg.setOnClickListener(onClickListener);
        headerViewHolder.centerEndImg.setOnClickListener(onClickListener);
        headerViewHolder.bottomImg.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //顶部
                case R.id.item_home_page_top_iv:
                    break;
                //中间
                case R.id.item_home_page_center_start_iv:
                    break;
                case R.id.item_home_page_center_middle_iv:
                    break;
                case R.id.item_home_page_center_end_iv:
                    break;
                //底部
                case R.id.item_home_page_bottom_iv:
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置页面下部数据
     */
    private void setBottomViewHolderData(BottomViewHolder bottomViewHolder){
        MusicListAdapter adapter = new MusicListAdapter();
        bottomViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext()));
        bottomViewHolder.recyclerView.addItemDecoration(new DividerItemDecoration(BaseApplication.getContext(), RecyclerView.HORIZONTAL));
        bottomViewHolder.recyclerView.setAdapter(adapter);
    }

}
