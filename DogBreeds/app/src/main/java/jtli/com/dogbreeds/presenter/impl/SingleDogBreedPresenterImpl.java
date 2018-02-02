package jtli.com.dogbreeds.presenter.impl;

import javax.inject.Inject;

import jtli.com.dogbreeds.bean.ImageBean;
import jtli.com.dogbreeds.http.service.DogBreedImageService;
import jtli.com.dogbreeds.http.service.DogBreedsService;
import jtli.com.dogbreeds.http.utils.Callback;
import jtli.com.dogbreeds.presenter.BasePresenter;
import jtli.com.dogbreeds.presenter.SingleDogBreedPresenter;

/**
 * Created by Jingtian(Tansent).
 */

public class SingleDogBreedPresenterImpl  extends BasePresenter<SingleDogBreedPresenter.View> implements SingleDogBreedPresenter.Presenter   {

    DogBreedImageService dogBreedImageService;

    @Inject
    public SingleDogBreedPresenterImpl(DogBreedImageService dogBreedImageService) {
        this.dogBreedImageService = dogBreedImageService;
    }

    @Override
    public void fetchADogBreed(String breedName) {
        invoke(dogBreedImageService.getBreedImages(breedName),new Callback<ImageBean>());
    }
}
