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
import java.util.Collections;
import java.util.LinkedList;

import hlv.app.mvvmsample.databinding.ItemUserBinding;
import hlv.app.mvvmsample.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final Context context;
    public AsyncListDiffer<User> differ;

    public UserAdapter(Context context) {
        this.context = context;

        DiffUtil.ItemCallback<User> diffUtil = new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getName().equals(newItem.getName());
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.equals(newItem);
            }
        };

        differ = new AsyncListDiffer<>(this, diffUtil);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        if (differ.getCurrentList().get(position) != null)
            holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(ArrayList<User> list, boolean withPagination) {
        if (withPagination && differ.getCurrentList() != null && list != null) {
            LinkedList<User> oldList = new LinkedList<>(differ.getCurrentList());

            if (oldList.equals(list))
                return;

            oldList.addAll(list);
            differ.submitList(oldList);
            return;
        }

        differ.submitList(list);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtID, txtName, txtAge, txtGender;
        private final AppCompatImageView imgAvatar;

        public ViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());

            txtID = binding.txtID;
            txtName = binding.txtName;
            txtAge = binding.txtAge;
            txtGender = binding.txtGender;
            imgAvatar = binding.aImgAvatar;
        }

        private void bind(User user) {
            txtID.setText(getFormat("ID", user.getId()));
            txtName.setText(getFormat("Name", user.getName()));
            txtAge.setText(getFormat("Age", user.getAge()));
            txtGender.setText(getFormat("Gender", user.isMale() ? "Man" : "Woman"));

            Picasso.get().load(user.getImage()).into(imgAvatar);
        }

        private String getFormat(String headerTitle, Object value) {
            headerTitle = headerTitle + ": ";
            return MessageFormat.format(headerTitle + "{0}", value.toString());
        }
    }

}
