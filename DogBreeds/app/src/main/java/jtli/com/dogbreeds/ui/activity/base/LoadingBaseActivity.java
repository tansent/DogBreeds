package jtli.com.dogbreeds.ui.activity.base;

import android.os.Bundle;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import jtli.com.dogbreeds.http.Stateful;
import jtli.com.dogbreeds.presenter.BasePresenter;
import jtli.com.dogbreeds.view.LoadingPage;

/**
 * Created by Jingtian(Tansent).
 */

public abstract class LoadingBaseActivity<T extends BasePresenter> extends BaseActivity implements Stateful {

    protected LoadingPage mLoadingPage;
    @Inject
    protected T mPresenter;
    private Unbinder bind;
    protected FrameLayout flBaseContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initInject();
        mPresenter.attachView(this);
        flBaseContent = (FrameLayout) findViewById(setFrameLayoutId());
        if (mLoadingPage == null) {
            mLoadingPage = new LoadingPage(this) {
                @Override
                protected void initView() {
                    bind = ButterKnife.bind(LoadingBaseActivity.this, contentView);
                    LoadingBaseActivity.this.initView();
                }

                @Override
                protected void loadData() {
                    LoadingBaseActivity.this.loadData();
                }

                @Override
                protected int getLayoutId() {
                    return LoadingBaseActivity.this.getContentLayoutId();
                }
            };
        }
        flBaseContent.addView(mLoadingPage);
        loadData();
    }

    @Override
    public void setState(int state) {
        mLoadingPage.state = state;
        mLoadingPage.showPage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }
    /**
     * findviewbyid(...) here
     * if using butteknife, no need to write anything here
     */
    protected abstract void initUI();

    /**
     * dagger2 injections
     */
    protected abstract void initInject();

    /**
     * set layout
     *
     * @return
     */
    public abstract int setFrameLayoutId();

    /**
     * 3
     * set view items
     */
    protected abstract void initView();


    /**
     * 1
     * set state based on the data got from the network
     * or
     * get data from intent
     */
    protected abstract void loadData();

    /**
     * 2
     * load layout when successfully access data from the internet
     *
     * @return
     */
    public abstract int getContentLayoutId();



}
