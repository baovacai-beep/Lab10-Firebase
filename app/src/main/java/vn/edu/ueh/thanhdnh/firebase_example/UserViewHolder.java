package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {
  public ImageView ivAvatar;
  public TextView tvUname, tvBio, tvPassword;
  public ImageButton btnDelete;

  public UserViewHolder(@NonNull View itemView) {
    super(itemView);
    ivAvatar = itemView.findViewById(R.id.ivAvatar);
    tvUname = itemView.findViewById(R.id.tvUname);
    tvBio = itemView.findViewById(R.id.tvBio);
    tvPassword = itemView.findViewById(R.id.tvPassword);
    btnDelete = itemView.findViewById(R.id.btnDelete);
  }
}