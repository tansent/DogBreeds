package jtli.com.dogbreeds.http.utils;

import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.ToastUtils;

import jtli.com.dogbreeds.app.AppConstants;
import jtli.com.dogbreeds.http.LifeSubscription;
import jtli.com.dogbreeds.http.Stateful;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HttpUtils {

    public static <T> void invoke(LifeSubscription lifecycle, Observable<T> observable, Callback<T> callback) {
        Stateful target = null;
        if (lifecycle instanceof Stateful) {
            target = (Stateful) lifecycle;
            callback.setTarget(target);
        }
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShortToast("Internet is disconnected");
            if (target != null) {
                target.setState(AppConstants.STATE_ERROR);
            }
            return;
        }

        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
        lifecycle.bindSubscription(subscription);

    }
}
