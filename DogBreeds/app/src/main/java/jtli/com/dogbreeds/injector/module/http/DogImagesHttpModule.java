package jtli.com.dogbreeds.injector.module.http;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jtli.com.dogbreeds.http.service.DogBreedImageService;
import jtli.com.dogbreeds.http.service.DogBreedsService;
import jtli.com.dogbreeds.injector.qualifier.DogBreedsUrl;
import jtli.com.dogbreeds.injector.qualifier.DogImagesUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Jingtian(Tansent).
 */
@Module
public class DogImagesHttpModule extends BaseHttpModule  {

    @Singleton
    @Provides
    @DogImagesUrl
    Retrofit provideDogImagesRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, DogBreedsService.API_DOG_BREEDS);
    }

    @Singleton
    @Provides
    DogBreedImageService provideDoubanService(@DogImagesUrl Retrofit retrofit) {
        return retrofit.create(DogBreedImageService.class);
    }

}
