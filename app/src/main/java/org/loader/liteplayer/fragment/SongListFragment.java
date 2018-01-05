package org.loader.liteplayer.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.loader.liteplayer.R;

/**
 * @author longyinzaitian
 * @date 2017/12/27.
 */
public class SongListFragment extends BaseFragment{
    private RecyclerView recyclerView;
    private int hotId;

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
        hotId = getArguments().getInt("id", 0);

    }
}
