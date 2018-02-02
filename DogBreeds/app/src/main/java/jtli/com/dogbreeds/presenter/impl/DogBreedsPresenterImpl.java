package jtli.com.dogbreeds.presenter.impl;

import android.util.Log;

import com.zhy.http.okhttp.callback.StringCallback;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import jtli.com.dogbreeds.app.AppConstants;
import jtli.com.dogbreeds.bean.MessageBean;
import jtli.com.dogbreeds.bean.Model;
import jtli.com.dogbreeds.bean.SingleImageBean;
import jtli.com.dogbreeds.http.Stateful;
import jtli.com.dogbreeds.http.service.DogBreedsService;
import jtli.com.dogbreeds.http.utils.Callback;
import jtli.com.dogbreeds.presenter.BasePresenter;
import jtli.com.dogbreeds.presenter.DogBreedsPresenter;
import okhttp3.Call;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Jingtian(Tansent).
 */

public class DogBreedsPresenterImpl extends BasePresenter<DogBreedsPresenter.View> implements DogBreedsPresenter.Presenter{

    private DogBreedsService dogBreedsService;

    @Inject
    public DogBreedsPresenterImpl(DogBreedsService dogBreedsService) {
        this.dogBreedsService = dogBreedsService;
    }

    List<Model> list = new LinkedList<>();

    //useless
    List<String> breeds = new LinkedList<>();
    List<String> imgs1 = new LinkedList<>();
    List<String> imgs2 = new LinkedList<>();
    List<String> imgs3 = new LinkedList<>();
    //---------------------------
    @Override
    public void fetchDogBreedsData() {

        invoke(dogBreedsService.getDogBreedsListData(),
                new Callback<MessageBean>(){
            @Override
            public void onResponse(MessageBean data) {
                final List<String> breeds = data.getMessage();
                checkState(breeds);
                for (int i=0; i<breeds.size(); i++){
                    final Model model = new Model();
                    String breed = breeds.get(i);
                    model.setTitle(breed);
                    for(int j=1;j<=3;j++) {
                        final int finalI = i;
                        final int finalJ = j;
                        invoke(dogBreedsService.getRandomBreedImage(breed), new Callback<SingleImageBean>() {
                            @Override
                            public void onResponse(SingleImageBean data) {
                                String image = data.getMessage();
                                if(finalJ ==1)
                                    model.setImage1(image);
                                else if(finalJ ==2)
                                    model.setImage2(image);
                                else if(finalJ ==3)
                                    model.setImage3(image);

                                if (finalI == 3 && finalJ == 3){
                                    mView.refreshView(list);

                                }
                            }
                        });
                    }
                    list.add(model);
                }
                mView.refreshView(list);
            }
        });


    }

    @Override
    public void fetchRandomDogBreedsImage() {
    }

    /**
     * useless
     * @return
     */
    private Observable<String> getDogBreeds(){
        Observable<MessageBean> dogBreedsListData = dogBreedsService.getDogBreedsListData();

        return dogBreedsListData.concatMap(new Func1<MessageBean, Observable<String>>() {
            @Override
            public Observable<String> call(MessageBean messageBean) {
                return Observable.from(messageBean.getMessage());
            }
        }).doOnNext(new Action1<String>() {
            @Override
            public void call(String breed) {
                breeds.add(breed);

            }
        });
    }

    /**
     * useless
     * @return
     */
    private Observable<String> getImgs(){
        return getDogBreeds().concatMap(new Func1<String, Observable<SingleImageBean>>(){
            @Override
            public Observable<SingleImageBean> call(String breed) {
                return dogBreedsService.getRandomBreedImage(breed);
            }
        }).concatMap(new Func1<SingleImageBean, Observable<String>>() {
            @Override
            public Observable<String> call(SingleImageBean singleImageBean) {
                return Observable.just(singleImageBean.getMessage());
            }
        }).doOnNext(new Action1<String>() {
            @Override
            public void call(String img) {
                imgs1.add(img);
            }
        });
    }

    /**
     * useless
     * @return
     */
    private Observable<Model> result() {

//        return Observable.zip(getDogBreeds(), getImgs(), getImgs(), getImgs(), new Func2<String, String,String,String, Model>() {
//            @Override
//            public String call(String s, String s2) {
//                return null;
//            }
//
//            @Override
//            public Model call(String title, String img1, String img2,String img3) {
//                return new Model(title,img1,img2,img3);
//            }
//        });

        return Observable.zip(getDogBreeds(), getImgs(), new Func2<String, String, Model>() {
            @Override
            public Model call(String title, String img1) {
                return new Model(title,img1);
            }
        });
    }


}
