package teo.android.teoshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import teo.android.teoshop.Model.Category;
import teo.android.teoshop.MyOnItemPopularClickListener;
import teo.android.teoshop.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ImageViewHolder> {

    private Context context;
    private ArrayList<Category> categoryList;
    private MyOnItemPopularClickListener myOnItemPopularClickListener;



    public CategoryAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public void setMyOntemPopularClickListener(MyOnItemPopularClickListener myOnItemPopularClickListener){
        this.myOnItemPopularClickListener = myOnItemPopularClickListener;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        Category categoryCur = categoryList.get(position);
        holder.txtCardName.setText(categoryCur.getCatname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemPopularClickListener.onClick(categoryList.get(position));
            }
        });
        /*
        holder.txtCardName.setBackgroundColor(Color.parseColor(categoryCur.getCartitlebg()));
        holder.iconwapper.setBackgroundColor(Color.parseColor(categoryCur.getCatbg()));
        Picasso.with(context)
                .load(categoryCur.getCaticon())
                .placeholder(R.drawable.ic_menu_gallery)
                .fit()
                .centerCrop()
                .into(holder.imgCatIcon);
         */
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView txtCardName;
        public ImageView imgCatIcon;
        public LinearLayout iconwapper;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCardName = itemView.findViewById(R.id.txtCardName);
        }
    }
}
