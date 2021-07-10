package hlv.app.mvvmsample.repo.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import hlv.app.mvvmsample.model.User;
import hlv.app.mvvmsample.repo.remote.interfaces.GetUsers;
import hlv.app.mvvmsample.viewmodel.UserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    public MutableLiveData<ArrayList<User>> userMutableLiveData = new MutableLiveData<>();

    public void users(int page) {
        GetUsers getUsers = RetrofitClient.get().create(GetUsers.class);
        Call<JsonElement> call = getUsers.getUsers(page, 1);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                try {
                    if (!response.isSuccessful() || response.body() == null) {
                        userMutableLiveData.setValue(null);
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

                        String name = title + ". " + first + " " + last;

                        String image = object.getAsJsonObject("picture").get("large").getAsString();

                        int age = object.getAsJsonObject("dob").get("age").getAsInt();

                        boolean isMale = object.get("gender").getAsString().equalsIgnoreCase("male");

                        User user2 = new User(i, name, image, age, isMale);

                        userList.add(user2);
                    }

                    userMutableLiveData.setValue(userList);

                } catch (Exception e) {
                    e.printStackTrace();
                    userMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Log.e("Error Retrofit", t.getMessage());
                userMutableLiveData.setValue(null);
            }
        });
    }

/*
    public void users2(int page) {
        GetUsers getUsers = RetrofitClient.get().create(GetUsers.class);
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
