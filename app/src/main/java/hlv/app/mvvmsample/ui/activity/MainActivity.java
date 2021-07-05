package hlv.app.mvvmsample.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.button.MaterialButton;

import hlv.app.mvvmsample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MaterialButton btnList;
    private FrameLayout frame;

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
        frame = binding.frame;
    }

    private void handleActions() {
        btnList.setOnClickListener(v -> {

        });
    }
}