package jtli.com.dogbreeds.injector.component;

import javax.inject.Singleton;

import dagger.Component;
import jtli.com.dogbreeds.injector.module.DogBreedsModule;
import jtli.com.dogbreeds.injector.module.DogImagesModule;
import jtli.com.dogbreeds.injector.module.http.DogBreedsHttpModule;
import jtli.com.dogbreeds.injector.module.http.DogImagesHttpModule;
import jtli.com.dogbreeds.ui.activity.main.DogBreedActivity;
import jtli.com.dogbreeds.ui.fragment.home.HomeFragment;

/**
 * Created by Jingtian(Tansent).
 */
@Singleton
@Component(modules = { DogImagesHttpModule.class,DogImagesModule.class})
public interface DogImagesComponent {
    void injectDogImages(DogBreedActivity dogBreedActivity);
}
