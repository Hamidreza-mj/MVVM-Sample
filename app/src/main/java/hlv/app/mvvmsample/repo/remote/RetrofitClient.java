package hlv.app.mvvmsample.repo.remote;

import java.util.concurrent.TimeUnit;

import hlv.app.mvvmsample.util.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit get() {
        OkHttpClient okHttpClient = null;

        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(40, TimeUnit.SECONDS)
                    .writeTimeout(40, TimeUnit.SECONDS)
                    .build();
        }


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Urls.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

}
