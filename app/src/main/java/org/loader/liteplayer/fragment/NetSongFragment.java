package org.loader.liteplayer.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import org.loader.liteplayer.R;
import org.loader.liteplayer.activity.SearchResultActivity;
import org.loader.liteplayer.adapter.NetSongPagerAdapter;
import org.loader.liteplayer.application.App;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.engine.SongsRecommendation;
import org.loader.liteplayer.pojo.HotSong;
import org.loader.liteplayer.pojo.RankList;

import java.util.ArrayList;
import java.util.List;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class NetSongFragment extends BaseFragment
                    implements OnClickListener {

    private static NetSongFragment instance;

    private ViewPager mViewPager;
    private View mPopView;
    private TabLayout mTabLayout;
    private FloatingActionButton mFloatingActBtn;

    private PopupWindow mPopupWindow;

    private ArrayList<HotSong> mResultData = new ArrayList<>();
    private ArrayList<RankList.Item> items;

    private int mPage = 0;
    private int mLastItem;
    private boolean hasMoreData = true;
    /**
     * 该类是android系统中的下载工具类，非常好用
     */
    private DownloadManager mDownloadManager;

    private boolean isFirstShown = true;

    public static NetSongFragment getInstance(){
        if (instance == null){
            instance = new NetSongFragment();
        }

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
        mFloatingActBtn = view.findViewById(R.id.net_song_page_float_btn);

//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        if (getActivity() != null){
//            ((MainActivity)getActivity()).setSupportActionBar(toolbar);
//        }

        mDownloadManager = (DownloadManager) BaseApplication.getContext()
                .getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Override
    protected void bindListener() {
        mFloatingActBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseApplication.getContext(), SearchResultActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {
        initItem();
        initAdapter();
    }

    private void initItem(){
        if (App.rankLists == null){
            App.rankLists = new ArrayList<>();
            App.rankLists.add(new RankList.Item("tv", "电视动画"));
            App.rankLists.add(new RankList.Item("ova", "OVA动画"));
            App.rankLists.add(new RankList.Item("oad", "OAD动画"));
            App.rankLists.add(new RankList.Item("movie", "剧场版动画"));
            App.rankLists.add(new RankList.Item("comic", "漫画"));
            App.rankLists.add(new RankList.Item("music", "音乐专辑"));
//            App.rankLists.add(new RankList.Item("radio", "音乐电台"));
        }

        items = new ArrayList<>();
        items.addAll(App.rankLists);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initAdapter(){
        if (items == null){
            return;
        }

        List<Fragment> fragments = new ArrayList<>();
        for (RankList.Item item : items){
            SongListFragment songListFragment = SongListFragment.getInstance(item.getId());
            fragments.add(songListFragment);
        }

        NetSongPagerAdapter adapter = new NetSongPagerAdapter(getChildFragmentManager(), fragments, items);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(items.size());
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 该方法实现的功能是： 当该Fragment不可见时，isVisibleToUser=false
     * 当该Fragment可见时，isVisibleToUser=true
     * 该方法由系统调用，重写该方法实现用户可见当前Fragment时再进行数据的加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 当Fragment可见且是第一次加载时
        if (isVisibleToUser && isFirstShown) {

            isFirstShown = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SongsRecommendation.getInstance().cleanWork();
    }

    /**
     * 底部对话框
     * @param position 位置
     */
    private void showDownloadDialog(final int position) {

        if (mPopupWindow == null) {
            mPopView = View.inflate(BaseApplication.getContext(), R.layout.download_pop_layout,
                    null);

            mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(
                    Color.TRANSPARENT));
            mPopupWindow.setAnimationStyle(R.style.popwin_anim);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
        }

        //下载按钮点击时间
        mPopView.findViewById(R.id.tv_pop_download).setOnClickListener(
            new OnClickListener() {
                @Override
                public void onClick(View v) {

                    dismissDialog();
                }
            });

        mPopView.findViewById(R.id.tv_pop_cancel).setOnClickListener(
            new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissDialog();
                }
            });

        //设置对话框展示的位置
//        mPopupWindow.showAtLocation(BaseApplication.getContext().getWindow().getDecorView(),
//                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void dismissDialog() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        default:
            break;
        }
    }
}