package jtli.com.dogbreeds.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

import jtli.com.dogbreeds.app.AppConstants;
import jtli.com.dogbreeds.http.LifeSubscription;
import jtli.com.dogbreeds.http.Stateful;
import jtli.com.dogbreeds.http.utils.Callback;
import jtli.com.dogbreeds.http.utils.HttpUtils;
import rx.Observable;

/**
 * Created by Jingtian(Tansent).
 */

public class BasePresenter<T extends BaseView> {

    protected Reference<T> mReferenceView;//indicating pages, BaseFragment or BaseActivity
    protected T mView;
    private Callback callback;

    public void attachView(LifeSubscription mLifeSubscription) {
        this.mReferenceView = new WeakReference<>((T) mLifeSubscription);
        mView = mReferenceView.get();
    }

    protected <T> void invoke(Observable<T> observable, Callback<T> callback) {
        this.callback = callback;
        HttpUtils.invoke((LifeSubscription) mView, observable, callback);
    }

    /**
     * check if no data is obtained
     * if so, set state to STATE_EMPTY and return
     *
     * @param list
     */
    public void checkState(List list) {
        if (list.size() == 0) {
            if (mView instanceof Stateful)
                ((Stateful) mView).setState(AppConstants.STATE_EMPTY);
            return;
        }
    }

    public void detachView() {
        if (mReferenceView != null)
            mReferenceView.clear();
        mReferenceView = null;
        if (mView != null)
            mView = null;
        if (callback != null) {
            callback.detachView();
        }
    }



}
