package teo.android.teoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;
import teo.android.teoshop.Adapter.PopularAdapter;
import teo.android.teoshop.Model.Category;
import teo.android.teoshop.Model.Popular;
import teo.android.teoshop.Prevalent.Prevalent;

public class BookListActivity extends AppCompatActivity {
    private TextView txtTenNganh;
    private RecyclerView rvListBook;
    private ArrayList<Popular>listBook;
    private PopularAdapter adapterBook;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        rvListBook = findViewById(R.id.rvListBook);
        txtTenNganh = findViewById(R.id.txtTenNganh);
        Paper.init(this);
        Intent intent = getIntent();
        String catname = intent.getStringExtra("CATNAME");
        loadingbar = new ProgressDialog(this);
        loadingbar.setMessage(getResources().getString(R.string.waitting));
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
        HienThiSach(catname);
    }

    private void HienThiSach(String catname) {
        String catnamechange = "";
        txtTenNganh.setText(catname);
        rvListBook.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManagerCategory = new GridLayoutManager(this, 3);
        rvListBook.setLayoutManager(layoutManagerCategory);

        listBook = new ArrayList<>();
        if(catname.equals("Electronics"))
        {
            catnamechange = "DDT";
        }else if(catname.equals("English"))
        {
            catnamechange = "ENG";
        }else if(catname.equals("Economy"))
        {
            catnamechange = "KTC";
        }else if(catname.equals("Environment"))
        {
            catnamechange = "MTH";
        }else if(catname.equals("Math"))
        {
            catnamechange = "TOA";
        }else if(catname.equals("Medicine"))
        {
            catnamechange = "NYH";
        }else if(catname.equals("Construction"))
        {
            catnamechange = "XDH";
        }else if(catname.equals("Car"))
        {
            catnamechange = "OTO";
        }
        Log.e("TAG", "catname: "+catname);
        Log.e("TAG", "catnamechange: "+catnamechange);
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(catnamechange);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listBook.clear();
                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                    {
                        if(postSnapshot.getKey().equals("name"))
                        {

                        }else{
                            Popular popular = postSnapshot.getValue(Popular.class);
                            listBook.add(popular);
                        }

                    }
                    adapterBook = new PopularAdapter(BookListActivity.this, listBook);
                    adapterBook.setMyOntemPopularClickListener(new MyOnItemPopularClickListener() {
                        @Override
                        public void onClick(Popular popular) {
                            Intent intent = new Intent(BookListActivity.this, BookActivity.class);
                            intent.putExtra("IMAGE", popular.getProduct_image());
                            intent.putExtra("IMAGE1", popular.getProduct_image1());
                            intent.putExtra("IMAGE2", popular.getProduct_image2());
                            intent.putExtra("IMAGE3", popular.getProduct_image3());
                            intent.putExtra("NameBook", popular.getProduct_name());
                            intent.putExtra("SoLuongBook", popular.getProduct_soluong());
                            intent.putExtra("TenTacGia", popular.getProduct_tacgia());
                            intent.putExtra("DiaChi", popular.getMap());
                            intent.putExtra("Code", popular.getCode());
                            startActivity(intent);
                        }

                        @Override
                        public void onClick(Category category) {

                        }
                    });
                    rvListBook.setAdapter(adapterBook);
                    loadingbar.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){

        }

    }
}
