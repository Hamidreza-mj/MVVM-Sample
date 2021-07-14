package hlv.app.mvvmsample.repo.remote.services;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hlv.app.mvvmsample.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GetUsersService {

    /**
     * https://randomuser.me/api/1/12
     *
     * @param page     current page
     * @param per_page max of items can show in each page
     * @return list of users
     */
    @GET("api/{page}/{results}")
    Call<List<User>> getAllUsersWithPath(
            @Path("page") int page,
            @Path("results") int per_page
    );

    /**
     * https://randomuser.me/api?page=1&results=2&seed=abc
     *
     * @param page    current page
     * @param perPage max of items can show in each page
     * @return list of users
     */
    @GET("api")
    Call<JsonElement> getUsers(@Query("page") int page, @Query("results") int perPage, @Query("nat") String national);

    @GET("api")
    Call<List<User>> getUsers(@QueryMap Map<String, String> queryMap);

    @POST("post")
    Call<POST> createUser(@Body User user);

    @FormUrlEncoded
    @POST("post")
    Call<User> createUser(
            @Field("id") int id,
            @Field("name") String name
    );

    @Headers({"Static-Header1: 1", "Static-Header2: 2"})
    @FormUrlEncoded
    @POST("post")
    Call<User> createUser(@Header("Dynamic-Head") String head, @FieldMap Map<String, String> bodyMap);
}
