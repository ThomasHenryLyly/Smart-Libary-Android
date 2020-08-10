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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import teo.android.teoshop.Model.User;
import teo.android.teoshop.Prevalent.Prevalent;

public class LoginActivity extends AppCompatActivity {
    private EditText txtEmailJoin, txtPassJoin;
    private CheckBox ckbRememberMe;
    private TextView txtForgetPassWord;
    private Button btnLoginLogin;
    private ProgressDialog loadingJoin;
    private String tableDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAccount();
            }
        });
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }


    private void loginAccount() {
        String email = encodeUserEmail(txtEmailJoin.getText().toString());
        String pass = txtPassJoin.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.importemail)
                    , Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(LoginActivity.this,
                    getResources().getString(R.string.importpass)
                    , Toast.LENGTH_SHORT).show();
        }else{

            loadingJoin.setTitle(getResources().getString(R.string.login));
            loadingJoin.setMessage(getResources().getString(R.string.waitting));
            loadingJoin.setCanceledOnTouchOutside(false);
            loadingJoin.show();


            login(email, pass);
        }
    }

    private void login(final String email, final String pass) {

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
                            //Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            loadingJoin.dismiss();
                            if(ckbRememberMe.isChecked())
                            {
                                Paper.book().write(Prevalent.userEmailKey, email);
                                Paper.book().write(Prevalent.userPassKey, pass);
                                Paper.book().write(Prevalent.chk, "check");
                            }else{
                                Paper.book().write(Prevalent.userEmailKey, email);
                                Paper.book().write(Prevalent.userPassKey, pass);
                                Paper.book().write(Prevalent.chk, "uncheck");
                            }
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            loadingJoin.dismiss();
                            Toast.makeText(LoginActivity.this,
                                    getResources().getString(R.string.wrongpassword),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                }else{
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.unregisteredaccount),
                            Toast.LENGTH_SHORT).show();
                    loadingJoin.dismiss();
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.pleaseregister),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addControls() {
        txtEmailJoin = findViewById(R.id.txtEmailJoin);
        txtPassJoin = findViewById(R.id.txtPassJoin);
        txtForgetPassWord = findViewById(R.id.txtForgetPassWord);
        ckbRememberMe = findViewById(R.id.ckbRememberMe);
        btnLoginLogin = findViewById(R.id.btnLoginLogin);

        loadingJoin = new ProgressDialog(this);
        Paper.init(this);
        String emaill = Paper.book().read(Prevalent.userEmailKey);
        String passs = Paper.book().read(Prevalent.userPassKey);
        String check = Paper.book().read(Prevalent.chk);

        if(!TextUtils.isEmpty(check))
        {
            if(check.equals("check"))
            {
                ckbRememberMe.setChecked(true);
                if(!TextUtils.isEmpty(emaill))
                {
                    String decodeEmail = decodeUserEmail(emaill);
                    txtEmailJoin.setText(decodeEmail);
                }
                if(!TextUtils.isEmpty(passs))
                {
                    txtPassJoin.setText(passs);
                }
            }
        }



    }
}
