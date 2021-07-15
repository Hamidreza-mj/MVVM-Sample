package hlv.app.mvvmsample.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hlv.app.mvvmsample.databinding.FragmentUserBinding;
import hlv.app.mvvmsample.model.User;
import hlv.app.mvvmsample.repo.remote.event.ResponseEvent;
import hlv.app.mvvmsample.repo.remote.event.Status;
import hlv.app.mvvmsample.ui.adapter.UserAdapter;
import hlv.app.mvvmsample.ui.component.PagingListener;
import hlv.app.mvvmsample.viewmodel.UserViewModel;

public class UserFragment extends Fragment {

    private UserViewModel viewModel;
    private ResponseEvent<ArrayList<User>> responseEvent;

    private UserAdapter adapter;

    private RecyclerView recyclerView;
    private TextView txtStatus;

    private boolean isLoading = false;
    private int totalPages = 10;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentUserBinding binding = FragmentUserBinding.inflate(LayoutInflater.from(getContext()), container, false);

        initViews(binding);

        handleRecyclerView();

        handleObserver();

        return binding.getRoot();
    }

    private void initViews(FragmentUserBinding binding) {
        recyclerView = binding.recyclerView;
        txtStatus = binding.txtStatus;
    }

    private void handleRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        getActivity().runOnUiThread(() -> {
            adapter = new UserAdapter(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        });
        handlePaging(layoutManager);
    }

    private void handlePaging(LinearLayoutManager layoutManager) {
        recyclerView.addOnScrollListener(new PagingListener(layoutManager) {
            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public boolean isLastPage() {
                return responseEvent.isLastPage();
            }

            @Override
            public int getTotalPage() {
                return totalPages;
            }

            @Override
            public void loadNextPageItems() {
                loadNextPage();
            }
        });
    }

    private void loadNextPage() {
        viewModel.fetchData(responseEvent.getCurrentPage() + 1);
    }

    private void handleObserver() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.fetchData();

        //without livedata
        //lytFooter.setVisibility(viewModel.footerVisibility());

        viewModel.getUsersLiveData().observe(getViewLifecycleOwner(), response -> {
            responseEvent = response;
            txtStatus.setText(response.getStatus().toString());

            switch (response.getStatus()) {
                case LOADING:
                    if (response.getCurrentPage() == 1) {

                    }
                    break;

                case SUCCESS:
                    adapter.submitList(response.getResult(), true);
                    isLoading = response.getStatus() == Status.LOADING;
                    totalPages = response.getTotalPages();

                    //with livedata
                    //lytFooter.setVisibility(viewModel.footerVisibility());

                    if (response.isLastPage())
                        adapter.removeLoadingLastPage();

                    break;

                case FAILURE:
                    if (response.getMessage() != null && !response.getMessage().isEmpty()) {

                    }
                    break;

                case NETWORK_ERROR:

                    break;
            }
        });
    }

    public void reCreate() {
        this.getChildFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getUsersLiveData().removeObservers(getViewLifecycleOwner());
    }

}