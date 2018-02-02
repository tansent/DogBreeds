package jtli.com.dogbreeds.injector.module;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jtli.com.dogbreeds.adapter.DogBreedImagesAdapter;
import jtli.com.dogbreeds.adapter.DogBreedsAdapter;

/**
 * Created by Jingtian(Tansent).
 */
@Module
public class DogImagesModule {

    @Provides
    @Singleton
    public BaseQuickAdapter provideAdapter() {
        return new DogBreedImagesAdapter(new ArrayList());
    }

}
