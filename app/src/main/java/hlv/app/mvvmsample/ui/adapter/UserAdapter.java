package hlv.app.mvvmsample.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;

import hlv.app.mvvmsample.R;
import hlv.app.mvvmsample.databinding.ItemFooterBinding;
import hlv.app.mvvmsample.databinding.ItemLoadingBinding;
import hlv.app.mvvmsample.databinding.ItemRetryBinding;
import hlv.app.mvvmsample.databinding.ItemUserBinding;
import hlv.app.mvvmsample.model.User;
import hlv.app.mvvmsample.util.Constants;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final Context context;
    public AsyncListDiffer<User> differ;

    private static final int LOADING_VIEW_TYPE = 0;
    private static final int NORMAL_VIEW_TYPE = 1;
    private static final int FOOTER_VIEW_TYPE = 2;
    private static final int RETRY_VIEW_TYPE = 3;
    private boolean isLoaderVisible = false;
    private boolean addFooterInLastPage = false;
    private boolean retryButtonShown = false;
    private boolean networkError = true;

    public UserAdapter(Context context) {
        this.context = context;

        DiffUtil.ItemCallback<User> diffUtil = new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return Objects.equals(oldItem.getUniqueID(), newItem.getUniqueID());
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return Objects.equals(oldItem, newItem);
            }
        };

        differ = new AsyncListDiffer<>(this, diffUtil);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;

        switch (viewType) {
            case LOADING_VIEW_TYPE:
                ItemLoadingBinding bindingLoading = ItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false);
                viewHolder = new ViewHolder(bindingLoading);
                break;

            case NORMAL_VIEW_TYPE:
                ItemUserBinding bindingNormal = ItemUserBinding.inflate(LayoutInflater.from(context), parent, false);
                viewHolder = new ViewHolder(bindingNormal);
                break;

            case FOOTER_VIEW_TYPE:
                ItemFooterBinding bindingFooter = ItemFooterBinding.inflate(LayoutInflater.from(context), parent, false);
                viewHolder = new ViewHolder(bindingFooter);
                break;

            case RETRY_VIEW_TYPE:
                ItemRetryBinding bindingRetry = ItemRetryBinding.inflate(LayoutInflater.from(context), parent, false);
                viewHolder = new ViewHolder(bindingRetry);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        if (differ.getCurrentList().get(position) != null)
            if (getItemViewType(position) == NORMAL_VIEW_TYPE)
                holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return (position == differ.getCurrentList().size() - 1) ? LOADING_VIEW_TYPE : NORMAL_VIEW_TYPE;
        } else if (addFooterInLastPage && position == differ.getCurrentList().size() - 1) {
            return FOOTER_VIEW_TYPE;
        } else if (retryButtonShown && position == differ.getCurrentList().size() - 1) {
            return RETRY_VIEW_TYPE;
        } else {
            return NORMAL_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(ArrayList<User> list, boolean withPagination) {
        if (withPagination && differ.getCurrentList() != null && list != null) {
            ArrayList<User> oldList = new ArrayList<>(differ.getCurrentList());

            //if two list is same do nothing
            if (oldList.equals(list))
                return;

            //after paginate must be remove loading from previous page
            if (oldList.size() > 0) {
                //removeLoading
                isLoaderVisible = false;
                int position = oldList.size() - 1;
                User item = oldList.get(position);

                if (item != null && Objects.equals(item.getUniqueID(), Constants.App.LOADING_UNIQUE_ID)) {
                    oldList.remove(oldList.get(position));
                }
            }

            oldList.addAll(list);

            //add loading item to end of list
            if (oldList.size() > 0) {
                //addLoading
                isLoaderVisible = true;

                User loadingItem = new User();
                loadingItem.setUniqueID(Constants.App.LOADING_UNIQUE_ID); //need for DiffUtil unique property
                oldList.add(loadingItem);
            }

            //submit edited list to differ
            differ.submitList(oldList);
            return;
        }

        differ.submitList(list);
    }

    /**
     * remove loading if is in last page (current_page == total_page)
     * must be call with some delay
     */
    public void removeLoadingLastPage() {
        isLoaderVisible = false;
        new Handler().postDelayed(() -> {
            ArrayList<User> users = new ArrayList<>(differ.getCurrentList());
            int position = users.size() - 1;
            User item = users.get(position);

            if (item != null && Objects.equals(item.getUniqueID(), Constants.App.LOADING_UNIQUE_ID)) {
                users.remove(item);
                differ.submitList(users);
            }
        }, 100);
    }

    /**
     * add footer in the end of items of last page (isLastPage && position == list.size -1)
     * this must be call with more delay than {@link #removeLoadingLastPage()}
     */
    public void addFooterInLastPage() {
        addFooterInLastPage = true;
        new Handler().postDelayed(() -> {
            ArrayList<User> currentList = new ArrayList<>(differ.getCurrentList());
            User footerItem = new User();
            footerItem.setUniqueID(Constants.App.FOOTER_UNIQUE_ID);
            currentList.add(footerItem);
            differ.submitList(currentList);
        }, 200);
    }

    public void addRetryButton() {
        retryButtonShown = true;
        new Handler().postDelayed(() -> {
            ArrayList<User> currentList = new ArrayList<>(differ.getCurrentList());
            User retryItem = new User();
            retryItem.setUniqueID(Constants.App.RETRY_UNIQUE_ID);
            currentList.add(retryItem);
            differ.submitList(currentList);
        }, 300);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtID, txtName, txtAge, txtGender;
        private AppCompatImageView imgAvatar;
        private AVLoadingIndicatorView aviLoader;

        public ViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());

            txtID = binding.txtID;
            txtName = binding.txtName;
            txtAge = binding.txtAge;
            txtGender = binding.txtGender;
            imgAvatar = binding.aImgAvatar;
        }

        public ViewHolder(@NonNull ItemLoadingBinding binding) {
            super(binding.getRoot());

            aviLoader = binding.avi;
        }

        public ViewHolder(@NonNull ItemFooterBinding binding) {
            super(binding.getRoot());
        }

        public ViewHolder(ItemRetryBinding bindingRetry) {
            super(bindingRetry.getRoot());
        }

        private void bind(User user) {
            if (txtID != null && user.getName() != null) {
                txtID.setText(getFormat("ID", user.getId()));
                txtName.setText(getFormat("Name", user.getName()));
                txtAge.setText(getFormat("Age", user.getAge()));
                txtGender.setText(getFormat("Gender", user.isMale() ? "Man" : "Woman"));

                Picasso.get().load(user.getImage()).into(imgAvatar);
            } else if (aviLoader != null && networkError && Objects.equals(user.getUniqueID(), Constants.App.LOADING_UNIQUE_ID)) {
                aviLoader.setVisibility(View.GONE);
            }
        }

        private String getFormat(String headerTitle, Object value) {
            headerTitle = headerTitle + ": ";
            return MessageFormat.format(headerTitle + "{0}", String.valueOf(value));
        }
    }

}
