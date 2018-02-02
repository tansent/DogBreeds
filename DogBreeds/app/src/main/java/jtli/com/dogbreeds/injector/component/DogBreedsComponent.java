package jtli.com.dogbreeds.injector.component;

import javax.inject.Singleton;

import dagger.Component;
import jtli.com.dogbreeds.injector.module.DogBreedsModule;
import jtli.com.dogbreeds.injector.module.http.DogBreedsHttpModule;
import jtli.com.dogbreeds.ui.fragment.home.HomeFragment;

/**
 * Created by Jingtian(Tansent).
 */
@Singleton
@Component(modules = { DogBreedsHttpModule.class,DogBreedsModule.class})
public interface DogBreedsComponent {
    void injectDogBreeds(HomeFragment homeFragment);
}
