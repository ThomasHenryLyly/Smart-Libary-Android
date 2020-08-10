package teo.android.teoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import teo.android.teoshop.Model.User;
import teo.android.teoshop.Prevalent.Prevalent;

public class MainActivity extends AppCompatActivity {
    Button btnMainRegister, btnMainLogin;
    String tableDbName = "Users";

    private ProgressDialog loadingbar;
    String userEmailKeyEncode = "";
    String userEmailKey = "";
    String userPassKey ="";
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnMainRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void addControls() {
        btnMainRegister = findViewById(R.id.btnMainRegister);
        btnMainLogin = findViewById(R.id.btnMainLogin);
        loadingbar = new ProgressDialog(this);

        Paper.init(this);

        userEmailKey = Paper.book().read(Prevalent.userEmailKey);
        //userEmailKeyEncode = encodeUserEmail(userEmailKey);
        userPassKey = Paper.book().read(Prevalent.userPassKey);
        if(userEmailKey != "" && userPassKey != "")
        {
            if(!TextUtils.isEmpty(userEmailKey) && !TextUtils.isEmpty(userPassKey))
            {
                loadingbar.setTitle(getResources().getString(R.string.login));
                loadingbar.setMessage(getResources().getString(R.string.waitting));
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
                loadingbar.dismiss();
                //allowAccess(userEmailKey, userPassKey);
            }
        }

    }

    private void allowAccess(final String email, final String pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(tableDbName).child(email).exists())
                {
                    User userData = dataSnapshot.child(tableDbName).child(email).getValue(User.class);

                    if (userData.getEmail().equals(email))
                    {
                        if (userData.getPassword().equals(pass))
                        {
                            //Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            loadingbar.dismiss();
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.wrongpassword), Toast.LENGTH_SHORT).show();
                        }
                    }


                }else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.unregisteredaccount), Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.pleaseregister), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
