package hlv.app.mvvmsample.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import hlv.app.mvvmsample.databinding.ItemUserBinding;
import hlv.app.mvvmsample.model.User;

public class UserAdapter extends ListAdapter<User, UserAdapter.ViewHolder> {

    private final Context context;

    public UserAdapter(Context context) {
        super(User.DiffUtil);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.bind(getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    public void submitList(ArrayList<User> list, boolean withPagination) {
        if (withPagination && getCurrentList() != null) {
            ArrayList<User> oldList = new ArrayList<>(getCurrentList());
            oldList.addAll(list);
            submitList(oldList);
            return;
        }

        submitList(list);
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
