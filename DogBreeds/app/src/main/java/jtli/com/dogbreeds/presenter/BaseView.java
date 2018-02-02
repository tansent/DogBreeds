package jtli.com.dogbreeds.presenter;


public interface BaseView<T> {
    void refreshView(T mData);//invoked when successfully obtained data from the internet
}
