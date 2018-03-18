package org.loader.liteplayer.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.loader.liteplayer.R;
import org.loader.liteplayer.adapter.NetSongPagerAdapter;
import org.loader.liteplayer.application.App;
import org.loader.liteplayer.pojo.RankList;

import java.util.ArrayList;
import java.util.List;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class NetSongFragment extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private ArrayList<RankList.Item> items;
    static NetSongFragment instance = new NetSongFragment();

    public static NetSongFragment getInstance() {
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.search_music_layout;
    }

    @Override
    protected void bindView(View view) {
        mViewPager = view.findViewById(R.id.net_song_view_pager);
        mTabLayout = view.findViewById(R.id.net_song_tab_layout);
    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void loadData() {
        initItem();
        initAdapter();
    }

    private void initItem() {
        if (App.rankLists == null) {
            App.rankLists = new ArrayList<>();
            App.rankLists.add(new RankList.Item("tv", "电视动画"));
            App.rankLists.add(new RankList.Item("ova", "OVA动画"));
            App.rankLists.add(new RankList.Item("oad", "OAD动画"));
            App.rankLists.add(new RankList.Item("movie", "剧场版动画"));
            App.rankLists.add(new RankList.Item("comic", "漫画"));
            App.rankLists.add(new RankList.Item("music", "音乐专辑"));
            /*
            App.rankLists.add(new RankList.Item("radio", "音乐电台"));
             */
        }

        items = new ArrayList<>();
        items.addAll(App.rankLists);
    }

    private void initAdapter() {
        if (items == null) {
            return;
        }

        List<Fragment> fragments = new ArrayList<>();
        for (RankList.Item item : items) {
            SongListFragment songListFragment = SongListFragment.getInstance(item.getId());
            fragments.add(songListFragment);
        }

        NetSongPagerAdapter adapter = new NetSongPagerAdapter(getChildFragmentManager(), fragments, items);
        mViewPager.setOffscreenPageLimit(items.size());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}