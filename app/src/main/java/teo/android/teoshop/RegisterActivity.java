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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegisterRegister;
    private EditText txtEmailResgister, txtNameRegister, txtNganhRegister, txtPassRegister, txtRePassRegister;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    private void registerAccount() {
        String emailRegister = encodeUserEmail(txtEmailResgister.getText().toString());
        String nameRegister = txtNameRegister.getText().toString();
        String nganhRegister = txtNganhRegister.getText().toString();
        String passRegister = txtPassRegister.getText().toString();
        String rePassRegister = txtRePassRegister.getText().toString();

        if(TextUtils.isEmpty(emailRegister))
        {
            Toast.makeText(this,
                    getResources().getString(R.string.importemail),
                    Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(nameRegister))
        {
            Toast.makeText(this,
                    getResources().getString(R.string.importname),
                    Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(nganhRegister))
        {
            Toast.makeText(this,
                    getResources().getString(R.string.importmajor),
                    Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(passRegister))
        {
            Toast.makeText(this,
                    getResources().getString(R.string.importpass),
                    Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(rePassRegister))
        {
            Toast.makeText(this,
                    getResources().getString(R.string.importpass),
                    Toast.LENGTH_SHORT).show();
        }else if(!passRegister.equals(rePassRegister))
        {
            Toast.makeText(this,
                    getResources().getString(R.string.passworddidnotmatch),
                    Toast.LENGTH_SHORT).show();
        }else
        {
            loadingBar.setTitle(getResources().getString(R.string.creatingaccount));
            loadingBar.setMessage(getResources().getString(R.string.waitting));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            createAccount(emailRegister, nameRegister, nganhRegister, passRegister);

        }

    }

    private void createAccount(final String emailRegister, final String nameRegister, final String nganhRegister, final String passRegister) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Users").child(emailRegister).exists())
                {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("email", emailRegister);
                    userDataMap.put("name", nameRegister);
                    userDataMap.put("major", nganhRegister);
                    userDataMap.put("password", passRegister);
                    RootRef.child("Users").child(emailRegister).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,
                                                getResources().getString(R.string.successful),
                                                Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this,
                                                getResources().getString(R.string.fail),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }else{
                    Toast.makeText(RegisterActivity.this,
                            emailRegister + " " +getResources().getString(R.string.alreadyexist)
                            , Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this,
                            getResources().getString(R.string.registeragain),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addControls() {
        btnRegisterRegister = findViewById(R.id.btnRegisterRegister);
        txtEmailResgister = findViewById(R.id.txtEmailResgister);
        txtNameRegister = findViewById(R.id.txtNameRegister);
        txtNganhRegister = findViewById(R.id.txtNganhRegister);
        txtPassRegister = findViewById(R.id.txtPassRegister);
        txtRePassRegister = findViewById(R.id.txtRePassRegister);
        loadingBar = new ProgressDialog(this);

    }
}
