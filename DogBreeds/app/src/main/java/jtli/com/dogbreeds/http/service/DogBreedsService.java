package jtli.com.dogbreeds.http.service;

import jtli.com.dogbreeds.bean.ImageBean;
import jtli.com.dogbreeds.bean.MessageBean;
import jtli.com.dogbreeds.bean.SingleImageBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Jingtian(Tansent).
 */

public interface DogBreedsService {

    String API_DOG_BREEDS = "https://dog.ceo/";

    @GET("api/breeds/list")
    Observable<MessageBean> getDogBreedsListData();

    @GET("api/breed/{breed_name}/images")
    Observable<ImageBean> getBreedImages(@Path("breed_name") String breedName);

    @GET("api/breed/{breed_name}/images/random")
    Observable<SingleImageBean> getRandomBreedImage(@Path("breed_name") String breedName);

}
