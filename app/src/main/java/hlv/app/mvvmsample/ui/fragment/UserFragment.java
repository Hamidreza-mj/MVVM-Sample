package hlv.app.mvvmsample.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hlv.app.mvvmsample.adapter.UserAdapter;
import hlv.app.mvvmsample.databinding.FragmentUserBinding;
import hlv.app.mvvmsample.viewmodel.UserViewModel;

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserFragment() {
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getUsersLiveData().observe(this, list -> {
            adapter = new UserAdapter(getContext(), list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentUserBinding binding = FragmentUserBinding.inflate(LayoutInflater.from(getContext()), container, false);
        recyclerView = binding.recyclerView;

        return binding.getRoot();
    }

    public void reCreate() {
        this.getChildFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

}