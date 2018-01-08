package org.loader.liteplayer.fragment;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.loader.liteplayer.R;
import org.loader.liteplayer.activity.MainActivity;
import org.loader.liteplayer.activity.PlayActivity;
import org.loader.liteplayer.adapter.HomePageFrmAdapter;
import org.loader.liteplayer.application.BaseApplication;
import org.loader.liteplayer.event.EventCenter;
import org.loader.liteplayer.event.IEvent;
import org.loader.liteplayer.event.IEventCallback;
import org.loader.liteplayer.event.PublishProgressEvent;
import org.loader.liteplayer.event.SongPlayChangeEvent;
import org.loader.liteplayer.pojo.Music;
import org.loader.liteplayer.utils.ImageTools;
import org.loader.liteplayer.utils.LogUtil;
import org.loader.liteplayer.utils.MusicIconLoader;
import org.loader.liteplayer.utils.MusicUtils;
import org.loader.liteplayer.utils.ThreadCenter;

import java.io.File;
import java.io.LineNumberInputStream;

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

    private HomePageFrmAdapter mHomePageFrmAdapter = new HomePageFrmAdapter();

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

    /**
     * view创建完毕 回调通知activity绑定歌曲播放服务
     */
    @Override
    public void onStart() {
        super.onStart();
        LogUtil.l(TAG, "onViewCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        isPause = false;
        if (mActivity == null){
            return;
        }

        if (mActivity.getPlayService() == null){
            return;
        }

        play(mActivity.getPlayService().getPlayingPosition());
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
        LogUtil.l(TAG, "onDestroyView");
        mActivity.allowUnbindService();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_page_layout;
    }

    @Override
    protected void bindView(View layout) {
        mMusicListView = layout.findViewById(R.id.lv_music_list);
        mMusicIcon = layout.findViewById(R.id.iv_play_icon);
        mMusicTitle = layout.findViewById(R.id.tv_play_title);
        mMusicArtist = layout.findViewById(R.id.tv_play_artist);

        mPreImageView = layout.findViewById(R.id.iv_pre);
        mPlayImageView = layout.findViewById(R.id.iv_play);
        mNextImageView = layout.findViewById(R.id.iv_next);

        mMusicProgress = layout.findViewById(R.id.play_progress);

        mMusicListView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext()));
        mMusicListView.setAdapter(mHomePageFrmAdapter);
        mHomePageFrmAdapter.setItemClickListener(mMusicItemClickListener);
    }

    @Override
    protected void bindListener() {
        mMusicIcon.setOnClickListener(this);
        mPreImageView.setOnClickListener(this);
        mPlayImageView.setOnClickListener(this);
        mNextImageView.setOnClickListener(this);

        EventCenter.getInstance().registerIEvent(SongPlayChangeEvent.class, new IEventCallback() {
            @Override
            public void eventCallback(IEvent event) {
                SongPlayChangeEvent songPlayChangeEvent = (SongPlayChangeEvent) event;
                play(songPlayChangeEvent.getPos());
                mHomePageFrmAdapter.setPlayingPosition(songPlayChangeEvent.getPos());
            }
        });

        EventCenter.getInstance().registerIEvent(PublishProgressEvent.class, new IEventCallback() {
            @Override
            public void eventCallback(IEvent event) {
                PublishProgressEvent publishProgressEvent = (PublishProgressEvent) event;
                setProgress(publishProgressEvent.getProgress());
            }
        });
    }

    @Override
    protected void loadData() {

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
//                        mMusicListAdapter.notifyDataSetChanged();
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
            mActivity.getPlayService().play(position);
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
     * 播放音乐item
     * 
     * @param position int
     */
    private void play(int position) {
        onPlay(position);
    }

    /**
     * 播放时，更新控制面板
     * 
     * @param position int
     */
    public void onPlay(int position) {
        if (MusicUtils.sMusicList.isEmpty()){
            Toast.makeText(getActivity(), "当前手机没有MP3文件", Toast.LENGTH_LONG).show();
            return;
        }

        //设置进度条的总长度
        mMusicProgress.setMax(mActivity.getPlayService().getDuration());

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
}
