package teo.android.teoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;
import teo.android.teoshop.Prevalent.Prevalent;

public class SettingPersonActivity extends AppCompatActivity {

    private EditText edtNhapMatKhauSetting, edtNhapLaiMatKhauSetting;
    private ImageView btnVisiablePass, btnVisiableRePass;
    private Button btnConfirmChangePass;
    private ProgressDialog loading;
    private boolean flatVisible1 = true, flatVisible2 = true;
    private String email;

    private String tableDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_person);

        addControls();
        addEvents();

    }

    private void addEvents() {
        btnVisiablePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flatVisible1 == true)
                {
                    edtNhapMatKhauSetting.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnVisiablePass.setImageResource(R.drawable.ic_visibility_black_24dp);
                    flatVisible1 = false;
                }else{
                    edtNhapMatKhauSetting.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnVisiablePass.setImageResource(R.drawable.ic_visibility_off);
                    flatVisible1 = true;
                }
            }
        });

        btnVisiableRePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flatVisible2 == true)
                {
                    edtNhapLaiMatKhauSetting.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnVisiableRePass.setImageResource(R.drawable.ic_visibility_black_24dp);
                    flatVisible2 = false;
                }else{
                    edtNhapLaiMatKhauSetting.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnVisiableRePass.setImageResource(R.drawable.ic_visibility_off);
                    flatVisible2 = true;
                }
            }
        });

        btnConfirmChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edtNhapMatKhauSetting.getText().toString();
                String re_pass = edtNhapLaiMatKhauSetting.getText().toString();

                if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(SettingPersonActivity.this,
                            "password empty"
                            , Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(re_pass))
                {
                    Toast.makeText(SettingPersonActivity.this,
                            "Re-password empty"
                            , Toast.LENGTH_SHORT).show();
                }else if (!pass.equals(re_pass)){
                    Toast.makeText(SettingPersonActivity.this,
                            "Re-password wrong"
                            , Toast.LENGTH_SHORT).show();
                }
                else{
                    loading.setTitle("Change");
                    loading.setMessage("Waitting...");
                    loading.setCanceledOnTouchOutside(false);
                    loading.show();
                    changePass(pass);
                }
            }
        });

    }

    private void changePass(String pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference(tableDbName).child(email).child("password");
        RootRef.setValue(pass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                loading.dismiss();
                Toast.makeText(SettingPersonActivity.this, "Change Password Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        edtNhapMatKhauSetting = findViewById(R.id.edtNhapMatKhauSetting);
        edtNhapLaiMatKhauSetting = findViewById(R.id.edtNhapLaiMatKhauSetting);

        btnVisiablePass = findViewById(R.id.btnVisiablePass);
        btnVisiableRePass = findViewById(R.id.btnVisiableRePass);
        btnConfirmChangePass = findViewById(R.id.btnConfirmChangePass);
        loading = new ProgressDialog(this);

        Paper.init(this);
        email = Paper.book().read(Prevalent.userEmailKey);
    }
}
