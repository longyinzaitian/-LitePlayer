package org.loader.liteplayer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.loader.liteplayer.R;
import org.loader.liteplayer.application.BaseApplication;

/**
 * @author longyinzaitian
 * @date 2017/12/24.
 */
public class MinePageFragment extends BaseFragment {
    private static MinePageFragment instance;

    private ImageView mImgAvatar;
    private TextView mTxName;
    private TextView mTxSex;
    private LinearLayout mSettingLv;
    private LinearLayout mAboutLv;

    public static MinePageFragment getInstance(){
        if (instance == null){
            instance = new MinePageFragment();
        }

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

    }

    @Override
    protected void loadData() {

    }
}
