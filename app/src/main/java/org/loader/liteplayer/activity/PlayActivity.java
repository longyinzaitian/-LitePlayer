package org.loader.liteplayer.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.loader.liteplayer.R;
import org.loader.liteplayer.event.EventCenter;
import org.loader.liteplayer.event.IEvent;
import org.loader.liteplayer.event.IEventCallback;
import org.loader.liteplayer.event.PublishProgressEvent;
import org.loader.liteplayer.event.SongPlayChangeEvent;
import org.loader.liteplayer.pojo.Music;
import org.loader.liteplayer.service.PlayService;
import org.loader.liteplayer.ui.CDView;
import org.loader.liteplayer.ui.LrcView;
import org.loader.liteplayer.ui.PagerIndicator;
import org.loader.liteplayer.utils.ImageTools;
import org.loader.liteplayer.utils.LogUtil;
import org.loader.liteplayer.utils.MobileUtils;
import org.loader.liteplayer.utils.MusicIconLoader;
import org.loader.liteplayer.utils.MusicUtils;
import org.loader.liteplayer.utils.PlayBgShape;
import org.loader.liteplayer.utils.PlayPageTransformer;

import java.util.ArrayList;

/**
 * 2015年8月15日 16:34:37
 *  博文地址：http://blog.csdn.net/u010156024
 *  @author longyinzaitian
 */
public class PlayActivity extends BaseActivity implements OnClickListener {
    private static String TAG = "PlayActivity";
    private LinearLayout mPlayContainer;
    /**back button*/
    private ImageView mPlayBackImageView;
    /**music title*/
    private TextView mMusicTitle;
    /**cd or lrc*/
    private ViewPager mViewPager;
    /**cd*/
    private CDView mCdView;
    /**seekbar*/
    private SeekBar mPlaySeekBar;
    /**start or pause*/
    private ImageButton mStartPlayButton;
    /**singer*/
    private TextView mSingerTextView;
    /**single line lrc*/
    private LrcView mLrcViewOnFirstPage;
    /** 7 lines lrc*/
    private LrcView mLrcViewOnSecondPage;
    /**indicator*/
    private PagerIndicator mPagerIndicator;

    /** cd view and lrc view */
    private ArrayList<View> mViewPagerContent = new ArrayList<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        getApplicationContext().unbindService(mPlayServiceConnection);
        super.onPause();
    }

    private OnPageChangeListener mPageChangeListener =
            new OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                if (mPlayService.isPlaying()){
                    mCdView.start();
                }
            } else {
                mCdView.pause();
            }
            mPagerIndicator.current(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    /**
     * 拖动进度条
     */
    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            mPlayService.seek(progress);
            mLrcViewOnFirstPage.onDrag(progress);
            mLrcViewOnSecondPage.onDrag(progress);
        }
    };

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mViewPagerContent.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        /**
         * 该方法是PagerAdapter的预加载方法，系统调用 当显示第一个界面时，
         * 第二个界面已经预加载，此时调用的就是该方法。
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViewPagerContent.get(position));
            return mViewPagerContent.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            (container).removeView((View) object);
        }
    };

    /**
     * 初始化viewpager的内容
     */
    private void initViewPagerContent() {
        View cd = View.inflate(this, R.layout.play_pager_item_1, null);
        mCdView = cd.findViewById(R.id.play_cdview);
        mSingerTextView = cd.findViewById(R.id.play_singer);
        mLrcViewOnFirstPage = cd.findViewById(R.id.play_first_lrc);

        View lrcView = View.inflate(this, R.layout.play_pager_item_2, null);
        mLrcViewOnSecondPage = lrcView.findViewById(R.id.play_first_lrc_2);

        mViewPagerContent.add(cd);
        mViewPagerContent.add(lrcView);
    }

    @SuppressWarnings("deprecation")
    private void setBackground(int position) {
        if(position==0){
            return ;
        }

        Bitmap bgBitmap=null;
        if(MusicUtils.sMusicList.size()!=0){
            Music currentMusic = MusicUtils.sMusicList.get(position);
            bgBitmap = MusicIconLoader.getInstance().load(
                    currentMusic.getImage());
        }

        if (bgBitmap == null) {
            bgBitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
        }

        mPlayContainer.setDividerDrawable(
                new ShapeDrawable(new PlayBgShape(bgBitmap)));
    }

    /**
     * 上一曲
     * 
     * @param view View
     */
    public void pre(View view) {
        mPlayService.pre(); // 上一曲
    }

    /**
     * 播放 or 暂停
     * 
     * @param view View
     */
    public void play(View view) {
        if (MusicUtils.sMusicList.isEmpty()) {
            Toast.makeText(PlayActivity.this, "当前手机没有MP3文件", Toast.LENGTH_LONG).show();
            return ;
        }

        if (mPlayService.isPlaying()) {
            mPlayService.pause(); // 暂停
            mCdView.pause();
            mStartPlayButton
                    .setImageResource(R.drawable.player_btn_play_normal);
        } else {
            // 播放
            onPlay(mPlayService.resume());
        }
    }

    /**
     * 上一曲
     * 
     * @param view View
     */
    public void next(View view) {
        mPlayService.next(); // 上一曲
    }

    /**
     * 播放时调用 主要设置显示当前播放音乐的信息
     * 
     * @param position int
     */
    private void onPlay(int position) {
        Bitmap bmp=null;
        if(!MusicUtils.sMusicList.isEmpty()){
            Music music = MusicUtils.sMusicList.get(position);
            
            mMusicTitle.setText(music.getTitle());
            mSingerTextView.setText(music.getArtist());
            mPlaySeekBar.setMax(music.getLength());
            bmp = MusicIconLoader.getInstance().load(music.getImage());
        }

        if (bmp == null){
            bmp = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
        }

        mCdView.setImage(ImageTools.scaleBitmap(bmp,
                (int) (MobileUtils.getScreenWidth() * 0.8)));

        if (mPlayService.isPlaying()) {
            mCdView.start();
            mStartPlayButton
                    .setImageResource(R.drawable.player_btn_pause_normal);
        } else {
            mCdView.pause();
            mStartPlayButton
                    .setImageResource(R.drawable.player_btn_play_normal);
        }
    }

    private void setLrc(int position) {
        if(MusicUtils.sMusicList.size()!=0){
            Music music = MusicUtils.sMusicList.get(position);
            String lrcPath = MusicUtils.getLrcDir() + music.getTitle() + ".lrc";
            mLrcViewOnFirstPage.setLrcPath(lrcPath);
            mLrcViewOnSecondPage.setLrcPath(lrcPath);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.iv_play_back:
            finish();
            break;
        default:
            break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.play_activity_layout;
    }

    @Override
    protected void bindView() {
        mPlayContainer = findViewById(R.id.ll_play_container);
        mPlayBackImageView = findViewById(R.id.iv_play_back);
        mMusicTitle = findViewById(R.id.tv_music_title);
        mViewPager = findViewById(R.id.vp_play_container);
        mPlaySeekBar = findViewById(R.id.sb_play_progress);
        mStartPlayButton = findViewById(R.id.ib_play_start);
        mPagerIndicator = findViewById(R.id.pi_play_indicator);
    }

    @Override
    protected void bindListener() {
        // 动态设置seekBar的margin
        MarginLayoutParams p = (MarginLayoutParams) mPlaySeekBar
                .getLayoutParams();
        p.leftMargin = (int) (MobileUtils.getScreenWidth() * 0.1);
        p.rightMargin = (int) (MobileUtils.getScreenWidth() * 0.1);

        mPlaySeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);

        initViewPagerContent();
        // 设置viewpager的切换动画
        mViewPager.setPageTransformer(true, new PlayPageTransformer());
        mPagerIndicator.create(mViewPagerContent.size());
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mViewPager.setAdapter(mPagerAdapter);

        mPlayBackImageView.setOnClickListener(this);

        EventCenter.getInstance().registerIEvent(PublishProgressEvent.class, mPublishProgressEventCallback);
        EventCenter.getInstance().registerIEvent(SongPlayChangeEvent.class, mSongPlayEventCallback);
    }

    private IEventCallback mPublishProgressEventCallback = new IEventCallback() {
        @Override
        public void eventCallback(IEvent event) {
            PublishProgressEvent publishProgressEvent = (PublishProgressEvent) event;
            mPlaySeekBar.setProgress(publishProgressEvent.getProgress());
        }
    };

    private IEventCallback mSongPlayEventCallback = new IEventCallback() {
        @Override
        public void eventCallback(IEvent event) {
            SongPlayChangeEvent songPlayChangeEvent = (SongPlayChangeEvent) event;
            onPlay(songPlayChangeEvent.getPos());
        }
    };

    @Override
    protected void loadData() {
        getApplicationContext().bindService(new Intent(this, PlayService.class),
                mPlayServiceConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void clearData() {
        EventCenter.getInstance().removeIEventCallback(PublishProgressEvent.class, mPublishProgressEventCallback);
        EventCenter.getInstance().removeIEventCallback(PublishProgressEvent.class, mSongPlayEventCallback);
    }

    private ServiceConnection mPlayServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.l(TAG, "play--->onServiceDisconnected");
            mPlayService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayService = ((PlayService.PlayBinder) service).getService();
            onPlay(mPlayService.getPlayingPosition());
        }
    };
}