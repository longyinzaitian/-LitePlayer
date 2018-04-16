package org.loader.liteplayer.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import org.loader.liteplayer.R;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.application.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author longyinzaitian
 * @date 2018/1/7.
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.music_introduce)
    public TextView mTxIntroduce;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void bindListener() {

    }

    @OnClick({R.id.music_introduce})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_introduce:
                Intent intent = new Intent(BaseApplication.getContext(), WebViewActivity.class);
                intent.putExtra(Const.INTENT_KEY_URL, Const.SERVER_LOCAL_URL + "index.html");
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void clearData() {

    }
}
