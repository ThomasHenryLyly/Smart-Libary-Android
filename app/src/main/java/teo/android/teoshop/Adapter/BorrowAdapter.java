package teo.android.teoshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;
import teo.android.teoshop.Model.BorrowModel;
import teo.android.teoshop.R;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.ImageViewHolder> {
    private Context context;
    private ArrayList<BorrowModel> borrowList;

    public BorrowAdapter(Context context, ArrayList<BorrowModel> borrowList) {
        this.context = context;
        this.borrowList = borrowList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_borrow,parent,false);


        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        BorrowModel borrowCur = borrowList.get(position);
        String id = borrowCur.getId();
        holder.txtDate.setText(borrowCur.getMfg());
        holder.txtExpire.setText(borrowCur.getExp());
        holder.txtNameBookBorrow.setText(borrowCur.getProduct_name());
        holder.txtIdBook.setText(borrowCur.getId());
        Picasso.with(context)
                .load(borrowCur.getProduct_image())
                .placeholder(R.drawable.ic_photo_camera)
                .fit()
                .centerCrop()
                .into(holder.imgHinhBorrow);
    }

    @Override
    public int getItemCount() {
        return borrowList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhBorrow;
        public TextView txtNameBookBorrow, txtTenTacGiaBorrow, txtDate, txtExpire, txtIdBook;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhBorrow = itemView.findViewById(R.id.imgHinhBorrow);
            txtNameBookBorrow = itemView.findViewById(R.id.txtNameBookBorrow);
            txtTenTacGiaBorrow = itemView.findViewById(R.id.txtTenTacGiaBorrow);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtExpire = itemView.findViewById(R.id.txtExpire);
            txtIdBook = itemView.findViewById(R.id.txtIdBook);
        }
    }
}
