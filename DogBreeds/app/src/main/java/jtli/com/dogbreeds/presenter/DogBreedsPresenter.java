package jtli.com.dogbreeds.presenter;

import java.util.List;

import jtli.com.dogbreeds.bean.Model;

/**
 * Created by Jingtian(Tansent).
 */

public interface DogBreedsPresenter {

    interface View extends BaseView<List<Model>> {
    }
//    interface View extends BaseView<Model> {
//    }

    interface Presenter{
        void fetchDogBreedsData();
        void fetchRandomDogBreedsImage();
    }
}
