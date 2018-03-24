package org.loader.liteplayer.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.loader.liteplayer.R;
import org.loader.liteplayer.adapter.HotSongListAdapter;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.network.NetWorkCallBack;
import org.loader.liteplayer.network.NetWorkUtil;
import org.loader.liteplayer.pojo.Wiki;
import org.loader.liteplayer.utils.LogUtil;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2017/12/27.
 */
public class SongListFragment extends BaseFragment{
    private RecyclerView mRecyclerView;
    private HotSongListAdapter mAdapter;

    private int page = 1;
    private String type;

    public static SongListFragment getInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        SongListFragment fragment = new SongListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        LogUtil.l(TAG, "getLayoutId");
        return R.layout.fragment_song_list;
    }

    @Override
    protected void bindView(View view) {
        LogUtil.l(TAG, "bindView");
        mRecyclerView = view.findViewById(R.id.rv_song_list);
    }

    @Override
    protected void bindListener() {
        LogUtil.l(TAG, "bindListener");
    }

    private void setAdapter() {
        mAdapter = new HotSongListAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(BaseApplication.getContext(), RecyclerView.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        LogUtil.l(TAG, "loadData");
        Bundle bundle = getArguments();
        if (bundle == null){
            return;
        }
        type = getArguments().getString("type", "");
        setAdapter();
        getYiTingRankDetaiList();
    }

    private void getYiTingRankDetaiList() {
        NetWorkUtil.getWikisItem(type, page, new NetWorkCallBack() {
            @Override
            public void onResponse(final JSONObject jsonObject) {
                LogUtil.l(TAG, "rsult:" + jsonObject);
                try {
                    List<Wiki> wikis = new Gson().fromJson(
                            jsonObject.optJSONArray("wikis").toString(),
                            new TypeToken<List<Wiki>>(){}.getType());
                    LogUtil.l(TAG, "wiki:" + wikis);
                    if (mAdapter != null) {
                        mAdapter.setSongsData(wikis);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
}
