package org.loader.liteplayer.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.loader.liteplayer.R;
import org.loader.liteplayer.adapter.SearchResultAdapter;
import org.loader.liteplayer.application.AppUtil;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.network.NetWorkCallBack;
import org.loader.liteplayer.network.NetWorkUtil;
import org.loader.liteplayer.pojo.ContentList;

import java.util.List;

/**
 * @author husyin
 * @date 2018/1/15
 */

public class SearchResultActivity extends BaseActivity {
    private RecyclerView mRecycleView;
    private EditText mEditText;
    private SearchResultAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_seach_result_layout;
    }

    @Override
    protected void bindView() {
        mRecycleView = findViewById(R.id.act_search_result_recycle_view);
        mEditText = findViewById(R.id.et_search);
    }

    @Override
    protected void bindListener() {
        mEditText.setEnabled(true);
        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    getYiTingSearchResult(mEditText.getText().toString().trim());

                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive()){
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }

                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void loadData() {
        mAdapter = new SearchResultAdapter(null);
        mRecycleView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext()));
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void clearData() {

    }

    private void getYiTingSearchResult(String q){
        if (TextUtils.isEmpty(q)){
            return;
        }

        NetWorkUtil.getYiTingSearchDetailList(q, new NetWorkCallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject == null){
                    return;
                }

                JSONObject body = jsonObject.optJSONObject("showapi_res_body");
                if (body == null){
                    return;
                }

                JSONObject pageBean = body.optJSONObject("pagebean");
                if (pageBean == null){
                    return;
                }

                JSONArray jsonArray = pageBean.optJSONArray("contentlist");
                if (jsonArray == null){
                    return;
                }

                List<ContentList.ContentItem> hotSongPageBean = AppUtil.getGson().fromJson(jsonArray.toString(),
                        new TypeToken<List<ContentList.ContentItem>>(){}.getType());

                if (hotSongPageBean == null || hotSongPageBean.isEmpty()){
                    return;
                }

                mAdapter.setSearchResult(hotSongPageBean);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
}
