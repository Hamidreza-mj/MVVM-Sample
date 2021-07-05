package hlv.app.mvvmsample.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import hlv.app.mvvmsample.model.User;
import hlv.app.mvvmsample.util.faker.TemporaryContentProvider;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<User>> usersLiveData;

    public UserViewModel() {
        usersLiveData = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        usersLiveData.setValue(TemporaryContentProvider.ITEMS);
    }

    public MutableLiveData<ArrayList<User>> getUsersLiveData() {
        return usersLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //most release repo if singleton,...
    }
}





