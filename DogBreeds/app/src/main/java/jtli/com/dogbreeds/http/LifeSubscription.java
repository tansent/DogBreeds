package jtli.com.dogbreeds.http;

import rx.Subscription;

public interface LifeSubscription {
    void bindSubscription(Subscription subscription);
}

