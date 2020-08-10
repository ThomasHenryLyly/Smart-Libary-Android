package teo.android.teoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import teo.android.teoshop.Adapter.PopularAdapter;
import teo.android.teoshop.Model.Category;
import teo.android.teoshop.Model.Popular;

public class SearchActivity extends AppCompatActivity {

    private ImageView btnBack;
    private SearchView svSearch;

    private RecyclerView rvItem;
    private ArrayList<Popular> list;
    private PopularAdapter listAdapter;

    private String ds[] = {"CNC", "DDG", "DDT", "ENG", "KTC",
            "MTH", "NYH", "OTO", "POP", "TOA", "VLH", "XDH"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBack);
        svSearch = findViewById(R.id.svSearch);
        rvItem = findViewById(R.id.rvItem);
    }

    @Override
    protected void onStart() {
        super.onStart();

        rvItem.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerCategory = new GridLayoutManager(SearchActivity.this, 3);
        rvItem.setLayoutManager(layoutManagerCategory);
        list = new ArrayList<>();
        for (String a : ds)
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(a);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            try {
                                Popular popular = snapshot.getValue(Popular.class);
                                list.add(popular);
                            }catch(Exception e)
                            {

                            }

                        }
                        listAdapter = new PopularAdapter(SearchActivity.this, list);
                        listAdapter.setMyOntemPopularClickListener(new MyOnItemPopularClickListener() {
                            @Override
                            public void onClick(Popular popular) {
                                Intent intent = new Intent(SearchActivity.this, BookActivity.class);
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
                        rvItem.setAdapter(listAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        if (svSearch != null)
        {
            svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }

    }

    private void search(String newText) {
        rvItem.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerCategory = new GridLayoutManager(SearchActivity.this, 3);
        rvItem.setLayoutManager(layoutManagerCategory);
        ArrayList<Popular> myList = new ArrayList<>();

        for (Popular object : list)
        {
            try {
                if (object.getProduct_name().toLowerCase().contains(newText.toLowerCase()))
                {
                    myList.add(object);
                }
            }catch (Exception e)
            {

            }

        }

        listAdapter = new PopularAdapter(SearchActivity.this, myList);
        listAdapter.setMyOntemPopularClickListener(new MyOnItemPopularClickListener() {
            @Override
            public void onClick(Popular popular) {
                Intent intent = new Intent(SearchActivity.this, BookActivity.class);
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
        rvItem.setAdapter(listAdapter);

    }
}
