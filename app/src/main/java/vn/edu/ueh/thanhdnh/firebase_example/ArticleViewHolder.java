package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
  public ImageView ivThumbnail;
  public TextView tvTitle, tvContent;
  public ImageButton btnDelete;

  public ArticleViewHolder(@NonNull View itemView) {
    super(itemView);
    ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
    tvTitle = itemView.findViewById(R.id.tvTitle);
    tvContent = itemView.findViewById(R.id.tvContent);
    btnDelete = itemView.findViewById(R.id.btnDelete);
  }
}