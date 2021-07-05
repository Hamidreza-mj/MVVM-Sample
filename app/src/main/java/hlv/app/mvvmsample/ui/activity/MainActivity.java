package hlv.app.mvvmsample.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import hlv.app.mvvmsample.databinding.ActivityMainBinding;
import hlv.app.mvvmsample.ui.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MaterialButton btnList;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        handleActions();
    }

    private void initViews() {
        btnList = binding.mBtnLiveShowList;
    }

    private void handleActions() {
        btnList.setOnClickListener(v -> {

            if (userFragment == null)
                userFragment = UserFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(binding.frame.getId(), userFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public void onBackPressed() {
        if (userFragment != null) {
//            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().remove(userFragment);
            userFragment = null;
            return;
        }

        super.onBackPressed();



//        int count = getFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getFragmentManager().popBackStack();
//        }
    }
}