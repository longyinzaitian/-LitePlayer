package org.loader.liteplayer.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.loader.liteplayer.R;
import org.loader.liteplayer.adapter.HotSongListAdapter;
import org.loader.liteplayer.application.AppUtil;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.network.NetWorkCallBack;
import org.loader.liteplayer.network.NetWorkUtil;
import org.loader.liteplayer.pojo.HotSong;
import org.loader.liteplayer.pojo.HotSongPageBean;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2017/12/27.
 */
public class SongListFragment extends BaseFragment{
    private RecyclerView mRecyclerView;
    private HotSongListAdapter mAdapter;
    private int mHotId;

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
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mHotId = getArguments().getInt("id", 0);
        NetWorkUtil.getHotSongRank(String.valueOf(mHotId), new NetWorkCallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject == null){
                    return;
                }

                JSONObject resBody = jsonObject.optJSONObject("showapi_res_body");
                if (resBody == null){
                    return;
                }

                JSONObject pageBean = resBody.optJSONObject("pagebean");
                if (pageBean == null){
                    return;
                }

                HotSongPageBean hotSongPageBean = AppUtil.getGson().fromJson(pageBean.toString(),
                        new TypeToken<HotSongPageBean>(){}.getType());
                int code = hotSongPageBean.getCode();
                if (code == 0){
                    List<HotSong> hotSongs = hotSongPageBean.getSonglist();
                    if (hotSongs == null || hotSongs.isEmpty()){
                        return;
                    }else {
                        mAdapter.setSongsData(hotSongs);
                    }
                }else {

                }
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
}
