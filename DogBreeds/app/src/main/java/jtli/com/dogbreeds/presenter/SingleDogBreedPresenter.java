package jtli.com.dogbreeds.presenter;

import jtli.com.dogbreeds.bean.ImageBean;

/**
 * Created by Jingtian(Tansent).
 */

public interface SingleDogBreedPresenter {

    interface View extends BaseView<ImageBean> {
    }

    interface Presenter{
        void fetchADogBreed(String breedName);
    }

}
