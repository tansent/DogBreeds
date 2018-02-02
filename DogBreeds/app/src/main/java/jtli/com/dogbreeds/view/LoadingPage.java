package jtli.com.dogbreeds.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import jtli.com.dogbreeds.R;
import jtli.com.dogbreeds.app.AppConstants;

import static jtli.com.dogbreeds.app.AppConstants.STATE_EMPTY;
import static jtli.com.dogbreeds.app.AppConstants.STATE_ERROR;
import static jtli.com.dogbreeds.app.AppConstants.STATE_LOADING;
import static jtli.com.dogbreeds.app.AppConstants.STATE_SUCCESS;
import static jtli.com.dogbreeds.app.AppConstants.STATE_UNKNOWN;


/**
 * For different status of page loading view
 */

public abstract class LoadingPage extends FrameLayout {
    private View loadingView;                 // loading View
    private View errorView;                   // error View
    private View emptyView;                   // empty View
    public View contentView;                 // successful View

    private AnimationDrawable mAnimationDrawable;
//    private ImageView img;
    private ProgressBar pb;

    public int state = STATE_UNKNOWN;

    private Context mContext;

    public LoadingPage(Context context) {
        this(context,null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();//initialize 4 views
    }


    private void init() {
        this.setBackgroundColor(getResources().getColor(R.color.colorPageBg));
        //add loadingView to frameLayout
        if (loadingView == null) {
            loadingView = createLoadingView();
            this.addView(loadingView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        //add emptyView to frameLayout
        if (emptyView == null) {
            emptyView = createEmptyView();
            this.addView(emptyView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        }
        //add errorView to frameLayout
        if (errorView == null) {
            errorView = createErrorView();
            this.addView(errorView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        showPage();//set page according to different state
    }


    private View createLoadingView() {
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.basefragment_state_loading, null);
//        img = (ImageView) loadingView.getRootView().findViewById(R.id.img_progress);
//        // 加载动画 这边也可以直接用progressbar 可以看看topnews页下拉刷新就是只用用progressbar控制动画
//        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
//        // 默认进入页面就开启动画
//        if (!mAnimationDrawable.isRunning()) {
//            mAnimationDrawable.start();
//        }
        pb = (ProgressBar) loadingView.getRootView().findViewById(R.id.pb_progress);

        return loadingView;
    }

    private View createEmptyView() {
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.basefragment_state_empty, null);
        return emptyView;
    }

    private View createErrorView() {
        errorView = LayoutInflater.from(mContext).inflate(R.layout.basefragment_state_error, null);
        errorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                state = STATE_LOADING;
                showPage();
                loadData();
            }
        });
        return errorView;
    }

    private void startAnimation() {
//        if (!mAnimationDrawable.isRunning()) {
//            mAnimationDrawable.start();
//        }
        pb.setProgress(View.VISIBLE);
    }

    private void stopAnimation() {
//        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
//            mAnimationDrawable.stop();
//        }
        pb.setVisibility(View.GONE);
    }

    public void showPage() {
        if (loadingView != null) {
            if (state == STATE_UNKNOWN || state == STATE_LOADING) {
                loadingView.setVisibility(View.VISIBLE);
                // 开始动画
                startAnimation();
            } else {
                // 关闭动画
                stopAnimation();
                loadingView.setVisibility(View.GONE);
            }
        }
        if (state == STATE_EMPTY || state == STATE_ERROR || state == STATE_SUCCESS) {
            // 关闭动画
            stopAnimation();
        }


        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.GONE);
        }

        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.GONE);
        }

        if (state == STATE_SUCCESS) {
            if (contentView == null) {
                contentView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
                addView(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                initView();
            }
            contentView.setVisibility(View.VISIBLE);
        } else {
            if (contentView != null) {
                contentView.setVisibility(View.GONE);
            }
        }
    }

    /** 3
     * 子类关于View的操作(如setAdapter)都必须在这里面，会因为页面状态不为成功，而binding还没创建就引用而导致空指针。
     */
    protected abstract void initView();

    /** 1
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected abstract void loadData();

    /**
     * load success view
     * @return
     */
    protected abstract int getLayoutId();
}
