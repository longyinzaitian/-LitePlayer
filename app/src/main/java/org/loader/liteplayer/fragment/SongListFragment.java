package org.loader.liteplayer.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.loader.liteplayer.R;
import org.loader.liteplayer.adapter.HotSongListAdapter;
import org.loader.liteplayer.application.AppUtil;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.network.NetWorkCallBack;
import org.loader.liteplayer.network.NetWorkUtil;
import org.loader.liteplayer.pojo.MusicList;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2017/12/27.
 */
public class SongListFragment extends BaseFragment{
    private RecyclerView mRecyclerView;
    private HotSongListAdapter mAdapter;
    private String mHotId;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (mAdapter == null){
                return;
            }

            if (mAdapter.getItemCount() <= 0){
                getYiTingRankDetaiList();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_song_list;
    }

    @Override
    protected void bindView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_song_list);
    }

    @Override
    protected void bindListener() {
        mAdapter = new HotSongListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(BaseApplication.getContext(), RecyclerView.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new HotSongListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int pos, String url) {

            }
        });
    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        if (bundle == null){
            return;
        }
        mHotId = getArguments().getString("id", "");
    }

    private void getYiTingRankDetaiList() {
        NetWorkUtil.getYiTingRankDetailList(String.valueOf(mHotId), new NetWorkCallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject == null){
                    return;
                }

                JSONObject resBody = jsonObject.optJSONObject("showapi_res_body");
                if (resBody == null){
                    return;
                }

                JSONArray pageBean = resBody.optJSONArray("musicList");
                if (pageBean == null){
                    return;
                }

                List<MusicList.MusicItem> hotSongPageBean = AppUtil.getGson().fromJson(pageBean.toString(),
                        new TypeToken<List<MusicList.MusicItem>>(){}.getType());

                if (hotSongPageBean == null || hotSongPageBean.isEmpty()){
                    return;
                }

                mAdapter.setSongsData(hotSongPageBean);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
}
