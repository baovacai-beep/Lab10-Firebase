package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

  private Context context;
  private List<User> userList;
  private FirebaseFirestore db = FirebaseFirestore.getInstance();

  public UserViewAdapter(Context context, List<User> userList) {
    this.context = context;
    this.userList = userList;
  }

  public void updateList(List<User> newList) {
    this.userList = newList;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.contact_list, parent, false);
    return new UserViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
    User user = userList.get(position);

    holder.tvUname.setText(user.getUname());
    holder.tvBio.setText(user.getShort_bio());
    holder.tvPassword.setText("Pass: " + user.getPassword());

    // Nạp ảnh đại diện Online qua Glide
    if (user.getUrl_profile() != null && !user.getUrl_profile().isEmpty()) {
      Glide.with(context)
              .load(user.getUrl_profile().replace("\\/", "/"))
              .placeholder(R.mipmap.ic_launcher)
              .error(R.mipmap.ic_launcher)
              .into(holder.ivAvatar);
    } else {
      holder.ivAvatar.setImageResource(R.mipmap.ic_launcher);
    }

    // Xóa document trên Cloud Firestore khi bấm nút thùng rác
    holder.btnDelete.setOnClickListener(v -> {
      if (user.getId() != null && !user.getId().isEmpty()) {
        db.collection("users").document(user.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                  Toast.makeText(context, "Đã xóa: " + user.getUname(), Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                  Toast.makeText(context, "Lỗi xóa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
      }
    });
  }

  @Override
  public int getItemCount() {
    return userList != null ? userList.size() : 0;
  }
}