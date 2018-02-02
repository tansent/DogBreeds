package jtli.com.dogbreeds.injector.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Jingtian(Tansent).
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface DogBreedsUrl {
}
