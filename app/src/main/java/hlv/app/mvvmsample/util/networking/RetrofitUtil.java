package hlv.app.mvvmsample.util.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class RetrofitUtil {

    private static RetrofitUtil retrofitUtil;

    private RetrofitUtil() {
    }

    public static RetrofitUtil get() {
        if (retrofitUtil == null)
            retrofitUtil = new RetrofitUtil();

        return retrofitUtil;
    }

    /**
     * getting ResponseBody
     */
    public String getResponseBody(Response<ResponseBody> response) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();

        if (response.body() != null) {
            reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));

            String line;

            try {
                while ((line = reader.readLine()) != null)
                    sb.append(line);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
