package org.loader.liteplayer.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.loader.liteplayer.pojo.Item;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2018/1/5
 */

public class NetSongPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<Item> items;

    public NetSongPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<Item> items) {
        super(fm);
        this.fragmentList = fragmentList;
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getTitle();
    }
}
