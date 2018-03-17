package org.loader.liteplayer.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.loader.liteplayer.R;
import org.loader.liteplayer.activity.AboutActivity;
import org.loader.liteplayer.activity.SettingActivity;
import org.loader.liteplayer.application.BaseApplication;

/**
 * @author longyinzaitian
 * @date 2017/12/24.
 */
public class MinePageFragment extends BaseFragment {
    private ImageView mImgAvatar;
    private TextView mTxName;
    private TextView mTxSex;
    private LinearLayout mSettingLv;
    private LinearLayout mAboutLv;

    public static MinePageFragment getInstance(){
        MinePageFragment instance = new MinePageFragment();
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_page;
    }

    @Override
    protected void bindView(View view) {
        mImgAvatar = view.findViewById(R.id.frm_mine_avatar);
        mTxName = view.findViewById(R.id.frm_mine_name);
        mTxSex = view.findViewById(R.id.frm_mine_sexy);
        mSettingLv = view.findViewById(R.id.frm_mine_setting);
        mAboutLv = view.findViewById(R.id.frm_mine_about);
    }

    @Override
    protected void bindListener() {
        mSettingLv.setOnClickListener(onClickListener);
        mAboutLv.setOnClickListener(onClickListener);
    }

    @Override
    protected void loadData() {

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.frm_mine_setting:
                    startActivity(new Intent(BaseApplication.getContext(), SettingActivity.class));
                    break;
                case R.id.frm_mine_about:
                    startActivity(new Intent(BaseApplication.getContext(), AboutActivity.class));
                    break;
                default:
                    break;
            }
        }
    };
}
