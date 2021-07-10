package hlv.app.mvvmsample.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hlv.app.mvvmsample.App;
import hlv.app.mvvmsample.repo.remote.ResponseEvent;
import hlv.app.mvvmsample.ui.adapter.UserAdapter;
import hlv.app.mvvmsample.databinding.FragmentUserBinding;
import hlv.app.mvvmsample.viewmodel.UserViewModel;

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private boolean isLoading = false;

    private UserViewModel viewModel;

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
    }

    private void handleRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new UserAdapter(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        handlePaging(layoutManager);
    }

    private void handlePaging(LinearLayoutManager layoutManager) {
        RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (!isLoading/* && !isLastPage*/) {
                    if ((5 + lastVisibleItemPosition) >= totalItemCount
                            && lastVisibleItemPosition >= 0
                            && totalItemCount >= 1) { //prePage
                        loadNextPageItems();
                    }
                }
            }
        };

        /*
         super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= 1) { //prePage
                loadNextPageItems();
            }
        }

         */

        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    }

    private void loadNextPageItems() {
        viewModel.prepareData(viewModel.getPageLiveData().getValue() + 1);

//        if (viewModel.getPageLiveData().getValue() / 10 == 0)
//            isLoading = true; ////// TODO: 7/11/21 remove !!
    }

    private void handleObserver() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.prepareData();

        App.get().apiRepository.userMutableLiveData.observe(getViewLifecycleOwner(), list -> {
            adapter.submitList(list, true);
        });
    }

    public void reCreate() {
        this.getChildFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}