package org.loader.liteplayer.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.loader.liteplayer.R;
import org.loader.liteplayer.fragment.BaseFragment;
import org.loader.liteplayer.fragment.HomePageFragment;
import org.loader.liteplayer.fragment.MinePageFragment;
import org.loader.liteplayer.fragment.NetSearchFragment;
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

	private RadioGroup mRbGroup;

	private HomePageFragment mHomePageFrm;
	private NetSearchFragment mNetworkPageFrm;
	private MinePageFragment mMinePageFrm;
	private BaseFragment mPreShowFrm;

	private	FragmentManager fragmentManager = getSupportFragmentManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		initFragments();
		setupViews();
	}

	private void initFragments() {
		mHomePageFrm = HomePageFragment.getInstance();
		mNetworkPageFrm = NetSearchFragment.getInstance();
		mMinePageFrm = MinePageFragment.getInstance();
	}

	private void setupViews() {
		mRbHomePage = findViewById(R.id.ac_rb_home_page);
		mRbNetworkPage = findViewById(R.id.ac_rb_network_page);
		mRbMinePage = findViewById(R.id.ac_rb_mine_page);
		mRbGroup = findViewById(R.id.ac_radio_group);

		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.am_content, mHomePageFrm, "HOME_PAGE")
				.commitAllowingStateLoss();
		mPreShowFrm = mHomePageFrm;

		mRbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				int id = radioGroup.getCheckedRadioButtonId();
				onClick(id);
			}
		});
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

		switch (id){
			case R.id.ac_rb_home_page:
				mRbHomePage.setChecked(true);
				mRbHomePage.setTextColor(getResources().getColor(R.color.springgreen));

				if (mHomePageFrm.isAdded()){
					fragmentTransaction.hide(mPreShowFrm)
							.show(mHomePageFrm)
							.commitAllowingStateLoss();
				}else {
					fragmentTransaction.hide(mPreShowFrm)
							.add(R.id.am_content, mHomePageFrm, "HOME_PAGE")
							.commitAllowingStateLoss();
				}

				mPreShowFrm = mHomePageFrm;
				break;
			case R.id.ac_rb_network_page:
				mRbNetworkPage.setChecked(true);
				mRbNetworkPage.setTextColor(getResources().getColor(R.color.springgreen));

				if (mNetworkPageFrm.isAdded()){
					fragmentTransaction.hide(mPreShowFrm)
							.show(mNetworkPageFrm)
							.commitAllowingStateLoss();
				}else {
					fragmentTransaction.hide(mPreShowFrm)
							.add(R.id.am_content, mNetworkPageFrm, "NETWORK_PAGE")
							.commitAllowingStateLoss();
				}

				mPreShowFrm = mNetworkPageFrm;
				break;
			case R.id.ac_rb_mine_page:
				mRbMinePage.setChecked(true);
				mRbMinePage.setTextColor(getResources().getColor(R.color.springgreen));

				if (mMinePageFrm.isAdded()){
					fragmentTransaction.hide(mPreShowFrm)
							.show(mMinePageFrm)
							.commitAllowingStateLoss();
				}else {
					fragmentTransaction.hide(mPreShowFrm)
							.add(R.id.am_content, mMinePageFrm, "MINE_PAGE")
							.commitAllowingStateLoss();
				}

				mPreShowFrm = mMinePageFrm;
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}