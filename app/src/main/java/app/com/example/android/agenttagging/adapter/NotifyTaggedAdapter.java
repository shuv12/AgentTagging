package app.com.example.android.agenttagging.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.com.example.android.agenttagging.R;
import app.com.example.android.agenttagging.model.NotificationsModel;

/**
 * Created by shuvam on 09-09-2016.
 */
public class NotifyTaggedAdapter extends RecyclerView.Adapter<NotifyTaggedAdapter.ViewHolder> {
    private Context context;
    private List<NotificationsModel> notificationsModelList;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView notipostString, notiStatus, notifor;
        ImageView notiPic;

        public ViewHolder(View itemView) {
            super(itemView);
            this.notipostString = (TextView) itemView.findViewById(R.id.notipoststr);
            this.notifor = (TextView) itemView.findViewById(R.id.fortype);
            this.notiStatus = (TextView) itemView.findViewById(R.id.status);
            this.notiPic = (ImageView) itemView.findViewById(R.id.notipropic);
        }
    }

    public NotifyTaggedAdapter(Context context, List<NotificationsModel> notificationsModelList) {
        this.context = context;
        this.notificationsModelList = notificationsModelList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_display_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationsModel notificationsModel = (NotificationsModel) this.notificationsModelList.get(position);
        holder.notiStatus.setText(notificationsModel.getNotiStatus());
        holder.notiStatus.setTextColor(ContextCompat.getColor(context,R.color.approved));
        holder.notipostString.setText(notificationsModel.getNotiText());
        holder.notifor.setText(notificationsModel.getNotiforType());
        Picasso.with(context).load(notificationsModel.getNotiPic()).fit().into(holder.notiPic);
    }

    public int getItemCount() {
        return notificationsModelList.size();
    }
}
