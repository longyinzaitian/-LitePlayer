package org.loader.liteplayer.activity;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.RadioButton;

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

    private Fragment mPreShowFrm;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    /**
     * 获取音乐播放服务
     */
    public PlayService getPlayService() {
        return mPlayService;
    }

    public void onClick(int id) {
        mRbHomePage.setTextColor(ActivityCompat.getColor(MainActivity.this, R.color.black));
        mRbNetworkPage.setTextColor(ActivityCompat.getColor(MainActivity.this, R.color.black));
        mRbMinePage.setTextColor(ActivityCompat.getColor(MainActivity.this, R.color.black));
        Fragment fragment;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (id) {
            //网络歌曲页
            case R.id.ac_rb_network_page:
                mRbNetworkPage.setTextColor(ActivityCompat.getColor(MainActivity.this, R.color.springgreen));

                fragment = fragmentManager.findFragmentByTag("NETWORK_PAGE");
                if (fragment == null) {
                    fragment = NetSongFragment.getInstance();
                    fragmentTransaction.hide(mPreShowFrm)
                            .add(R.id.am_content, fragment, "NETWORK_PAGE")
                            .commitAllowingStateLoss();
                    mPreShowFrm = fragment;
                    return;
                }

                if (mPreShowFrm == null) {
                    fragmentTransaction
                            .add(R.id.am_content, fragment, "NETWORK_PAGE")
                            .commitAllowingStateLoss();
                } else {
                    fragmentTransaction.hide(mPreShowFrm)
                            .show(fragment)
                            .commitAllowingStateLoss();
                }
                mPreShowFrm = fragment;
                break;

            //我的页
            case R.id.ac_rb_mine_page:
                mRbMinePage.setTextColor(ActivityCompat.getColor(MainActivity.this, R.color.springgreen));
                fragment = fragmentManager.findFragmentByTag("MINE_PAGE");
                if (fragment == null) {
                    fragment = MinePageFragment.getInstance();
                    fragmentTransaction.hide(mPreShowFrm)
                            .add(R.id.am_content, fragment, "MINE_PAGE")
                            .commitAllowingStateLoss();
                    mPreShowFrm = fragment;
                    return;
                }

                if (mPreShowFrm == null) {
                    fragmentTransaction
                            .add(R.id.am_content, fragment, "MINE_PAGE")
                            .commitAllowingStateLoss();
                } else {
                    fragmentTransaction.hide(mPreShowFrm)
                            .show(fragment)
                            .commitAllowingStateLoss();
                }
                mPreShowFrm = fragment;
                break;
            //首页
            case R.id.ac_rb_home_page:
                mRbHomePage.setTextColor(ActivityCompat.getColor(MainActivity.this, R.color.springgreen));

                fragment = fragmentManager.findFragmentByTag("HOME_PAGE");
                if (fragment == null) {
                    fragment = HomePageFragment.getInstance();
                    fragmentTransaction
                            .add(R.id.am_content, fragment, "HOME_PAGE")
                            .commitAllowingStateLoss();
                    mPreShowFrm = fragment;
                    return;
                }

                if (mPreShowFrm == null) {
                    fragmentTransaction
                            .add(R.id.am_content, fragment, "HOME_PAGE")
                            .commitAllowingStateLoss();
                } else {
                    fragmentTransaction.hide(mPreShowFrm)
                            .show(fragment)
                            .commitAllowingStateLoss();
                }
                mPreShowFrm = fragment;
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView() {
        setupViews();
    }

    private void setupViews() {
        mRbHomePage = findViewById(R.id.ac_rb_home_page);
        mRbNetworkPage = findViewById(R.id.ac_rb_network_page);
        mRbMinePage = findViewById(R.id.ac_rb_mine_page);

//        Fragment fragment = HomePageFragment.getInstance();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.am_content, fragment, "HOME_PAGE")
//                .commitAllowingStateLoss();
//        mPreShowFrm = fragment;
        mRbMinePage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onClick(R.id.ac_rb_mine_page);
                }
            }
        });
        mRbNetworkPage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onClick(R.id.ac_rb_network_page);
                }
            }
        });
        mRbHomePage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onClick(R.id.ac_rb_home_page);
                }
            }
        });
        onClick(R.id.ac_rb_home_page);
    }

    @Override
    protected void bindListener() {
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void clearData() {

    }
}