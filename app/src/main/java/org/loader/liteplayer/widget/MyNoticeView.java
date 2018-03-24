package org.loader.liteplayer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;

import org.loader.liteplayer.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author husyin
 * @date 2017年12月20日
 **/
public class MyNoticeView extends TextSwitcher {
    private static final String TAG = "MyNoticeView";

    private static final int M_INIT_DELAY = 4000;
    private static final int M_INIT_INTERVAL = 3000;

    private Drawable mIcon;
    private final TextFactory mDefaultFactory = new TextFactory();
    private List<String> mDataList = new ArrayList<>();

    private int mIndex = 0;
    private int mInterval = 4000;
    private int mDuration = 900;

    private int mIconTint = 0xff999999;
    private int mIconPadding = 0;
    private int mPaddingLeft = 0;

    private boolean mIsVisible = false;
    private boolean mIsStarted = false;
    private boolean mIsResumed = true;
    private boolean mIsRunning = false;
    private boolean mOrientHorizontal = false;
    /**
     * 通过计算
     */
    private int maxLengthPerLine = 18;
    /**
     * 跑马灯在运行的话不要switch滚动
     */
    private boolean isMarquee = false;

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsRunning) {
                if (!isMarquee) {
                    show(mIndex + 1);
                }
                postDelayed(mRunnable, mInterval);
            }
        }
    };

    public MyNoticeView(Context context) {
        this(context, null);
    }

    public MyNoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context, attrs);
        Animation mInUp = anim(1.5f, 0);
        Animation mOutUp = anim(0, -1.5f);
        setInAnimation(mInUp);
        setOutAnimation(mOutUp);
        setFactory(mDefaultFactory);
        mInUp.setDuration(mDuration);
        mOutUp.setDuration(mDuration);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    private void initWithContext(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyNoticeView);
        mOrientHorizontal = a.getBoolean(R.styleable.MyNoticeView_nvOrient, false);
        mIcon = a.getDrawable(R.styleable.MyNoticeView_nvIcon);
        mIconPadding = (int) a.getDimension(R.styleable.MyNoticeView_nvIconPadding, 0);

        boolean hasIconTint = a.hasValue(R.styleable.MyNoticeView_nvIconTint);

        if (hasIconTint) {
            mIconTint = a.getColor(R.styleable.MyNoticeView_nvIconTint, 0xff999999);
        }

        mInterval = a.getInteger(R.styleable.MyNoticeView_nvInterval, 4000);
        mDuration = a.getInteger(R.styleable.MyNoticeView_nvDuration, 900);

        mDefaultFactory.resolve(a);
        a.recycle();

        if (mIcon != null) {
            mPaddingLeft = getPaddingLeft();
            int realPaddingLeft = mPaddingLeft + mIconPadding + mIcon.getIntrinsicWidth();
            setPadding(realPaddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());

            if (hasIconTint) {
                mIcon = mIcon.mutate();
                DrawableCompat.setTint(mIcon, mIconTint);
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mIcon != null) {
            int y = (getMeasuredHeight() - mIcon.getIntrinsicWidth()) / 2;
            mIcon.setBounds(mPaddingLeft, y, mPaddingLeft + mIcon.getIntrinsicWidth(), y + mIcon.getIntrinsicHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIcon != null) {
            mIcon.draw(canvas);
        }
    }

    public int getIndex() {
        return mIndex;
    }

    public void start(List<String> list) {
        mDataList = list;
        if (mDataList == null || mDataList.size() < 1) {
            mIsStarted = false;
            update();
        } else {
            mIsStarted = true;
            update();
            show(0);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIsVisible = false;
        update();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mIsVisible = visibility == VISIBLE;
        update();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mIsResumed = true;
                update();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsResumed = true;
                update();
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private void update() {
        boolean running = mIsVisible && mIsResumed && mIsStarted;
        if (running != mIsRunning) {
            if (running) {
                String result = mDataList.get(mIndex <= 0 ? mDataList.size() - 1 : mIndex - 1);
                int length = mDataList.get(mIndex).length();
                //计算
                int i = length % maxLengthPerLine == 0 ? length / maxLengthPerLine : length / maxLengthPerLine;
                mInterval = M_INIT_DELAY + M_INIT_INTERVAL * i;
                //根据list的字节长度判断延迟的时间
                postDelayed(mRunnable, mInterval);
            } else {
                removeCallbacks(mRunnable);
            }
            mIsRunning = running;
        }
    }

    private void show(int index) {
        mIndex = index % mDataList.size();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setText(Html.fromHtml(mDataList.get(mIndex), Html.FROM_HTML_MODE_LEGACY));
        } else {
            setText(Html.fromHtml(mDataList.get(mIndex)));
        }
    }

    private Animation anim(float from, float to) {
        TranslateAnimation anim;
        if (mOrientHorizontal) {
            anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, from, Animation.RELATIVE_TO_PARENT, to, 0, 0, 0, 0);
        } else {
            anim = new TranslateAnimation(0, 0f, 0, 0f, Animation.RELATIVE_TO_PARENT, from, Animation.RELATIVE_TO_PARENT, to);
        }

        anim.setDuration(mDuration);
        anim.setFillAfter(false);
        anim.setInterpolator(new LinearInterpolator());

        return anim;
    }

    class TextFactory implements ViewFactory {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();

        float size = dp2px(14);
        int color = 1;
        int lines = 1;
        int gravity = Gravity.LEFT;

        void resolve(TypedArray ta) {
            lines = ta.getInteger(R.styleable.MyNoticeView_nvTextMaxLines, lines);
            size = ta.getDimension(R.styleable.MyNoticeView_nvTextSize, size);
            color = ta.getColor(R.styleable.MyNoticeView_nvTextColor, color);
            gravity = ta.getInteger(R.styleable.MyNoticeView_nvTextGravity, gravity);
        }

        private int dp2px(float dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
        }

        @Override
        public View makeView() {
            MarqueeTextView tView = new MarqueeTextView(getContext());
            tView.setSingleLine(true);
            tView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            tView.setGravity(Gravity.CENTER_VERTICAL | gravity);
            tView.setMaxEms(maxLengthPerLine);

            if (color != 1) {
                tView.setTextColor(color);
            }

            tView.setMarqueeRepeatLimit(1);
            tView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            return tView;
        }
    }
}
