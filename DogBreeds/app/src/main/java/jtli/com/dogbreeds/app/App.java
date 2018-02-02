package jtli.com.dogbreeds.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.blankj.utilcode.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import jtli.com.dogbreeds.injector.component.AppComponent;
import jtli.com.dogbreeds.injector.component.DaggerAppComponent;
import jtli.com.dogbreeds.injector.module.AppModule;
import okhttp3.OkHttpClient;


/**
 * Created by Jingtian(Tansent).
 */

public class App extends Application {

    private static App instance;
    public static AppComponent appComponent;
    private static Handler handler;
    private static int mainThreadID;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mainThreadID = android.os.Process.myTid(); //Main thread id
        handler=new Handler();
        Utils.init(this);//utils initialisation library https://github.com/Blankj/AndroidUtilCode/blob/master/README-CN.md
        initOkHttpUtils();
    }

    public static AppComponent getAppComponent(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }

    public static Context getApplication() {
        return instance;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainTid() {
        return mainThreadID;
    }

    private void initOkHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

}
