package jtli.com.dogbreeds.ui.activity.main;

import butterknife.OnClick;
import jtli.com.dogbreeds.R;
import jtli.com.dogbreeds.ui.activity.base.ToolbarBaseActivity;

/**
 * Created by Jingtian(Tansent).
 */

public class AboutActivity extends ToolbarBaseActivity {

    @Override
    protected void initUI() {
        tvToolbarTitle.setText("About");
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_about_us;
    }

}
