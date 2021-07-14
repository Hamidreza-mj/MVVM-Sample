package hlv.app.mvvmsample.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import hlv.app.mvvmsample.App;
import hlv.app.mvvmsample.model.User;
import hlv.app.mvvmsample.repo.remote.event.ResponseEvent;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<ResponseEvent<ArrayList<User>>> usersLiveData;

    public UserViewModel() {
        usersLiveData = App.get().apiRepository().getUserEventLive();
    }

    public void fetchData(int page) {
        App.get().apiRepository().users(page);
    }

    /**
     * when initiate class must be call this
     */
    public void fetchData() {
        fetchData(1);
    }

    public LiveData<ResponseEvent<ArrayList<User>>> getUsersLiveData() {
        return usersLiveData;
    }

    public int footerVisibility() {
        return (usersLiveData.getValue().isLastPage()) ? View.VISIBLE : View.GONE;
    }

    /**
     * for loading offline temporary data call this
     */
    public void tempData() {
        //    private MutableLiveData<ArrayList<User>> usersLiveData;
        //usersLiveData.setValue(TemporaryContentProvider.ITEMS);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //most release repo if singleton,...
    }
}





