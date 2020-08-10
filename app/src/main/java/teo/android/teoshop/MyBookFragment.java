package teo.android.teoshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import teo.android.teoshop.Adapter.BorrowAdapter;
import teo.android.teoshop.Model.BookModel;
import teo.android.teoshop.Model.BorrowBookPhuModel;
import teo.android.teoshop.Model.BorrowModel;
import teo.android.teoshop.Prevalent.Prevalent;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookFragment extends Fragment {
    private RecyclerView rvBorrowList;

    private ArrayList<BorrowModel> borrowList = new ArrayList<>();;
    BorrowAdapter borrowAdapter;

    private List<String> idList = new ArrayList<>();
    private List<String> mfgList = new ArrayList<>();;
    private List<String> expList = new ArrayList<>();;


    private String tableDbname = "Users";

    private String autorS = "", imageS = "", nameBookS = "";
    private String head, tail;


    public MyBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_book, container, false);
        rvBorrowList = view.findViewById(R.id.rvBorrowList);
        Paper.init(getContext());
        showBorrowBook();
        return view;
    }

    public void showInfoBook()
    {
        rvBorrowList.setHasFixedSize(true);
        rvBorrowList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false));
        int i = 0;

        borrowList.clear();
        for (String s : idList){
            final String mfg = mfgList.get(i);
            final String id = s;
            final String exp = expList.get(i);

            String a = s.substring(5, 6);
            if (a.equals("i"))
            {
                head = s.substring(0, 3);
                tail = s.substring(3, 5);
            }else{
                head = s.substring(0, 3);
                tail = s.substring(3, 6);
            }



            Log.e("TAG", s.substring(0, 3));
            Log.e("TAG", s.substring(3, 5));
            Log.e("TAG", mfg);
            Log.e("TAG", exp);

            DatabaseReference reference  = FirebaseDatabase.getInstance().getReference(head).child(tail);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("TAG", dataSnapshot.toString());
                    BorrowModel borrowModel = new BorrowModel();
                    Log.e("TAG", dataSnapshot.child("product_image").getValue().toString());
                    borrowModel.setProduct_image(dataSnapshot.child("product_image").getValue().toString());
                    borrowModel.setProduct_name(dataSnapshot.child("product_name").getValue().toString());
                    borrowModel.setProduct_tacgia(dataSnapshot.child("product_tacgia").getValue().toString());
                    borrowModel.setMfg(mfg);
                    borrowModel.setExp(exp);
                    borrowModel.setId(id);
                    //Log.e("TAG", borrowModel.getProduct_name());
                    borrowList.add(borrowModel);
                    borrowAdapter = new BorrowAdapter(getContext(), borrowList);
                    rvBorrowList.setAdapter(borrowAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            i++;
        }
    }

    private void showBorrowBook() {
        String email = Paper.book().read(Prevalent.userEmailKey);

        //borrowList = new ArrayList<>();

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference(tableDbname)
                .child(email).child("borrow").child("sach");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                borrowList.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Log.e("tag", String.valueOf(postSnapshot));
                    BorrowModel borrowModel = postSnapshot.getValue(BorrowModel.class);
                    idList.add(borrowModel.getId());
                    mfgList.add(borrowModel.getMfg());
                    expList.add(borrowModel.getExp());
                    /*
                    String a = borrowModel.getId();
                    String b = borrowModel.getMfg();
                    String c = borrowModel.getExp();
                    Log.e("a", borrowModel.getId());

                     */
                    //Log.e("nameBookS", (String) Paper.book().read(Prevalent.namebook));
                    //borrowList.add(borrowModel);
                }
                showInfoBook();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showBookId()
    {
        try {
            for(String id:idList)
            {
                Log.e("id", id);
            }
        }catch (Exception e)
        {

        }
    }

    private void getFullInfor(String id, String mfg, String exp) {
        String email = Paper.book().read(Prevalent.userEmailKey);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("OTO")
                .child("H1");
        //autorS = String.valueOf((reference.child("product_tacgia").getRef()));
        //Log.e("tag", autorS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //autorS = (String) dataSnapshot.child("product_tacgia").getValue();
                //Log.e("tag", autorS);
                BorrowBookPhuModel borrowModel = dataSnapshot.getValue(BorrowBookPhuModel.class);
                autorS = borrowModel.getProduct_tacgia();
                imageS = borrowModel.getProduct_image();
                nameBookS = borrowModel.getProduct_image();
                Paper.book().write(Prevalent.author, autorS);
                Paper.book().write(Prevalent.imagebook, imageS);
                Paper.book().write(Prevalent.namebook, nameBookS);
                /*
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Log.e("tag", String.valueOf(postSnapshot));
                    autorS = (String) postSnapshot.child("product_tacgia").getValue();
                    Log.e("tag", autorS);
                    if(postSnapshot.getKey().equals("id"))
                    {
                        Log.e("id", "hellooo id");
                    }
                    else {
                        //BorrowBookPhuModel borrowModel = postSnapshot.getValue(BorrowBookPhuModel.class);
                        //autorS = borrowModel.getAuthor();
                        //imageS = borrowModel.getImage();
                        //nameBookS = borrowModel.getBookname();
                        //Log.e("tag", String.valueOf(postSnapshot));
                    }


                }

                 */

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
