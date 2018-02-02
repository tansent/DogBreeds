package jtli.com.dogbreeds.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jtli.com.dogbreeds.R;
import jtli.com.dogbreeds.adapter.HomeFragmentPageAdapter;
import jtli.com.dogbreeds.ui.activity.base.BaseActivity;
import jtli.com.dogbreeds.ui.fragment.home.HomeFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.dl_layout)
    DrawerLayout dlLayout;

    @BindView(R.id.fl_title_menu)
    FrameLayout nvMenu;

    @BindView(R.id.toolbar)
    Toolbar tbToolbar;

    @BindView(R.id.fl_main)
    FrameLayout flMain;

    @BindView(R.id.vp_content)
    ViewPager vpContent;

    @OnClick(R.id.fl_title_menu)
    public void flTitleMenu() {
        dlLayout.openDrawer(GravityCompat.START);
    }

    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;

    @OnClick(R.id.iv_refresh)
    public void refresh() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment fragment = (HomeFragment) fragmentManager.findFragmentByTag("android:switcher:" + R.id.vp_content+ ":"+ vpContent.getCurrentItem());
        fragment.refresh(); //fetch data again from the server and pass to the adapter
        ToastUtils.showLongToast("Lots of data to refresh, please be patient");
    }

    @OnClick(R.id.fl_exit_app)
    public void exitApp() {
        this.killAll();
    }

    @OnClick(R.id.fl_feedback)
    public void feedback() {
        ToastUtils.showShortToast("Feedback has not been developed yet");
    }

    @OnClick(R.id.fl_about_us)
    public void aboutUs() {
        startActivity(new Intent(this, AboutActivity.class));
    }

    @OnClick(R.id.fl_setting)
    public void setting() {
        ToastUtils.showShortToast("In fact, nothing to set up~");
    }

    @OnClick(R.id.fl_theme_color)
    public void themeColor() {
        ToastUtils.showShortToast("Theme has not been developed yet");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolBar(tbToolbar, "Dog Breeds", false);
        initView();
    }

    private void initView() {
        List<Fragment> mFragmentList = new ArrayList<>();

        mFragmentList.add(new HomeFragment());
        //when Fragments binded to the adapter, the system will set fragments a TAG by default
        //tag format: "android:switcher:" + R.id.vp_content+ ":"+ vpContent.getCurrentItem()
        //if we set the fragment tag again, an IlligalStatementException will occur.
        vpContent.setAdapter(new HomeFragmentPageAdapter(getSupportFragmentManager(), mFragmentList));

    }

    /**
     * control on and off of toolbar,navigation bar(drawer)
     */
    @Override
    public void onBackPressed() {
        if (dlLayout.isDrawerOpen(GravityCompat.START)) {
            dlLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     *  don't quit when press back
     * This function has priority over "onBackPressed()"
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (dlLayout.isDrawerOpen(GravityCompat.START)) {
                dlLayout.closeDrawer(GravityCompat.START);
            } else {
                // not quit app, but run in background
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            onUserInteraction();
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
}
