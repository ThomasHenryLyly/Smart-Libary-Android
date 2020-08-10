package teo.android.teoshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import teo.android.teoshop.MyOnItemPopularClickListener;
import teo.android.teoshop.Model.Popular;
import teo.android.teoshop.R;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ImageViewHolder> {

    private Context context;
    private List<Popular> popularList;
    private MyOnItemPopularClickListener myOnItemPopularClickListener;

    public PopularAdapter(Context context, List<Popular> popularList) {
        this.context = context;
        this.popularList = popularList;
    }

    public void setMyOntemPopularClickListener(MyOnItemPopularClickListener myOntemPopularClickListener){
        this.myOnItemPopularClickListener = myOntemPopularClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_item, parent, false);


        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        Popular popularCur = popularList.get(position);
        int soLuong = popularCur.getProduct_soluong();
        holder.txtSoLuong.setText(String.valueOf(soLuong));
        Picasso.with(context)
                .load(popularCur.getProduct_image())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imgHinhPopular);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemPopularClickListener.onClick(popularList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView txtSoLuong;
        public ImageView imgHinhPopular;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imgHinhPopular = itemView.findViewById(R.id.imgHinhPopular);

        }
    }
}
