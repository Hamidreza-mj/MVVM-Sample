package hlv.app.mvvmsample.repo.remote.api;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import hlv.app.mvvmsample.model.User;
import hlv.app.mvvmsample.repo.remote.RetrofitClient;
import hlv.app.mvvmsample.repo.remote.event.ResponseEvent;
import hlv.app.mvvmsample.repo.remote.services.GetUsersService;
import hlv.app.mvvmsample.util.Constants;
import hlv.app.mvvmsample.util.networking.NetworkingHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private final MutableLiveData<ResponseEvent<ArrayList<User>>> userEventLive = new MutableLiveData<>();

    public void users(int page) {
        ResponseEvent<ArrayList<User>> event = new ResponseEvent<>();
        event.setPageMeta(page, Constants.Networking.PER_PAGE, Constants.Networking.TOTAL_PAGE);
        userEventLive.setValue(event.loadingResponse());

        if (!NetworkingHelper.getNetworking().isOnline()) {
            userEventLive.setValue(event.networkErrorResponse());
            return;
        }

        GetUsersService getUsersService = RetrofitClient.get().create(GetUsersService.class);
        Call<JsonElement> call = getUsersService.getUsers(page, Constants.Networking.PER_PAGE, "us");

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                try {
                    if (!response.isSuccessful() || response.body() == null) {
                        userEventLive.setValue(event.failureResponse("Response is empty!"));
                        return;
                    }

                    JsonObject responseObject = response.body().getAsJsonObject();
                    JsonArray arrayResults = responseObject.get("results").getAsJsonArray();

                    ArrayList<User> userList = new ArrayList<>();

                    for (int i = 0; i < arrayResults.size(); i++) {
                        JsonObject object = arrayResults.get(i).getAsJsonObject();

                        JsonObject nameObject = object.getAsJsonObject("name");
                        String title = nameObject.get("title").getAsString();
                        String first = nameObject.get("first").getAsString();
                        String last = nameObject.get("last").getAsString();

                        JsonObject loginObject = object.getAsJsonObject("login");
                        String uuid = loginObject.get("uuid").getAsString();
                        String username = loginObject.get("username").getAsString();
                        String sha1 = loginObject.get("sha1").getAsString();

                        String name = title + ". " + first + " " + last;

                        String image = object.getAsJsonObject("picture").get("large").getAsString();

                        int age = object.getAsJsonObject("dob").get("age").getAsInt();

                        boolean isMale = object.get("gender").getAsString().equalsIgnoreCase("male");

                        String uniqueID = uuid + username + sha1;

                        User user2 = new User(i, name, image, age, isMale, uniqueID);

                        userList.add(user2);
                    }

                    userEventLive.setValue(event.successResponse(userList));

                } catch (Exception e) {
                    e.printStackTrace();
                    userEventLive.setValue(event.failureResponse(e.getMessage()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Log.e("Error Retrofit", t.getMessage());
                userEventLive.setValue(event.failureResponse(t.getMessage()));
            }
        });
    }

    public MutableLiveData<ResponseEvent<ArrayList<User>>> getUserEventLive() {
        return userEventLive;
    }

    /*
    public void users2(int page) {
        GetUsersService getUsers = RetrofitClient.get().create(GetUsersService.class);
        Call<ResponseBody> call = getUsers.getUsers(page, 10);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        or JsonElement
                        if (response.body() != null) {
                            JSONObject object = new JSONObject(RetrofitUtil.get().getResponseBody(response));
                            Log.e("TAG", "tag");
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("Error Retrofit", t.getMessage());
            }
        });
    }
*/

}
