package teo.android.teoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;
import teo.android.teoshop.Adapter.CategoryAdapter;
import teo.android.teoshop.Adapter.PopularAdapter;
import teo.android.teoshop.Model.Category;
import teo.android.teoshop.Model.LockModel;
import teo.android.teoshop.Model.Popular;
import teo.android.teoshop.Model.User;
import teo.android.teoshop.Prevalent.Prevalent;


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavViewId;
    FrameLayout framLayoutId;
    String tableDbName = "Users";
    MenuItem menuItem;
    TextView txtNotification;
    int pindNotification = 0;
    private View notificationBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setFragment(new HomeFragment());


        addControls();
        addEvents();
        receiveNotification();
        addBadgeView(pindNotification);
    }

    private void addEvents() {
        bottomNavViewId.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.personId:
                        //bottomNavViewId.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(new PersonFragment());
                        return true;
                    case R.id.homeId:
                        //bottomNavViewId.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(new HomeFragment());
                        return true;
                    case R.id.cartId:
                        setFragment(new CartFragment());
                        return true;
                    case R.id.myBookId:
                        setFragment(new MyBookFragment());
                        return true;
                    case R.id.notificationId:
                        pindNotification = 0;
                        Paper.book().write(String.valueOf(Prevalent.pind), pindNotification);
                        notificationBadge.setVisibility(View.INVISIBLE);
                        setFragment(new NotificationFragment());
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void addBadgeView(int pinda) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavViewId.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(2);

        notificationBadge = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
        txtNotification = notificationBadge.findViewById(R.id.txtNotification);
        //txtNotification.setText(String.valueOf(pindNotification));
        if (pinda == 0)
        {
            txtNotification.setVisibility(View.INVISIBLE);
            itemView.addView(notificationBadge);
        }else{
            txtNotification.setText(String.valueOf(pinda));
            itemView.addView(notificationBadge);
        }

    }

    private void receiveNotification() {
        final String email = Paper.book().read(Prevalent.userEmailKey);
        DatabaseReference databaseReference ;
        databaseReference = FirebaseDatabase.getInstance().getReference(tableDbName).child(email).child("borrow");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LockModel userData = dataSnapshot.getValue(LockModel.class);
                String lockerpass = userData.getLockerpw();
                Log.e("lockerpass: ", lockerpass);
                String locknumber = userData.getLockernumber();
                Log.e("locknumber: ", locknumber);
                if(lockerpass.equals("null"))
                {

                }else{
                    pindNotification = Paper.book().read(String.valueOf(Prevalent.pind));
                    pindNotification++;
                    Paper.book().write(String.valueOf(Prevalent.pind), pindNotification);
                    Log.e("locknumber: ", "hello");
                    notification(locknumber, lockerpass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void notification(String lockernumber, String lockerpw)
    {
        notificationBadge.setVisibility(View.VISIBLE);
        pindNotification = Paper.book().read(String.valueOf(Prevalent.pind));
        Log.e("pind", String.valueOf(pindNotification));
        txtNotification.setText(String.valueOf(pindNotification));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel =
                    new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentTitle(getString(R.string.notification))
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentText("locker number: "+lockernumber+" Pass: "+lockerpw)
                .setSound(uri);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framLayoutId, fragment);
        fragmentTransaction.commit();
    }

    private void addControls() {
        bottomNavViewId = findViewById(R.id.bottomNavViewId);
        framLayoutId = findViewById(R.id.framLayoutId);
        Paper.init(this);
        try {
            pindNotification = Paper.book().read(String.valueOf(Prevalent.pind));
        }catch (Exception e)
        {
            Paper.book().write(String.valueOf(Prevalent.pind), 0);
        }

    }
}
