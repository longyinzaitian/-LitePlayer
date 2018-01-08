package org.loader.liteplayer.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import org.loader.liteplayer.R;

/**
 * @author longyinzaitian
 * @date 2018/1/2
 */

public class SettingActivity extends BaseActivity {
    private TextView mTxVersion;
    private TextView mTxLogout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void bindView() {
        mTxVersion = findViewById(R.id.act_setting_version);
        mTxLogout = findViewById(R.id.act_setting_logout);
    }

    @Override
    protected void bindListener() {
        mTxVersion.setOnClickListener(onClickListener);
        mTxLogout.setOnClickListener(onClickListener);
    }

    @Override
    protected void loadData() {
        PackageManager packageManager = getPackageManager();
        String versionCode = "V1.0.0";
        try{
            PackageInfo packageInfo = packageManager.getPackageInfo("org.loader.liteplayer", 0);
            versionCode = String.valueOf(packageInfo.versionName);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        mTxVersion.setText(versionCode);
    }

    @Override
    protected void clearData() {

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.act_setting_version:
                    break;
                case R.id.act_setting_logout:
                    break;
                default:break;
            }
        }
    };
}
