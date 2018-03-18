package org.loader.liteplayer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.loader.liteplayer.utils.LogUtil;

/**
 * @author longyinzaitian
 * @date 2017年12月24日
 */
public abstract class BaseFragment extends Fragment {
    protected final String TAG = this.getClass().getSimpleName();
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.l(TAG, "onHiddenChanged hidden:" + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.l(TAG, "setUserVisibleHint,  isVisibleToUser:" + isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.l(TAG, "onCreateView");
        return  inflater.inflate(getLayoutId(),
                null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindView(getView());
        bindListener();
        loadData();
    }

    /**
     * 布局
     * @return int
     */
    protected abstract int getLayoutId();

    /**
     * 设置组件View
     * @param view view
     */
    protected abstract void bindView(View view);

    /**
     * 设置监听器
     */
    protected abstract void bindListener();

    /**
     * 拉取数据
     */
    protected abstract void loadData();

}
