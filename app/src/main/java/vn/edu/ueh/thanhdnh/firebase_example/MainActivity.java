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
  private EditText etUname, etPassword, etUrlProfile, etShortBio;

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
    etUname = findViewById(R.id.etUname);
    etPassword = findViewById(R.id.etPassword);
    etUrlProfile = findViewById(R.id.etUrlProfile);
    etShortBio = findViewById(R.id.etShortBio);

    btAdd.setOnClickListener(this);
    btShow.setOnClickListener(this);
    btQuickSample.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.btAdd) {
      addUserToFirestore();
    } else if (id == R.id.btQuickSample) {
      addSampleUsers();
    } else if (id == R.id.btShow) {
      Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
      startActivity(intent);
    }
  }

  private void addUserToFirestore() {
    String uname = etUname.getText().toString().trim();
    String pass = etPassword.getText().toString().trim();
    String url = etUrlProfile.getText().toString().trim();
    String bio = etShortBio.getText().toString().trim();

    if (uname.isEmpty() || pass.isEmpty()) {
      Toast.makeText(this, "Vui lòng nhập Username và Password!", Toast.LENGTH_SHORT).show();
      return;
    }

    if (url.isEmpty()) {
      // URL mặc định nếu không nhập
      url = "https://images.unsplash.com/photo-1550258987-190a2d41a8ba";
    }

    User user = new User("", uname, pass, url, bio);

    // Đẩy lên Collection "users" của Cloud Firestore (theo Slide 90)
    db.collection("users")
            .add(user)
            .addOnSuccessListener(documentReference -> {
              // Cập nhật lại ID bằng mã tự sinh của Firestore
              documentReference.update("id", documentReference.getId());
              Toast.makeText(MainActivity.this, "Thêm User thành công!", Toast.LENGTH_SHORT).show();
              etUname.setText("");
              etPassword.setText("");
              etUrlProfile.setText("");
              etShortBio.setText("");
            })
            .addOnFailureListener(e -> {
              Toast.makeText(MainActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
  }

  // Nạp nhanh 3 User mẫu lấy theo link ảnh Unsplash của Lab 8 để test
  private void addSampleUsers() {
    User u1 = new User("1", "Dứa Nhiệt Đới", "123456",
            "https://images.unsplash.com/photo-1550258987-190a2d41a8ba", "Hương vị ngọt ngào nhiệt đới.");
    User u2 = new User("2", "Cherry Đỏ", "abcdef",
            "https://images.unsplash.com/photo-1528825871115-3581a5387919", "Trái cây giàu vitamin và tươi mát.");
    User u3 = new User("3", "Bơ Sáp", "999888",
            "https://images.unsplash.com/photo-1523049673857-eb18f1d7b578", "Nguồn dinh dưỡng tự nhiên dồi dào.");

    db.collection("users").add(u1);
    db.collection("users").add(u2);
    db.collection("users").add(u3);

    Toast.makeText(this, "Đã nạp 3 User mẫu lên Cloud Firestore!", Toast.LENGTH_SHORT).show();
  }
}