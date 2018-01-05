package org.loader.liteplayer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.loader.liteplayer.R;

/**
 * @author longyinzaitian
 * @date 2017/12/27.
 */
public class SongListFragment extends BaseFragment{
    private static SongListFragment songListFragment;

    private RecyclerView recyclerView;

    public SongListFragment getInstance(){
        if (songListFragment == null){
            songListFragment = new SongListFragment();
        }
        return songListFragment;
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
        recyclerView = view.findViewById(R.id.rv_song_list);
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void loadData() {

    }
}
