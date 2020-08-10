package teo.android.teoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import teo.android.teoshop.Adapter.PopularAdapter;
import teo.android.teoshop.Model.Category;
import teo.android.teoshop.Model.Popular;

public class SachChuyenNganhActivity extends AppCompatActivity {
    private RecyclerView rvNamNhat, rvNamHai, rvNamBa, rvNamTu;
    private ArrayList<Popular>listNamNhat;
    private ArrayList<Popular>listNamHai;
    private ArrayList<Popular>listNamBa;
    private ArrayList<Popular>listNamTu;
    private PopularAdapter adapter1;
    private PopularAdapter adapter2;
    private PopularAdapter adapter3;
    private PopularAdapter adapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach_chuyen_nganh);

        addContorls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addContorls() {
        rvNamNhat = findViewById(R.id.rvNamNhat);
        rvNamHai = findViewById(R.id.rvNamHai);
        rvNamBa = findViewById(R.id.rvNamBa);
        rvNamTu = findViewById(R.id.rvNamTu);

        Intent intent = getIntent();
        String tenNganh = intent.getStringExtra("MAJOR");
        show(tenNganh);
    }

    private void show(String tenNganh) {
        String tenNganhChange = "";
        if(tenNganh.equals("Cơ điện tử"))
        {
            tenNganhChange = "co_dien_tu";
        }
        rvNamNhat.setHasFixedSize(true);
        rvNamNhat.setLayoutManager(new LinearLayoutManager(SachChuyenNganhActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        listNamNhat = new ArrayList<>();

        rvNamHai.setHasFixedSize(true);
        rvNamHai.setLayoutManager(new LinearLayoutManager(SachChuyenNganhActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        listNamHai = new ArrayList<>();

        rvNamBa.setHasFixedSize(true);
        rvNamBa.setLayoutManager(new LinearLayoutManager(SachChuyenNganhActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        listNamBa = new ArrayList<>();

        rvNamTu.setHasFixedSize(true);
        rvNamTu.setLayoutManager(new LinearLayoutManager(SachChuyenNganhActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false));
        listNamTu = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chuyennganh").child(tenNganhChange);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNamNhat.clear();
                listNamHai.clear();
                listNamBa.clear();
                listNamTu.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {

                    for(DataSnapshot postSnapshotBook:postSnapshot.getChildren())
                    {
                        Log.e("TAG", postSnapshotBook.getKey());
                        if(postSnapshotBook.getKey().equals("nam1"))
                        {
                            for(DataSnapshot postSnapshotBook2:postSnapshotBook.getChildren()) {
                                Log.e("TAG", postSnapshotBook2.toString());
                                Popular popular = postSnapshotBook2.getValue(Popular.class);
                                listNamNhat.add(popular);
                            }
                        }else if(postSnapshotBook.getKey().equals("nam2"))
                        {
                            for(DataSnapshot postSnapshotBook2:postSnapshotBook.getChildren()) {
                                Popular popular = postSnapshotBook2.getValue(Popular.class);
                                listNamHai.add(popular);
                            }
                        }else if(postSnapshotBook.getKey().equals("nam3"))
                        {
                            for(DataSnapshot postSnapshotBook2:postSnapshotBook.getChildren()) {
                                Popular popular = postSnapshotBook2.getValue(Popular.class);
                                listNamBa.add(popular);
                            }
                        }else if(postSnapshotBook.getKey().equals("nam4"))
                        {
                            for(DataSnapshot postSnapshotBook2:postSnapshotBook.getChildren()) {
                                Popular popular = postSnapshotBook2.getValue(Popular.class);
                                listNamTu.add(popular);
                            }
                        }
                    }

                }
                adapter1 = new PopularAdapter(SachChuyenNganhActivity.this, listNamNhat);
                adapter2 = new PopularAdapter(SachChuyenNganhActivity.this, listNamHai);
                adapter3 = new PopularAdapter(SachChuyenNganhActivity.this, listNamBa);
                adapter4 = new PopularAdapter(SachChuyenNganhActivity.this, listNamTu);

                adapter1.setMyOntemPopularClickListener(new MyOnItemPopularClickListener() {
                    @Override
                    public void onClick(Popular popular) {
                        Intent intent = new Intent(SachChuyenNganhActivity.this, BookActivity.class);
                        intent.putExtra("IMAGE", popular.getProduct_image());
                        intent.putExtra("IMAGE1", popular.getProduct_image1());
                        intent.putExtra("IMAGE2", popular.getProduct_image2());
                        intent.putExtra("IMAGE3", popular.getProduct_image3());
                        intent.putExtra("NameBook", popular.getProduct_name());
                        intent.putExtra("SoLuongBook", popular.getProduct_soluong());
                        intent.putExtra("TenTacGia", popular.getProduct_tacgia());
                        intent.putExtra("DiaChi", popular.getMap());
                        startActivity(intent);
                    }

                    @Override
                    public void onClick(Category category) {

                    }
                });
                adapter2.setMyOntemPopularClickListener(new MyOnItemPopularClickListener() {
                    @Override
                    public void onClick(Popular popular) {
                        Intent intent = new Intent(SachChuyenNganhActivity.this, BookActivity.class);
                        intent.putExtra("IMAGE", popular.getProduct_image());
                        intent.putExtra("IMAGE1", popular.getProduct_image1());
                        intent.putExtra("IMAGE2", popular.getProduct_image2());
                        intent.putExtra("IMAGE3", popular.getProduct_image3());
                        intent.putExtra("NameBook", popular.getProduct_name());
                        intent.putExtra("SoLuongBook", popular.getProduct_soluong());
                        intent.putExtra("TenTacGia", popular.getProduct_tacgia());
                        intent.putExtra("DiaChi", popular.getMap());
                        startActivity(intent);
                    }

                    @Override
                    public void onClick(Category category) {

                    }
                });
                adapter3.setMyOntemPopularClickListener(new MyOnItemPopularClickListener() {
                    @Override
                    public void onClick(Popular popular) {
                        Intent intent = new Intent(SachChuyenNganhActivity.this, BookActivity.class);
                        intent.putExtra("IMAGE", popular.getProduct_image());
                        intent.putExtra("IMAGE1", popular.getProduct_image1());
                        intent.putExtra("IMAGE2", popular.getProduct_image2());
                        intent.putExtra("IMAGE3", popular.getProduct_image3());
                        intent.putExtra("NameBook", popular.getProduct_name());
                        intent.putExtra("SoLuongBook", popular.getProduct_soluong());
                        intent.putExtra("TenTacGia", popular.getProduct_tacgia());
                        intent.putExtra("DiaChi", popular.getMap());
                        startActivity(intent);
                    }

                    @Override
                    public void onClick(Category category) {

                    }
                });
                adapter4.setMyOntemPopularClickListener(new MyOnItemPopularClickListener() {
                    @Override
                    public void onClick(Popular popular) {
                        Intent intent = new Intent(SachChuyenNganhActivity.this, BookActivity.class);
                        intent.putExtra("IMAGE", popular.getProduct_image());
                        intent.putExtra("IMAGE1", popular.getProduct_image1());
                        intent.putExtra("IMAGE2", popular.getProduct_image2());
                        intent.putExtra("IMAGE3", popular.getProduct_image3());
                        intent.putExtra("NameBook", popular.getProduct_name());
                        intent.putExtra("SoLuongBook", popular.getProduct_soluong());
                        intent.putExtra("TenTacGia", popular.getProduct_tacgia());
                        intent.putExtra("DiaChi", popular.getMap());
                        startActivity(intent);
                    }

                    @Override
                    public void onClick(Category category) {

                    }
                });
                rvNamNhat.setAdapter(adapter1);
                rvNamHai.setAdapter(adapter2);
                rvNamBa.setAdapter(adapter3);
                rvNamTu.setAdapter(adapter4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
