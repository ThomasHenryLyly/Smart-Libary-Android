package teo.android.teoshop;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;
import teo.android.teoshop.Adapter.CartAdapter;
import teo.android.teoshop.Model.CartModel;
import teo.android.teoshop.Prevalent.Prevalent;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private RecyclerView rvCartList;
    private CartAdapter cartAdapter;
    private Button btnDatSach;
    private ArrayList<CartModel>cartList;
    private ArrayList<String> list;
    private DatabaseReference databaseReference;
    private Context context;
    View view;

    private String userEmail, mssv;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cart, container, false);
        rvCartList = view.findViewById(R.id.rvCartList);
        btnDatSach = view.findViewById(R.id.btnDatSach);
        Paper.init(getContext());
        userEmail = Paper.book().read(Prevalent.userEmailKey);
        mssv = userEmail.substring( 0, userEmail.indexOf("@"));
        showCart();


        btnDatSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference("BookingRequest");
                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.child("Request").child(mssv).exists())
                        {
                            datSach();
                            //Toast.makeText(getContext(), "hello dat sach", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), getResources().getString(R.string.bookedpleasecomingtolib), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        return view;
    }

    private void datSach() {
        final int[] dem = {0};
        final String userEmail = Paper.book().read(Prevalent.userEmailKey);
        DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userEmail).child("reserve");
        for (int i = 0; i < list.size(); i++)
        {
            //CartModel temp = cartList.get(i);
            //Log.e("tag", temp.getCode());
            String codeList = list.get(i);
            rootRef.child(codeList).setValue(list.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    dem[0] += 1;
                    if((dem[0]) == list.size())
                    {
                        updateBookingRequest();
                    }
                }
            });
        }
    }

    private void updateBookingRequest() {
        Log.e("tag", "mssv: "+mssv);
        final int[] dem = {0};
        DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference("BookingRequest").child("Request").child(mssv);
        rootRef.child("Studentid").setValue(mssv);
        rootRef.child("status").setValue("on");
        rootRef.child("Count").setValue(list.size());
        for (int i = 0; i < list.size(); i++)
        {
            final String codeList = list.get(i);
            rootRef.child("BookList").child(codeList).child("id").setValue(codeList).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    dem[0] += 1;
                    if((dem[0]) == list.size())
                    {
                        xoaCart();
                        Toast.makeText(getContext()
                                ,getResources().getString(R.string.successful)
                                ,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void xoaCart() {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userEmail).child("choose");
        RootRef.removeValue();
    }

    private void showCart() {
        rvCartList.setHasFixedSize(true);
        rvCartList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false));
        cartList = new ArrayList<>();
        list = new ArrayList<>();
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userEmail).child("choose");
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartList.clear();
                list.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    CartModel cartModel = postSnapshot.getValue(CartModel.class);
                    cartList.add(cartModel);
                    list.add(cartModel.getCode());
                }
                cartAdapter = new CartAdapter(getContext(), cartList);
                rvCartList.setAdapter(cartAdapter);
                int length = cartList.size();
                visiableButton(length);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void visiableButton(int length) {
        //Toast.makeText(getContext(), ""+length, Toast.LENGTH_SHORT).show();
        if(length == 0)
        {
            btnDatSach.setVisibility(View.GONE);
        }else{
            btnDatSach.setVisibility(View.VISIBLE);
        }
    }
}
