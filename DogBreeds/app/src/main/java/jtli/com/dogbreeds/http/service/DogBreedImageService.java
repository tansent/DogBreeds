package jtli.com.dogbreeds.http.service;

import jtli.com.dogbreeds.bean.ImageBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Jingtian(Tansent).
 */

public interface DogBreedImageService {

    String API_DOG_BREEDS = "https://dog.ceo/";

    @GET("api/breed/{breed_name}/images")
    Observable<ImageBean> getBreedImages(@Path("breed_name") String breedName);
}
