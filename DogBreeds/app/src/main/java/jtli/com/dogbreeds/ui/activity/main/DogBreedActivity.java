package jtli.com.dogbreeds.ui.activity.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import jtli.com.dogbreeds.R;
import jtli.com.dogbreeds.app.AppConstants;
import jtli.com.dogbreeds.bean.ImageBean;
import jtli.com.dogbreeds.injector.component.DaggerDogImagesComponent;
import jtli.com.dogbreeds.injector.module.DogBreedsModule;
import jtli.com.dogbreeds.injector.module.DogImagesModule;
import jtli.com.dogbreeds.injector.module.http.DogBreedsHttpModule;
import jtli.com.dogbreeds.injector.module.http.DogImagesHttpModule;
import jtli.com.dogbreeds.presenter.SingleDogBreedPresenter;
import jtli.com.dogbreeds.presenter.impl.SingleDogBreedPresenterImpl;
import jtli.com.dogbreeds.ui.activity.base.LoadingBaseActivity;
import jtli.com.dogbreeds.utils.GlideUtils;

public class DogBreedActivity extends LoadingBaseActivity<SingleDogBreedPresenterImpl> implements SingleDogBreedPresenter.View {

    @BindView(R.id.rcv_activity)
    RecyclerView rcv_activity;

    private String breedName;
    private String imgUrl;

    @Inject
    protected BaseQuickAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_base;
    }

    @Override
    public int setFrameLayoutId() {
        return R.id.fl_base_content;
    }

    protected ImageView detailBarImage;
    protected Toolbar toolbar_dog_title;
    protected TextView detailBarCopyright;
    @Override
    protected void initUI() {
        detailBarImage = (ImageView) findViewById(R.id.detail_bar_image);
        toolbar_dog_title = (Toolbar) findViewById(R.id.toolbar_dog_title);
        detailBarCopyright = (TextView) findViewById(R.id.detail_bar_copyright);
    }

    @Override
    protected void initInject() {
        DaggerDogImagesComponent.builder()
                .dogImagesHttpModule(new DogImagesHttpModule())
                .dogImagesModule(new DogImagesModule())
                .build().injectDogImages(this);
    }

    @Override
    protected void initView() {
        rcv_activity.setLayoutManager(new GridLayoutManager(this,2));
        rcv_activity.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        breedName = getIntent().getStringExtra("title");
        imgUrl = getIntent().getStringExtra("imgUrl");
        mPresenter.fetchADogBreed(breedName);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_dog_breed;
    }

    @Override
    public void refreshView(ImageBean mData) {
        setState(AppConstants.STATE_SUCCESS);
        List<String> images = mData.getMessage();
        setToolBar(toolbar_dog_title, breedName);
        GlideUtils.load(this, imgUrl, detailBarImage);
        mAdapter.setNewData(images);
    }
}
