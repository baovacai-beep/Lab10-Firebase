package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

  private Context context;
  private List<Article> articleList;
  private FirebaseFirestore db = FirebaseFirestore.getInstance();

  public ArticleAdapter(Context context, List<Article> articleList) {
    this.context = context;
    this.articleList = articleList;
  }

  public void updateList(List<Article> newList) {
    this.articleList = newList;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.contact_list, parent, false);
    return new ArticleViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
    Article article = articleList.get(position);

    holder.tvTitle.setText(article.getTitle());
    holder.tvContent.setText(article.getContent());

    // Nạp ảnh qua Glide
    if (article.getImage_url() != null && !article.getImage_url().isEmpty()) {
      Glide.with(context)
              .load(article.getImage_url().replace("\\/", "/"))
              .placeholder(R.mipmap.ic_launcher)
              .error(R.mipmap.ic_launcher)
              .into(holder.ivThumbnail);
    } else {
      holder.ivThumbnail.setImageResource(R.mipmap.ic_launcher);
    }

    // Bắt sự kiện click vào dòng bài viết để mở màn hình chi tiết
    holder.itemView.setOnClickListener(v -> {
      Intent intent = new Intent(context, DetailArticleActivity.class);
      intent.putExtra("article", article);
      context.startActivity(intent);
    });

    // Xóa bài viết khỏi Firestore
    holder.btnDelete.setOnClickListener(v -> {
      if (article.getId() != null && !article.getId().isEmpty()) {
        db.collection("articles").document(article.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                  Toast.makeText(context, "Đã xóa bài viết: " + article.getTitle(), Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                  Toast.makeText(context, "Lỗi xóa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
      }
    });
  }

  @Override
  public int getItemCount() {
    return articleList != null ? articleList.size() : 0;
  }
}