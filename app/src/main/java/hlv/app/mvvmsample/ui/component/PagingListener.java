package hlv.app.mvvmsample.ui.component;

import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hlv.app.mvvmsample.model.User;
import hlv.app.mvvmsample.util.Constants;

public abstract class PagingListener extends RecyclerView.OnScrollListener {

    private final LinearLayoutManager layoutManager;
    private boolean isScrolling = false;

    public PagingListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            isScrolling = true;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int currentVisibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int scrolledOutItems = layoutManager.findFirstVisibleItemPosition();

        boolean isNotLoadingAndNotLastPage = !isLoading() && !isLastPage();
        boolean isAtLastItem = scrolledOutItems + currentVisibleItemCount >= totalItemCount;
        boolean isNotAtBeginning = scrolledOutItems >= 0;
        boolean isTotalMoreThanVisible = totalItemCount >= Constants.Networking.PER_PAGE;

        boolean shouldPaginate = isNotLoadingAndNotLastPage &&
                isAtLastItem &&
                isNotAtBeginning &&
                isTotalMoreThanVisible &&
                isScrolling;

        if (shouldPaginate) {
            loadNextPageItems();
            isScrolling = false;
        }
    }

    public abstract boolean isLoading();

    public abstract int getTotalPage();

    public abstract boolean isLastPage();

    public abstract void loadNextPageItems();

}

/*    final boolean[] isScrolling = {false};

        RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling[0] = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (isScrolling[0] && !isLoading  && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= Constants.Networking.PER_PAGE) { //prePage
                        loadNextPage();
                        isScrolling[0] = false;
                    }
                }
            }
        };*/


