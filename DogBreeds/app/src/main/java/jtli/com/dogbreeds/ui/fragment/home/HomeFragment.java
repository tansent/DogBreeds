package jtli.com.dogbreeds.ui.fragment.home;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import jtli.com.dogbreeds.R;
import jtli.com.dogbreeds.adapter.DogBreedsAdapter;
import jtli.com.dogbreeds.bean.MessageBean;
import jtli.com.dogbreeds.bean.Model;
import jtli.com.dogbreeds.injector.component.DaggerDogBreedsComponent;
import jtli.com.dogbreeds.injector.module.DogBreedsModule;
import jtli.com.dogbreeds.injector.module.http.DogBreedsHttpModule;
import jtli.com.dogbreeds.presenter.DogBreedsPresenter;
import jtli.com.dogbreeds.presenter.impl.DogBreedsPresenterImpl;
import jtli.com.dogbreeds.ui.activity.main.DogBreedActivity;
import jtli.com.dogbreeds.ui.fragment.BaseFragment;

/**
 * Created by Jingtian(Tansent).
 */

public class HomeFragment extends BaseFragment<DogBreedsPresenterImpl> implements DogBreedsPresenter.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_android)
    RecyclerView rvAndroid;

    @BindView(R.id.srl_android)
    SwipeRefreshLayout srlAndroid;

    private boolean isRefresh = false;

    @Override
    protected void initInject() {
        DaggerDogBreedsComponent.builder()
                .dogBreedsHttpModule(new DogBreedsHttpModule())
                .dogBreedsModule(new DogBreedsModule())
                .build().injectDogBreeds(this);
    }

    @Override
    protected void loadData() {
        mPresenter.fetchDogBreedsData();
//        mPresenter.fetchRandomDogBreedsImage();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dog_breeds;
    }

    @Override
    protected void initView() {
        srlAndroid.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        rvAndroid.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAndroid.setAdapter(mAdapter);
        srlAndroid.setOnRefreshListener(this);

        ((DogBreedsAdapter)mAdapter).setOnItemClickListener(new DogBreedsAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(String title, String imgUrl, View view) {
//                ToastUtils.showShortToast(title);
                startBreedDetailActivity(title, imgUrl, view);
            }
        });
    }


    @Override
    public void refreshView(List<Model> data) {
        if (isRefresh){
            srlAndroid.setRefreshing(false);
            isRefresh = false;
            mAdapter.setNewData(data);
        }else{
            srlAndroid.setEnabled(true);
            mAdapter.addData(data);
            isRefresh = true; //to refresh once even at the beginning
        }
    }

    /**
     * drag and refresh
     */
    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh(){
        isRefresh = true;
        mPresenter.fetchDogBreedsData();
    }

    private void startBreedDetailActivity(String title, String imgUrl, View view) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DogBreedActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("imgUrl", imgUrl);
        /**
         * 用这个ActivityOptionsCompat is better than ActivityOptions, the former one can compat to version 16
         * ActivityOptionsCompat.makeSceneTransitionAnimation(）3rd param : value of transitionName
         *     <android.support.design.widget.AppBarLayout
         android:transitionName="zhihu_detail_title"
         android:fitsSystemWindows="true">
         */
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                view, getActivity().getResources().getString(R.string.dog_breed_title));
        getActivity().startActivity(intent, options.toBundle());
//        startActivity(intent);
    }

}
