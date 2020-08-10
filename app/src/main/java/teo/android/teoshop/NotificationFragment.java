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

import io.paperdb.Paper;
import teo.android.teoshop.Adapter.NotificationAdapter;
import teo.android.teoshop.Model.LockModel;
import teo.android.teoshop.Model.NotificationModel;
import teo.android.teoshop.Prevalent.Prevalent;

public class NotificationFragment extends Fragment {
    private RecyclerView rvrvNotificationList;
    private NotificationAdapter notificationAdapter;
    private ArrayList<NotificationModel>notificationList;
    private View view;

    private String userEmail, mssv;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);;
        rvrvNotificationList = view.findViewById(R.id.rvNotificationList);
        Paper.init(getContext());
        userEmail = Paper.book().read(Prevalent.userEmailKey);
        showNotification();
        return view;
    }

    private void showNotification() {
        rvrvNotificationList.setHasFixedSize(true);
        rvrvNotificationList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        notificationList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(userEmail).child("borrow");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notificationList.clear();
                    Log.e("LOCK", dataSnapshot.toString());
                    LockModel lockModel = dataSnapshot.getValue(LockModel.class);
                    NotificationModel notificationModel = new NotificationModel(R.drawable.ic_lock_black_24dp, "\tlocker: " + lockModel.getLockernumber() + "\n\tPass: " + lockModel.getLockerpw());
                    if (lockModel.getLockerpw().equals("null"))
                    {

                    }else{
                        notificationList.add(notificationModel);
                    }

                notificationAdapter = new NotificationAdapter(getContext(), notificationList);
                rvrvNotificationList.setAdapter(notificationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
