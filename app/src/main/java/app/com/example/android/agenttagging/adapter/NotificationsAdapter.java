package app.com.example.android.agenttagging.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import app.com.example.android.agenttagging.R;
import app.com.example.android.agenttagging.model.NotificationsModel;

/**
 * Created by shuvam on 08-09-2016.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private Context context;
    private List<NotificationsModel> notificationsModelList;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder{
        TextView notipostStr,notiStatus,notifor;
        Button btnTag;
        //ImageView notiPic;

        public ViewHolder(View itemView){
            super(itemView);
            this.notipostStr = (TextView) itemView.findViewById(R.id.notipoststr);
            this.notifor = (TextView) itemView.findViewById(R.id.fortype);
            this.notiStatus = (TextView) itemView.findViewById(R.id.status);
            this.btnTag = (Button) itemView.findViewById(R.id.btntag);
            //this.notiPic = (ImageView) itemView.findViewById(R.id.notipropic);
        }
    }

    public NotificationsAdapter(Context context, List<NotificationsModel> notificationsModelList){
        this.context = context;
        this.notificationsModelList = notificationsModelList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_display_item,parent,false));
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        NotificationsModel notificationsModel = (NotificationsModel) this.notificationsModelList.get(position);
        holder.notipostStr.setText(notificationsModel.getNotiUser() + " has posted a property \" " + notificationsModel.getNotiforType() + " at " + notificationsModel.getNotiproadd() + "\". ");
        holder.notiStatus.setText(notificationsModel.getNotiStatus());
        holder.notifor.setText(notificationsModel.getNotiforType());


        String curstatus = holder.notiStatus.getText().toString();
        switch (curstatus){
            case "Pending":
                holder.notiStatus.setTextColor(Color.BLUE);
                break;
            case "Approved":
                holder.notiStatus.setTextColor(Color.YELLOW);
                break;
            case "Denied":
                holder.notiStatus.setTextColor(Color.RED);
                break;
            default:
                holder.notiStatus.setVisibility(View.GONE);
                holder.btnTag.setVisibility(View.VISIBLE);

        }
        //Picasso.with(context).load(notificationsModel.getNotiPic()).resize(150,150).into(holder.notiPic);
    }

    public int getItemCount(){
        return notificationsModelList.size();
    }
}



