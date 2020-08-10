package teo.android.teoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;
import teo.android.teoshop.Model.Popular;
import teo.android.teoshop.Model.User;
import teo.android.teoshop.Prevalent.Prevalent;

public class BookActivity extends AppCompatActivity {
    private ImageView imgHinhBook, imgHinhBook1, imgHinhBook2, imgHinhBook3, imgHinhMap;
    private TextView txtNameBook, txtSoLuongBook, txtXemDiaChi, txtTenTacGia,txtOutOfStock;
    private Button btnChonDat;

    private String imgHinhBookS, imgHinhBookS1, imgHinhBookS2, imgHinhBookS3,
            imgXemDiaChiS, txtNameBookS, txtSoLuongBookS, txtTenTacGiaS, dateS, userEmail, codebook;
    private int txtSoLuongBookI;
    private Context context;
    private ProgressDialog loadingbar;

    private AlertDialog alertDialogImageMap;
    private AlertDialog.Builder alterBuilder;

    private String codeHeader = "";
    private String codeRail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        addContorls();
        addEvents();
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    private void addEvents() {
        btnChonDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Paper.book().read(Prevalent.userEmailKey);
                txtNameBookS = encodeUserEmail(txtNameBookS);
                loadingbar.setTitle(getResources().getString(R.string.choose));
                loadingbar.setMessage("Waitting...");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
                chooseBook(imgHinhBookS,txtNameBookS,txtTenTacGiaS,email);
            }
        });
        txtXemDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpImageMap();
            }
        });
    }

    private void chooseBook(final String imgHinhBookSS, final String txtNameBookSS, final String txtTenTacGiaSS, final String userEmailSS) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Users").child(userEmailSS).child("choose").child(txtNameBookSS).exists()) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("bookname", txtNameBookSS);
                    userDataMap.put("author", txtTenTacGiaSS);
                    userDataMap.put("image", imgHinhBookSS);
                    userDataMap.put("code", codebook);
                    RootRef.child("Users").child(userEmailSS).child("choose").child(txtNameBookSS).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        loadingbar.dismiss();
                                        Toast.makeText(BookActivity.this,
                                                getResources().getString(R.string.choose),
                                                Toast.LENGTH_SHORT).show();

                                    }else{
                                        loadingbar.dismiss();
                                        Toast.makeText(BookActivity.this,
                                                getResources().getString(R.string.fail),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    loadingbar.dismiss();
                    Toast.makeText(BookActivity.this,
                            getResources().getString(R.string.bookchoosechooseotherplease),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void addContorls() {
        imgHinhBook = findViewById(R.id.imgHinhBook);
        imgHinhBook1 = findViewById(R.id.imgHinhBook1);
        imgHinhBook2 = findViewById(R.id.imgHinhBook2);
        imgHinhBook3 = findViewById(R.id.imgHinhBook3);
        txtNameBook = findViewById(R.id.txtNameBook);
        txtSoLuongBook = findViewById(R.id.txtSoLuongBook);
        txtXemDiaChi = findViewById(R.id.txtXemDiaChi);
        txtTenTacGia = findViewById(R.id.txtTenTacGia);
        txtOutOfStock = findViewById(R.id.txtOutOfStock);
        btnChonDat = findViewById(R.id.btnChonDat);
        Paper.init(this);
        loadingbar = new ProgressDialog(this);
        Intent intent = getIntent();
        imgHinhBookS = intent.getStringExtra("IMAGE");
        imgHinhBookS1 = intent.getStringExtra("IMAGE1");
        imgHinhBookS2 = intent.getStringExtra("IMAGE2");
        imgHinhBookS3 = intent.getStringExtra("IMAGE3");
        txtNameBookS = intent.getStringExtra("NameBook");
        txtSoLuongBookI = intent.getIntExtra("SoLuongBook", 0);
        txtTenTacGiaS = intent.getStringExtra("TenTacGia");
        imgXemDiaChiS = intent.getStringExtra("DiaChi");
        codebook = intent.getStringExtra("Code");
        hienThiPageSach();
        if (txtSoLuongBookI == 0)
        {
            txtOutOfStock.setVisibility(View.VISIBLE);
            btnChonDat.setVisibility(View.INVISIBLE);
        }else{
            txtOutOfStock.setVisibility(View.INVISIBLE);
            btnChonDat.setVisibility(View.VISIBLE);
        }

        codeHeader = codebook.substring(0, 3);
        codeRail = codebook.substring(3);

        Log.d("TAG", "codeHeader: " + codeHeader);
        Log.d("TAG", "codeRail: " + codeRail);

        updateNumberBook(codeHeader, codeRail);
    }

    private void updateNumberBook(String codeHeader, String codeRail) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(codeHeader).child(codeRail);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Popular popular = dataSnapshot.getValue(Popular.class);
                txtSoLuongBook.setText(String.valueOf(popular.getProduct_soluong()));
                if (popular.getProduct_soluong() == 0)
                {
                    txtOutOfStock.setVisibility(View.VISIBLE);
                    btnChonDat.setVisibility(View.INVISIBLE);
                }else{
                    txtOutOfStock.setVisibility(View.INVISIBLE);
                    btnChonDat.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void hienThiPageSach() {
        Picasso.with(this)
                .load(imgHinhBookS)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imgHinhBook);
        Picasso.with(this)
                .load(imgHinhBookS1)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imgHinhBook1);
        Picasso.with(this)
                .load(imgHinhBookS2)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imgHinhBook2);
        Picasso.with(this)
                .load(imgHinhBookS3)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imgHinhBook3);
        txtNameBook.setText(txtNameBookS);
        txtSoLuongBook.setText(String.valueOf(txtSoLuongBookI));
        txtTenTacGia.setText(txtTenTacGiaS);



    }

    public void PopUpImageMap(){
        alterBuilder = new AlertDialog.Builder(this);
        View contactPopupView = getLayoutInflater().inflate(R.layout.popup_image, null);
        imgHinhMap = contactPopupView.findViewById(R.id.imgHinhMap);
        Picasso.with(this)
                .load(imgXemDiaChiS)
                .placeholder(R.drawable.books)
                .fit()
                .centerCrop()
                .into(imgHinhMap);
        alterBuilder.setView(contactPopupView);
        alertDialogImageMap = alterBuilder.create();
        alertDialogImageMap.show();
    }
}
