package hlv.app.mvvmsample;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import hlv.app.mvvmsample.repo.remote.ApiRepository;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static App app;
    public Context context;
    public ApiRepository apiRepository;

    public static App get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        app = this;

        apiRepository = new ApiRepository();
    }
}
