package teo.android.teoshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.paperdb.Paper;
import teo.android.teoshop.Model.CartModel;
import teo.android.teoshop.Prevalent.Prevalent;
import teo.android.teoshop.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ImageViewHolder> {

    private Context context;
    private List<CartModel> cartList;


    public CartAdapter(Context context, List<CartModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);


        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        final CartModel cartCur = cartList.get(position);
        holder.txtTenTacGiaCart.setText(cartCur.getAuthor());
        holder.txtNameBookCart.setText(cartCur.getBookname());
        holder.txtDate.setText(cartCur.getDatechoose());
        Picasso.with(context)
                .load(cartCur.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imgHinhCart);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = Paper.book().read(Prevalent.userEmailKey);
                String bookname = cartCur.getBookname();
                deleteItem(userEmail, bookname);
                cartList.remove(position);
                //notifyDataSetChanged();
            }
        });
    }

    private void deleteItem(String userEmail, String bookname) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userEmail).child("choose").child(bookname);
        RootRef.removeValue();
        //notifyDataSetChanged();
        Toast.makeText(context, "Xóa "+bookname+" thành công",Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhCart;
        public TextView txtNameBookCart, txtTenTacGiaCart, txtDate;
        public ImageView btnDelete;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhCart = itemView.findViewById(R.id.imgHinhCart);
            txtNameBookCart = itemView.findViewById(R.id.txtNameBookCart);
            txtTenTacGiaCart = itemView.findViewById(R.id.txtTenTacGiaCart);
            txtDate = itemView.findViewById(R.id.txtDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            Paper.init(context);
        }
    }
}
