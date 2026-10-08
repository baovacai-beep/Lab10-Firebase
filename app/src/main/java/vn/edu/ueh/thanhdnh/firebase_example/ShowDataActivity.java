package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserViewAdapter adapter;
    private List<User> userList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Danh sách Người Dùng (Realtime)");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserViewAdapter(this, userList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // LẮNG NGHE REALTIME (Slide 92): Tự động cập nhật khi thêm/sửa/xóa trên Firebase
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(ShowDataActivity.this, "Lỗi lắng nghe: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (snapshots != null) {
                    userList.clear();
                    for (QueryDocumentSnapshot q : snapshots) {
                        User user = q.toObject(User.class);
                        user.setId(q.getId()); // Gán Document ID để phục vụ thao tác xóa
                        userList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}