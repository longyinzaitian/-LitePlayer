package org.loader.liteplayer.adapter;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.loader.liteplayer.pojo.RankList;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2018/1/5
 */

public class NetSongPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<RankList.Item> items;

    public NetSongPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragments(List<Fragment> fragmentList, List<RankList.Item> items) {
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

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getName();
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        //do nothing here! no call to super.restoreState(arg0, arg1);
    }
}
