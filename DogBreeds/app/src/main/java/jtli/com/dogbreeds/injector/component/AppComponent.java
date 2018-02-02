package jtli.com.dogbreeds.injector.component;

import javax.inject.Singleton;

import dagger.Component;
import jtli.com.dogbreeds.app.App;
import jtli.com.dogbreeds.injector.module.AppModule;

/**
 * Created by Jingtian(Tansent).
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    App getContext();  // provide Application Context
}
