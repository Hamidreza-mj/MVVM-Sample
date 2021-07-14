package hlv.app.mvvmsample;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import hlv.app.mvvmsample.repo.remote.ApiRepository;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static App app;
    public Context applicationContext;
    private ApiRepository apiRepository;

    public static App get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        app = this;

        if (apiRepository == null)
            apiRepository = new ApiRepository();
    }

    public ApiRepository apiRepository() {
        return apiRepository;
    }
}
