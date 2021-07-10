package hlv.app.mvvmsample.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import hlv.app.mvvmsample.App;
import hlv.app.mvvmsample.model.User;
import hlv.app.mvvmsample.util.faker.TemporaryContentProvider;

public class UserViewModel extends ViewModel {

    private MutableLiveData<ArrayList<User>> usersLiveData;
    private final MutableLiveData<Integer> pageLiveData; // TODO: 7/11/21 probably need to int page and not need to live data

    public UserViewModel() {
        usersLiveData = new MutableLiveData<>();
        pageLiveData = new MutableLiveData<>();
    }

    public void prepareData(int page) {
        pageLiveData.setValue(page);
        App.get().apiRepository.users(page);
//        usersLiveData.setValue(App.get().apiRepository.userMutableLiveData.getValue());
    }

    /**
     * when initiate class must be call this
     */
    public void prepareData() {
        prepareData(1);
    }

    /**
     * for loading offline temporary data call this
     */
    public void tempData() {
        usersLiveData.setValue(TemporaryContentProvider.ITEMS);
    }

    public MutableLiveData<ArrayList<User>> getUsersLiveData() {
        return usersLiveData;
    }

    public MutableLiveData<Integer> getPageLiveData() {
        return pageLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //most release repo if singleton,...
    }
}





