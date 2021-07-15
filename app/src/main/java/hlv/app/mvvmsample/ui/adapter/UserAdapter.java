package hlv.app.mvvmsample.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import hlv.app.mvvmsample.databinding.ItemLoadingBinding;
import hlv.app.mvvmsample.databinding.ItemUserBinding;
import hlv.app.mvvmsample.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final Context context;
    public AsyncListDiffer<User> differ;

    private static final int LOADING_VIEW_TYPE = 0;
    private static final int NORMAL_VIEW_TYPE = 1;
    private boolean isLoaderVisible = false;

    public UserAdapter(Context context) {
        this.context = context;

        DiffUtil.ItemCallback<User> diffUtil = new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return Objects.equals(oldItem.getName(), newItem.getName());
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
            if (position == differ.getCurrentList().size() - 1)
                return LOADING_VIEW_TYPE;
            else
                return NORMAL_VIEW_TYPE;
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

                if (item != null && Objects.equals(item.getName(), "Loading-Item")) {
                    oldList.remove(oldList.get(position));
                }
            }

            oldList.addAll(list);

            //add loading item to end of list
            if (oldList.size() > 0) {
                //addLoading
                isLoaderVisible = true;

                User loadingItem = new User();
                loadingItem.setName("Loading-Item"); //need for DiffUtil unique property
                oldList.add(loadingItem);
            }

            //submit edited list to differ
            differ.submitList(oldList);
            return;
        }

        differ.submitList(list);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        ArrayList<User> users = new ArrayList<>(differ.getCurrentList());
        int position = users.size() - 1;
        User item = users.get(position);

        if (item != null && Objects.equals(item.getName(), "Loading-Item")) {
            users.remove(users.get(position));
            users.clear();
            differ.submitList(users);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtID, txtName, txtAge, txtGender;
        private AppCompatImageView imgAvatar;

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
        }

        private void bind(User user) {
            if (txtID != null && user.getImage() != null) {
                txtID.setText(getFormat("ID", user.getId()));
                txtName.setText(getFormat("Name", user.getName()));
                txtAge.setText(getFormat("Age", user.getAge()));
                txtGender.setText(getFormat("Gender", user.isMale() ? "Man" : "Woman"));

                Picasso.get().load(user.getImage()).into(imgAvatar);
            }
        }

        private String getFormat(String headerTitle, Object value) {
            headerTitle = headerTitle + ": ";
            return MessageFormat.format(headerTitle + "{0}", String.valueOf(value));
        }
    }

}
