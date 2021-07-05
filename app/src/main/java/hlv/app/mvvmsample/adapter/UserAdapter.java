package hlv.app.mvvmsample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;

import hlv.app.mvvmsample.databinding.ItemUserBinding;
import hlv.app.mvvmsample.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<User> list;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtID, txtName, txtAge, txtGender;

        public ViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());

            txtID = binding.txtID;
            txtName = binding.txtName;
            txtAge = binding.txtAge;
            txtGender = binding.txtGender;
        }

        private void bind(User user) {
            txtID.setText(getFormat("ID", user.getId()));
            txtName.setText(getFormat("Name", user.getName()));
            txtAge.setText(getFormat("Age", user.getAge()));
            txtGender.setText(getFormat("Gender", user.isMale() ? "Man" : "Woman"));
        }

        private String getFormat(String headerTitle, Object value) {
            headerTitle = headerTitle + ": ";
            return MessageFormat.format(headerTitle + "{0}", value.toString());
        }
    }

}
