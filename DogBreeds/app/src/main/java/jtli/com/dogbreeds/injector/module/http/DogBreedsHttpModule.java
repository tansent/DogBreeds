package jtli.com.dogbreeds.injector.module.http;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import jtli.com.dogbreeds.http.service.DogBreedsService;
import jtli.com.dogbreeds.injector.qualifier.DogBreedsUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Jingtian(Tansent).
 */
@Module
public class DogBreedsHttpModule extends BaseHttpModule {

    @Singleton
    @Provides
    @DogBreedsUrl
    Retrofit provideDogBreedsRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, DogBreedsService.API_DOG_BREEDS);
    }

    @Singleton
    @Provides
    DogBreedsService provideDoubanService(@DogBreedsUrl Retrofit retrofit) {
        return retrofit.create(DogBreedsService.class);
    }


}
