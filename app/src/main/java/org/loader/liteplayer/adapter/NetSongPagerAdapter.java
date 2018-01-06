package org.loader.liteplayer.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import org.loader.liteplayer.pojo.Item;

import java.util.List;

/**
 * @author husyin
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
        return fragmentList==null?0:fragmentList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getTitle();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
