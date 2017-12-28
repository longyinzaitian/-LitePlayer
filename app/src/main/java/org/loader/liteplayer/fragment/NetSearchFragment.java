package org.loader.liteplayer.fragment;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.loader.liteplayer.R;
import org.loader.liteplayer.activity.MainActivity;
import org.loader.liteplayer.adapter.SearchResultAdapter;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.engine.GetDownloadInfo;
import org.loader.liteplayer.engine.GetDownloadInfo.OnDownloadGetListener;
import org.loader.liteplayer.engine.SearchMusic;
import org.loader.liteplayer.engine.SongsRecommendation;
import org.loader.liteplayer.pojo.SearchResult;
import org.loader.liteplayer.utils.Constants;
import org.loader.liteplayer.utils.MobileUtils;
import org.loader.liteplayer.utils.MusicUtils;
import org.loader.liteplayer.network.NetWorkUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class NetSearchFragment extends BaseFragment
                    implements OnClickListener {

    private static NetSearchFragment instance;

    private MainActivity mActivity;

    private LinearLayout mSearchShowLinearLayout;
    private LinearLayout mSearchLinearLayout;
    private ImageButton mSearchButton;
    private EditText mSearchEditText;
    private RecyclerView mSearchResultListView;
    private ProgressBar mSearchProgressBar;
    private View mPopView;

    private PopupWindow mPopupWindow;

    private SearchResultAdapter mSearchResultAdapter;
    private ArrayList<SearchResult> mResultData = new ArrayList<SearchResult>();

    private int mPage = 0;
    private int mLastItem;
    private boolean hasMoreData = true;
    /**
     * 该类是android系统中的下载工具类，非常好用
     */
    private DownloadManager mDownloadManager;

    private boolean isFirstShown = true;

    public static NetSearchFragment getInstance(){
        if (instance == null){
            instance = new NetSearchFragment();
        }

        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.search_music_layout, null);
        setupViews(layout);

        mDownloadManager = (DownloadManager) mActivity
                .getSystemService(Context.DOWNLOAD_SERVICE);

        NetWorkUtil.getHotSongRank("5");
        NetWorkUtil.getSongLrcById("");
        NetWorkUtil.SearchSongByKeyword("邓紫棋", "2");
        return layout;
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
            mSearchProgressBar.setVisibility(View.VISIBLE);
            mSearchResultListView.setVisibility(View.GONE);
            SongsRecommendation
                .getInstance()
                .setListener(
                    new SongsRecommendation.OnRecommendationListener() {
                        @Override
                        public void onRecommend(
                            ArrayList<SearchResult> results) {

                            if (results == null || results.isEmpty()){
                                return;
                            }

                            mSearchProgressBar.setVisibility(View.GONE);
                            mSearchResultListView
                                    .setVisibility(View.VISIBLE);
                            mResultData.clear();
                            mResultData.addAll(results);
                            mSearchResultAdapter.notifyDataSetChanged();
                        }
                    }).get();
            isFirstShown = false;
        }
    }

    private void setupViews(View layout) {
        mSearchShowLinearLayout = layout.findViewById(R.id.ll_search_btn_container);
        mSearchLinearLayout = layout.findViewById(R.id.ll_search_container);
        mSearchButton = layout.findViewById(R.id.ib_search_btn);
        mSearchEditText = layout.findViewById(R.id.et_search_content);
        mSearchResultListView = layout.findViewById(R.id.lv_search_result);
        mSearchProgressBar = layout.findViewById(R.id.pb_search_wait);

        mSearchShowLinearLayout.setOnClickListener(this);
        mSearchButton.setOnClickListener(this);

        mSearchResultAdapter = new SearchResultAdapter(mResultData);
        mSearchResultListView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext()));
        mSearchResultListView.setAdapter(mSearchResultAdapter);
        mSearchResultListView.setOnScrollListener(mListViewScrollListener);
        mSearchResultAdapter.setOnItemClickListener(mResultItemClickListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SongsRecommendation.getInstance().cleanWork();
    }

    /**
     * 列表中每一列的点击时间监听器
     */
    private OnItemClickListener mResultItemClickListener
                            = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            if (position >= mResultData.size() || position < 0) {
                return;
            }

            showDownloadDialog(position);
        }
    };
    /**
     * 底部对话框
     * @param position 位置
     */
    private void showDownloadDialog(final int position) {

        if (mPopupWindow == null) {
            mPopView = View.inflate(mActivity, R.layout.download_pop_layout,
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
                    GetDownloadInfo
                            .getInstance()
                            .setListener(mDownloadUrlListener)
                            .parse(position,
                                    mResultData.get(position).getUrl());
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
        mPopupWindow.showAtLocation(mActivity.getWindow().getDecorView(),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void dismissDialog() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private OnDownloadGetListener mDownloadUrlListener =
            new OnDownloadGetListener() {
        @Override
        public void onMusic(int position, String url) {
            if (position == -1 || url == null) {
                Toast.makeText(mActivity, "歌曲链接失效",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String musicName = mResultData.get(position).getMusicName();
            mActivity.getDownloadService().download(position,
                    Constants.MUSIC_URL + url, musicName + ".mp3");
        }

        @Override
        public void onLrc(int position, String url) {
            if (url == null){
                return;
            }

            String musicName = mResultData.get(position).getMusicName();
            DownloadManager.Request request = new DownloadManager.Request(
                    Uri.parse(Constants.MUSIC_URL + url));
            request.setVisibleInDownloadsUi(false);
            request.setNotificationVisibility(Request.VISIBILITY_HIDDEN);
            /* request.setShowRunningNotification(false);*/
            request.setDestinationUri(Uri.fromFile(new File(MusicUtils
                    .getLrcDir() + musicName + ".lrc")));
            mDownloadManager.enqueue(request);
        }
    };

    private RecyclerView.OnScrollListener mListViewScrollListener =
        new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mLastItem == mSearchResultAdapter.getItemCount() && hasMoreData
                        && newState == OnScrollListener.SCROLL_STATE_IDLE) {
                    String searchText = mSearchEditText.getText().toString().trim();
                    if (TextUtils.isEmpty(searchText)) {
                        return;
                    }
                    startSearch(searchText);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // 计算可见列表的最后一条的列表是不是最后一个
//                    mLastItem = firstVisibleItem + visibleItemCount;
            }
    };

    private void search() {
        MobileUtils.hideInputMethod(mSearchEditText);
        String content = mSearchEditText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(mActivity, "请输入关键词", Toast.LENGTH_SHORT).show();
            return;
        }

        mPage = 0;
        mSearchProgressBar.setVisibility(View.VISIBLE);
        mSearchResultListView.setVisibility(View.GONE);
        startSearch(content);
    }

    private void startSearch(String content) {
        SearchMusic.getInstance()
            .setListener(new SearchMusic.OnSearchResultListener() {
                @Override
                public void onSearchResult(ArrayList<SearchResult> results) {
                    if (mPage == 1) {
                        hasMoreData = true;
                        mSearchProgressBar.setVisibility(View.GONE);
                        mSearchResultListView.setVisibility(View.VISIBLE);
                    }

                    if (results == null || results.isEmpty()) {
                        hasMoreData = false;
                        return;
                    }

                    if (mPage == 1) {
                        mResultData.clear();
                    }

                    mResultData.addAll(results);
                    mSearchResultAdapter.notifyDataSetChanged();
                }
            }).search(content, ++mPage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ll_search_btn_container:

            mSearchShowLinearLayout.setVisibility(View.GONE);
            mSearchLinearLayout.setVisibility(View.VISIBLE);
            break;

        case R.id.ib_search_btn:
            mSearchShowLinearLayout.setVisibility(View.VISIBLE);
            mSearchLinearLayout.setVisibility(View.GONE);
            search();
            break;
        default:
            break;
        }
    }
}