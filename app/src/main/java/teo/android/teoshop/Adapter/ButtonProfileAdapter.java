package teo.android.teoshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import teo.android.teoshop.Model.ButtonProfileModel;
import teo.android.teoshop.MyOnItemButtonListClickListener;
import teo.android.teoshop.MyOnItemPopularClickListener;
import teo.android.teoshop.R;

public class ButtonProfileAdapter extends RecyclerView.Adapter<ButtonProfileAdapter.ImageViewHolder> {
    private Context context;
    private List<ButtonProfileModel> buttonList;
    private MyOnItemButtonListClickListener myOnItemButtonListClickListener;

    public ButtonProfileAdapter(Context context, List<ButtonProfileModel> buttonList) {
        this.context = context;
        this.buttonList = buttonList;
    }

    public void MyOnItemButtonListClickListener(MyOnItemButtonListClickListener MyOnItemButtonListClickListener)
    {
        this.myOnItemButtonListClickListener= MyOnItemButtonListClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_button_profile, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        ButtonProfileModel buttonProfileModel = buttonList.get(position);
        holder.txtFunctionItemProfile.setText(buttonProfileModel.getFunction());
        holder.imgHinhItemProfile.setImageResource(buttonProfileModel.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemButtonListClickListener.onClick(buttonList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return buttonList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhItemProfile;
        public TextView txtFunctionItemProfile;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhItemProfile = itemView.findViewById(R.id.imgHinhItemProfile);
            txtFunctionItemProfile = itemView.findViewById(R.id.txtFunctionItemProfile);
        }
    }
}
