package jtli.com.dogbreeds.http.utils;

import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.ToastUtils;

import jtli.com.dogbreeds.app.AppConstants;
import jtli.com.dogbreeds.http.Stateful;
import jtli.com.dogbreeds.presenter.BaseView;
import rx.Subscriber;

/**
 * Created by Jingtian(Tansent).
 */

public class Callback<T> extends Subscriber<T> {

    private Stateful target;

    public void setTarget(Stateful target) {
        this.target = target;
    }

    public void detachView() {
        if (target != null) {
            target = null;
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onfail();
    }

    @Override
    public void onNext(T data) {
        target.setState(AppConstants.STATE_SUCCESS);
        onResponse();
        onResponse(data);
    }

    public void onfail() {
        if (!NetworkUtils.isAvailableByPing()) {
            ToastUtils.showShortToast("Your internet connection has some issus");
            if (target != null) {
                target.setState(AppConstants.STATE_ERROR);
            }
            return;
        }
        ToastUtils.showShortToast("Internet error, please try again");
        if (target != null) {
            target.setState(AppConstants.STATE_EMPTY);
        }
    }

    public void onResponse(T data) {
//        if(data != null){
//            ((BaseView) target).refreshView(data);
//        }

        ((BaseView) target).refreshView(data);
    }

    public void onResponse() {
    }
}
