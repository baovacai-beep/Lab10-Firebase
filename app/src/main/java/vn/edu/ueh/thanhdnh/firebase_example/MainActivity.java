package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private FirebaseFirestore db;
  private Button btAdd, btShow, btQuickSample;
  private EditText etTitle, etContent, etImageUrl;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Khởi tạo Firebase
    FirebaseApp.initializeApp(this);
    db = FirebaseFirestore.getInstance();

    // Ánh xạ View
    btAdd = findViewById(R.id.btAdd);
    btShow = findViewById(R.id.btShow);
    btQuickSample = findViewById(R.id.btQuickSample);
    etTitle = findViewById(R.id.etTitle);
    etContent = findViewById(R.id.etContent);
    etImageUrl = findViewById(R.id.etImageUrl);

    btAdd.setOnClickListener(this);
    btShow.setOnClickListener(this);
    btQuickSample.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.btAdd) {
      addArticleToFirestore();
    } else if (id == R.id.btQuickSample) {
      addSampleArticles();
    } else if (id == R.id.btShow) {
      Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
      startActivity(intent);
    }
  }

  private void addArticleToFirestore() {
    String title = etTitle.getText().toString().trim();
    String content = etContent.getText().toString().trim();
    String imageUrl = etImageUrl.getText().toString().trim();

    if (title.isEmpty() || content.isEmpty()) {
      Toast.makeText(this, "Vui lòng nhập Tiêu đề và Nội dung bài viết!", Toast.LENGTH_SHORT).show();
      return;
    }

    if (imageUrl.isEmpty()) {
      // Ảnh mặc định nếu người dùng không nhập link
      imageUrl = "https://images.unsplash.com/photo-1505373877841-8d25f7d46678";
    }

    Article article = new Article("", title, content, imageUrl);

    // Đẩy lên Collection "articles" của Firestore
    db.collection("articles")
            .add(article)
            .addOnSuccessListener(documentReference -> {
              // Cập nhật lại ID tài liệu vừa sinh vào trường id
              documentReference.update("id", documentReference.getId());
              Toast.makeText(MainActivity.this, "Thêm bài viết thành công!", Toast.LENGTH_SHORT).show();
              etTitle.setText("");
              etContent.setText("");
              etImageUrl.setText("");
            })
            .addOnFailureListener(e -> {
              Toast.makeText(MainActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
  }

  // Nạp nhanh 3 bài viết mẫu từ link ảnh Unsplash để test
  private void addSampleArticles() {
    Article a1 = new Article("1", "Trí Tuệ Nhân Tạo Năm 2026",
            "Những bước tiến vượt bậc của các mô hình AI thế hệ mới trên nền tảng di động.",
            "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe");
    Article a2 = new Article("2", "Lập Trình Android Hiện Đại",
            "Kết hợp Cloud Firestore để đồng bộ hóa dữ liệu thời gian thực không cần backend server.",
            "https://images.unsplash.com/photo-1555066931-4365d14bab8c");
    Article a3 = new Article("3", "Xu Hướng Thiết Kế UI/UX",
            "Tối ưu trải nghiệm cảm ứng với Material 3 và bố cục đa màn hình responsive.",
            "https://images.unsplash.com/photo-1507238691740-187a5b1d37b8");

    db.collection("articles").add(a1);
    db.collection("articles").add(a2);
    db.collection("articles").add(a3);

    Toast.makeText(this, "Đã nạp 3 bài viết mẫu lên Firestore!", Toast.LENGTH_SHORT).show();
  }
}