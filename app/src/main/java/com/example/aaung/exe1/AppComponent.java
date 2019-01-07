package com.example.aaung.exe1;

import com.example.aaung.exe1.model.NetModule;
import com.example.aaung.exe1.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent
{
    void inject(MainActivity activity);
    // void inject(MyFragment fragment);
    // void inject(MyService service);
}
