package teo.android.teoshop;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import teo.android.teoshop.Adapter.CategoryAdapter;
import teo.android.teoshop.Adapter.PopularAdapter;
import teo.android.teoshop.Model.Category;
import teo.android.teoshop.Model.Popular;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ViewFlipper imgBanner;

    private ImageView btnSearch;
    private RelativeLayout rlt08;

    private RecyclerView rvList, rvCategoryList;
    private PopularAdapter popularAdapter;
    private CategoryAdapter categoryAdapter;

    private DatabaseReference databaseReference;
    private ArrayList<Popular> popularList;
    private ArrayList<Category> categoryList;

    int sliders[] = {R.drawable.spkt1,
            R.drawable.spkt2,
            R.drawable.spkt3,
            R.drawable.spkt4,
            R.drawable.spkt6};

    View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        imgBanner = view.findViewById(R.id.imgBanner);
        btnSearch = view.findViewById(R.id.btnSearch);

        rlt08 = view.findViewById(R.id.rlt08);

        rvCategoryList = view.findViewById(R.id.rvCategoryList);
        rvList = view.findViewById(R.id.rvList);
        for(int slide:sliders)
        {
            bannerFlipter(slide);
        }

        rlt08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        showPopularProduct();
        showCategory();

        return view;
    }

    private void showCategory() {
        //rvCategoryList = findViewById(R.id.rvCategoryList);
        rvCategoryList.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManagerCategory = new GridLayoutManager(getContext(), 2);
        rvCategoryList.setLayoutManager(layoutManagerCategory);

        categoryList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Category category = postSnapshot.getValue(Category.class);
                    categoryList.add(category);
                }
                categoryAdapter = new CategoryAdapter(getContext(), categoryList);
                categoryAdapter.setMyOntemPopularClickListener(new MyOnItemPopularClickListener() {
                    @Override
                    public void onClick(Popular popular) {

                    }

                    @Override
                    public void onClick(Category category) {
                        Intent intent = new Intent(getContext(), BookListActivity.class);
                        intent.putExtra("CATNAME", category.getCatname());
                        startActivity(intent);
                    }
                });
                rvCategoryList.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void showPopularProduct() {
        //rvList = findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false));
        popularList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("POP");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    if(postSnapshot.getKey().equals("name"))
                    {

                    }else{
                        Popular popular = postSnapshot.getValue(Popular.class);
                        popularList.add(popular);
                    }

                }
                popularAdapter = new PopularAdapter(getContext(), popularList);
                popularAdapter.setMyOntemPopularClickListener(new MyOnItemPopularClickListener() {
                    @Override
                    public void onClick(Popular popular) {
                        Intent intent = new Intent(getContext(), BookActivity.class);
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

                rvList.setAdapter(popularAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void bannerFlipter(int image)
    {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(image);
        imgBanner.addView(imageView);
        imgBanner.setFlipInterval(5000);
        imgBanner.setAutoStart(true);
        imgBanner.setInAnimation(getContext(), android.R.anim.fade_in);
        imgBanner.setOutAnimation(getContext(), android.R.anim.fade_out);
    }


}
