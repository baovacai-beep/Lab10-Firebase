package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailArticleActivity extends AppCompatActivity {

    private ImageView ivDetailImage;
    private TextView tvDetailTitle, tvDetailContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chi Tiết Bài Viết");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ivDetailImage = findViewById(R.id.ivDetailImage);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailContent = findViewById(R.id.tvDetailContent);

        // Nhận dữ liệu truyền qua từ Adapter
        Article article = (Article) getIntent().getSerializableExtra("article");

        if (article != null) {
            tvDetailTitle.setText(article.getTitle());
            tvDetailContent.setText(article.getContent());

            if (article.getImage_url() != null && !article.getImage_url().isEmpty()) {
                Glide.with(this)
                        .load(article.getImage_url().replace("\\/", "/"))
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(ivDetailImage);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}