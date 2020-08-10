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

import teo.android.teoshop.Model.CartModel;
import teo.android.teoshop.Model.LockModel;
import teo.android.teoshop.Model.NotificationModel;
import teo.android.teoshop.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ImageViewHolder> {

    private Context context;
    private List<NotificationModel> notificationList;

    public NotificationAdapter(Context context, List<NotificationModel> notificationList)
    {
        this.context = context;
        this.notificationList = notificationList;
    }
    @NonNull
    @Override

    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);


        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        NotificationModel notificationCur = notificationList.get(position);
        holder.imgNotification.setImageResource(notificationCur.getImage());
        holder.txtContextNotification.setText(notificationCur.getContext());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView txtContextNotification;
        public ImageView imgNotification;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContextNotification = itemView.findViewById(R.id.txtContextNotification);
            imgNotification = itemView.findViewById(R.id.imgNotification);
        }
    }
}
