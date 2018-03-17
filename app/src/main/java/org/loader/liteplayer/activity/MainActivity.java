package org.loader.liteplayer.activity;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.loader.liteplayer.R;
import org.loader.liteplayer.fragment.HomePageFragment;
import org.loader.liteplayer.fragment.MinePageFragment;
import org.loader.liteplayer.fragment.NetSongFragment;
import org.loader.liteplayer.service.PlayService;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class MainActivity extends BaseActivity {

    private RadioButton mRbHomePage;
    private RadioButton mRbNetworkPage;
    private RadioButton mRbMinePage;

    private HomePageFragment mHomePageFrm;
    private NetSongFragment mNetworkPageFrm;
    private MinePageFragment mMinePageFrm;
    private Fragment mPreShowFrm;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private void initFragments() {
        mHomePageFrm = HomePageFragment.getInstance();
        mNetworkPageFrm = NetSongFragment.getInstance();
        mMinePageFrm = MinePageFragment.getInstance();
    }

    /**
     * 获取音乐播放服务
     */
    public PlayService getPlayService() {
        return mPlayService;
    }

    public void onClick(int id) {
        mRbHomePage.setTextColor(getResources().getColor(R.color.black));
        mRbNetworkPage.setTextColor(getResources().getColor(R.color.black));
        mRbMinePage.setTextColor(getResources().getColor(R.color.black));
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        switch (id){
            //网络歌曲页
            case R.id.ac_rb_network_page:
                mRbNetworkPage.setChecked(true);
                mRbNetworkPage.setTextColor(getResources().getColor(R.color.springgreen));

                fragment = fragmentManager.findFragmentByTag("NETWORK_PAGE");
                if (fragment == null) {
                    fragment = mNetworkPageFrm;
                }

                if (fragment.isAdded()){
                    fragmentTransaction.hide(mPreShowFrm)
                            .show(fragment)
                            .commitAllowingStateLoss();
                }else {
                    fragmentTransaction.hide(mPreShowFrm)
                            .add(R.id.am_content, fragment, "NETWORK_PAGE")
                            .commitAllowingStateLoss();
                }
                mPreShowFrm = fragment;
                break;
            //我的页
            case R.id.ac_rb_mine_page:
                mRbMinePage.setChecked(true);
                mRbMinePage.setTextColor(getResources().getColor(R.color.springgreen));
                fragment = fragmentManager.findFragmentByTag("MINE_PAGE");
                if (fragment == null) {
                    fragment = mMinePageFrm;
                }
                if (fragment.isAdded()){
                    fragmentTransaction.hide(mPreShowFrm)
                            .show(fragment)
                            .commitAllowingStateLoss();
                }else {
                    fragmentTransaction.hide(mPreShowFrm)
                            .add(R.id.am_content, fragment, "MINE_PAGE")
                            .commitAllowingStateLoss();
                }

                mPreShowFrm = fragment;
                break;
            //首页
            default:
            case R.id.ac_rb_home_page:
                mRbHomePage.setChecked(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    mRbHomePage.setTextColor(getResources().getColor(R.color.springgreen, null));
                }else {
                    mRbHomePage.setTextColor(getResources().getColor(R.color.springgreen));
                }
                fragment = fragmentManager.findFragmentByTag("HOME_PAGE");
                if (fragment == null) {
                    fragment = mHomePageFrm;
                }
                if (fragment.isAdded()){
                    fragmentTransaction.hide(mPreShowFrm)
                            .show(fragment)
                            .commitAllowingStateLoss();
                }else {
                    fragmentTransaction.hide(mPreShowFrm)
                            .add(R.id.am_content, fragment, "HOME_PAGE")
                            .commitAllowingStateLoss();
                }

                mPreShowFrm = fragment;
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView() {
        initFragments();
        setupViews();
    }

    private void setupViews() {
        mRbHomePage = findViewById(R.id.ac_rb_home_page);
        mRbNetworkPage = findViewById(R.id.ac_rb_network_page);
        mRbMinePage = findViewById(R.id.ac_rb_mine_page);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.am_content, mHomePageFrm, "HOME_PAGE")
                .commitAllowingStateLoss();
        mPreShowFrm = mHomePageFrm;
    }

    @Override
    protected void bindListener() {
        RadioGroup mRbGroup = findViewById(R.id.ac_radio_group);
        mRbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                onClick(id);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void clearData() {

    }
}