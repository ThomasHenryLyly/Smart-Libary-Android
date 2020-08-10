package teo.android.teoshop;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import teo.android.teoshop.R;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamite.DynamiteModule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;
import teo.android.teoshop.Adapter.ButtonProfileAdapter;
import teo.android.teoshop.Model.ButtonProfileModel;
import teo.android.teoshop.Model.User;
import teo.android.teoshop.Prevalent.Prevalent;


public class PersonFragment extends Fragment {

    private ImageView imgAvatar;
    private TextView txtNameAvatar, txtEmailAvatar, txtNganhAvatar;
    private RecyclerView rvButtonProfileList;

    private ButtonProfileAdapter buttonProfileAdapter;
    private ArrayList<ButtonProfileModel> buttonProfileList;
    private ButtonProfileModel buttonProfileModel;
    private String tableDbName = "Users", tenNganh;
    private DatabaseReference databaseReference;

    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        txtNameAvatar = view.findViewById(R.id.txtNameAvatar);
        txtEmailAvatar = view.findViewById(R.id.txtEmailAvatar);
        txtNganhAvatar = view.findViewById(R.id.txtNganhAvatar);
        rvButtonProfileList = view.findViewById(R.id.rvButtonProfileList);
        Paper.init(getContext());
        showButtonList();
        showProfile();
        return  view;
    }

    private void showProfile() {
        final String email = Paper.book().read(Prevalent.userEmailKey);
        DatabaseReference databaseReference ;
        databaseReference = FirebaseDatabase.getInstance().getReference(tableDbName).child(email);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    User userData = dataSnapshot.getValue(User.class);
                    //Log.e("tag", "error"+userData.getEmail());
                    txtEmailAvatar.setText(userData.getEmail());
                    tenNganh = userData.getMajor();
                    txtNganhAvatar.setText(userData.getMajor());
                    txtNameAvatar.setText(userData.getName());

                    Picasso.with(getContext())
                            .load(userData.getImage())
                            .placeholder(R.mipmap.ic_launcher)
                            .fit()
                            .centerCrop()
                            .into(imgAvatar);

                }catch(Exception ex){

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showButtonList() {
        rvButtonProfileList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerDevice = new GridLayoutManager(getContext(), 1);
        rvButtonProfileList.setLayoutManager(layoutManagerDevice);
        buttonProfileList = new ArrayList<>();

        //buttonProfileList.add(new ButtonProfileModel(R.drawable.books, "specializedbook", 1));
        //buttonProfileList.add(new ButtonProfileModel(R.drawable.ic_borrow, "borrowed", 2));
        //buttonProfileList.add(new ButtonProfileModel(R.drawable.ic_setting, "setting", 3));
        //buttonProfileList.add(new ButtonProfileModel(R.drawable.logout, "log out", 4));
        buttonProfileList.add(new ButtonProfileModel(R.drawable.books, getResources().getString(R.string.specializedbook), 1));
        buttonProfileList.add(new ButtonProfileModel(R.drawable.ic_borrow, getResources().getString(R.string.borrowed), 2));
        buttonProfileList.add(new ButtonProfileModel(R.drawable.ic_setting, getResources().getString(R.string.setting), 3));
        buttonProfileList.add(new ButtonProfileModel(R.drawable.logout, getResources().getString(R.string.logout), 4));

        buttonProfileAdapter = new ButtonProfileAdapter(getContext(), buttonProfileList);

        buttonProfileAdapter.MyOnItemButtonListClickListener(new MyOnItemButtonListClickListener() {
            @Override
            public void onClick(ButtonProfileModel buttonProfileModel) {
                if(buttonProfileModel.getStt()== 2)
                {
                    Fragment fragment = new MyBookFragment();
                    replaceFragment(fragment);
                }else if (buttonProfileModel.getStt()== 4){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else if (buttonProfileModel.getStt()== 1){
                    Intent intent = new Intent(getContext(), SachChuyenNganhActivity.class);
                    intent.putExtra("MAJOR", tenNganh);
                    startActivity(intent);
                }else if (buttonProfileModel.getStt()== 3)
                {
                    Intent intent = new Intent(getContext(), SettingPersonActivity.class);
                    startActivity(intent);
                }
            }
        });
        rvButtonProfileList.setAdapter(buttonProfileAdapter);
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framLayoutId, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
