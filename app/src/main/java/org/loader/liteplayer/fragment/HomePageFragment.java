package org.loader.liteplayer.fragment;

import java.io.File;

import org.loader.liteplayer.R;
import org.loader.liteplayer.activity.MainActivity;
import org.loader.liteplayer.activity.PlayActivity;
import org.loader.liteplayer.adapter.MusicListAdapter;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.pojo.Music;
import org.loader.liteplayer.utils.ImageTools;
import org.loader.liteplayer.utils.L;
import org.loader.liteplayer.utils.MusicIconLoader;
import org.loader.liteplayer.utils.MusicUtils;
import org.loader.liteplayer.utils.ThreadCenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 2015年8月15日 16:34:37
 * 博文地址：http://blog.csdn.net/u010156024
 * @author longyinzaitian
 */
public class HomePageFragment extends BaseFragment implements OnClickListener {

    private static HomePageFragment instance;

    private RecyclerView mMusicListView;
    private ImageView mMusicIcon;
    private TextView mMusicTitle;
    private TextView mMusicArtist;

    private ImageView mPreImageView;
    private ImageView mPlayImageView;
    private ImageView mNextImageView;

    private SeekBar mMusicProgress;

    private MusicListAdapter mMusicListAdapter = new MusicListAdapter();

    private MainActivity mActivity;

    private boolean isPause;

    public static HomePageFragment getInstance(){
        if (instance == null){
            instance = new HomePageFragment();
        }

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home_page_layout, null);
        setupViews(layout);

        return layout;
    }

    /**
     * view创建完毕 回调通知activity绑定歌曲播放服务
     */
    @Override
    public void onStart() {
        super.onStart();
        L.l(TAG, "onViewCreated");
        mActivity.allowBindService();
    }

    @Override
    public void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    public void onPause() {
        isPause = true;
        super.onPause();
    }

    /**
     * stop时， 回调通知activity解除绑定歌曲播放服务
     */
    @Override
    public void onStop() {
        super.onStop();
        L.l(TAG, "onDestroyView");
        mActivity.allowUnbindService();
    }

    private void setupViews(View layout) {
        mMusicListView = layout.findViewById(R.id.lv_music_list);
        mMusicIcon = layout.findViewById(R.id.iv_play_icon);
        mMusicTitle = layout.findViewById(R.id.tv_play_title);
        mMusicArtist = layout.findViewById(R.id.tv_play_artist);

        mPreImageView = layout.findViewById(R.id.iv_pre);
        mPlayImageView = layout.findViewById(R.id.iv_play);
        mNextImageView = layout.findViewById(R.id.iv_next);

        mMusicProgress = layout.findViewById(R.id.play_progress);

        mMusicListView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext()));
        mMusicListView.setAdapter(mMusicListAdapter);

        mMusicListAdapter.setOnItemClickListener(mMusicItemClickListener);
        mMusicListAdapter.setOnItemLongClickListener(mItemLongClickListener);

        mMusicIcon.setOnClickListener(this);
        mPreImageView.setOnClickListener(this);
        mPlayImageView.setOnClickListener(this);
        mNextImageView.setOnClickListener(this);
    }

    private OnItemLongClickListener mItemLongClickListener = 
            new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                int position, long id) {
            final int pos = position;

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("删除该条目");
            builder.setMessage("确认要删除该条目吗?");
            builder.setPositiveButton("删除",
                new DialogInterface.OnClickListener() {
                @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Music music = MusicUtils.sMusicList.remove(pos);
                        mMusicListAdapter.notifyDataSetChanged();
                        if (new File(music.getUri()).delete()) {
                            scanSDCard();
                        }
                    }
                });
            builder.setNegativeButton("取消", null);
            builder.create().show();

            return true;
        }
    };

    private OnItemClickListener mMusicItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            play(position);
        }
    };

    /**
     * 发送广播，通知系统扫描指定的文件
     * 请参考我的博文：
     * http://blog.csdn.net/u010156024/article/details/47681851
     * 
     */
    private void scanSDCard() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 判断SDK版本是不是4.4或者高于4.4
            String[] paths = new String[]{
                    Environment.getExternalStorageDirectory().toString()};
            MediaScannerConnection.scanFile(mActivity, paths, null, null);
        } else {
            Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
            intent.setClassName("com.android.providers.media",
                    "com.android.providers.media.MediaScannerReceiver");
            intent.setData(Uri.parse("file://"+ MusicUtils.getMusicDir()));
            mActivity.sendBroadcast(intent);
        }
    }

    /**
     * 播放时高亮当前播放条目
     * 实现播放的歌曲条目可见，且实现指示标记可见
     * @param position int
     */
    private void onItemPlay(int position) {
//        // 将ListView列表滑动到播放的歌曲的位置，是播放的歌曲可见
//        mMusicListView.smoothScrollToPosition(position);
//        // 获取上次播放的歌曲的position
//        int prePlayingPosition = mMusicListAdapter.getPlayingPosition();
//
//        // 如果上次播放的位置在可视区域内
//        // 则手动设置invisible
//        if (prePlayingPosition >= mMusicListView.getFirstVisiblePosition()
//                && prePlayingPosition <= mMusicListView
//                        .getLastVisiblePosition()) {
//            int preItem = prePlayingPosition
//                    - mMusicListView.getFirstVisiblePosition();
//            ((ViewGroup) mMusicListView.getChildAt(preItem)).getChildAt(0)
//                    .setVisibility(View.INVISIBLE);
//        }
//
//        // 设置新的播放位置
//        mMusicListAdapter.setPlayingPosition(position);
//
//        // 如果新的播放位置不在可视区域
//        // 则直接返回
//        if (mMusicListView.getLastVisiblePosition() < position
//                || mMusicListView.getFirstVisiblePosition() > position){
//            return;
//        }
//
//        // 如果在可视区域
//        // 手动设置改item visible
//        int currentItem = position - mMusicListView.getFirstVisiblePosition();
//        ((ViewGroup) mMusicListView.getChildAt(currentItem)).getChildAt(0)
//                .setVisibility(View.VISIBLE);
    }

    /**
     * 播放音乐item
     * 
     * @param position int
     */
    private void play(int position) {
        int pos = mActivity.getPlayService().play(position);
        onPlay(pos);
    }

    /**
     * 播放时，更新控制面板
     * 
     * @param position int
     */
    public void onPlay(int position) {
        if (MusicUtils.sMusicList.isEmpty() || position < 0){
            Toast.makeText(getActivity(), "当前手机没有MP3文件", Toast.LENGTH_LONG).show();
            return;
        }

        //设置进度条的总长度
        mMusicProgress.setMax(mActivity.getPlayService().getDuration());
        onItemPlay(position);

        Music music = MusicUtils.sMusicList.get(position);
        Bitmap icon = MusicIconLoader.getInstance().load(music.getImage());
        mMusicIcon.setImageBitmap(icon == null ? ImageTools
                .scaleBitmap(R.drawable.ic_launcher) : ImageTools
                .scaleBitmap(icon));
        mMusicTitle.setText(music.getTitle());
        mMusicArtist.setText(music.getArtist());

        if (mActivity.getPlayService().isPlaying()) {
            mPlayImageView.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            mPlayImageView.setImageResource(android.R.drawable.ic_media_play);
        }

        //新启动一个线程更新通知栏，防止更新时间过长，导致界面卡顿！
        ThreadCenter.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                mActivity.getPlayService().setRemoteViews();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_play_icon:
            startActivity(new Intent(mActivity, PlayActivity.class));
            break;

        case R.id.iv_play:
            if (mActivity.getPlayService().isPlaying()) {
                // 暂停
                mActivity.getPlayService().pause();
                mPlayImageView.setImageResource(android.R.drawable.ic_media_play);
            } else {
                // 播放
                onPlay(mActivity.getPlayService().resume());
            }
            break;

        case R.id.iv_next:
            // 下一曲
            mActivity.getPlayService().next();
            break;

        case R.id.iv_pre:
            // 上一曲
            mActivity.getPlayService().pre();
            break;
        default:
            break;
        }
    }

    /**
     * 设置进度条的进度(SeekBar)
     * @param progress int
     */
    public void setProgress(int progress) {
        if (isPause){
            return;
        }

        mMusicProgress.setProgress(progress);
    }

    /**
     * 主界面MainActivity.java中调用更新歌曲列表
     */
    public void onMusicListChanged() {
        mMusicListAdapter.notifyDataSetChanged();
    }
}
