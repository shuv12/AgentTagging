package app.com.example.android.agenttagging.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import app.com.example.android.agenttagging.R;
import app.com.example.android.agenttagging.model.NotificationsModel;

/**
 * Created by shuvam on 08-09-2016.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private Context context;
    private List<NotificationsModel> notificationsModelList;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView notipostStr, notiStatus, notifor;
        Button btnTag;
        ImageButton tick,cross;
        LinearLayout requestLayout;
        //ImageView notiPic;

        public ViewHolder(View itemView) {
            super(itemView);
            this.notipostStr = (TextView) itemView.findViewById(R.id.notipoststr);
            this.notifor = (TextView) itemView.findViewById(R.id.fortype);
            this.notiStatus = (TextView) itemView.findViewById(R.id.status);
            this.btnTag = (Button) itemView.findViewById(R.id.btntag);
            this.requestLayout = (LinearLayout) itemView.findViewById(R.id.requested);
            this.tick = (ImageButton) itemView.findViewById(R.id.checkaccept);
            this.cross = (ImageButton) itemView.findViewById(R.id.crossdenied);

            //this.notiPic = (ImageView) itemView.findViewById(R.id.notipropic);
        }
    }

    public NotificationsAdapter(Context context, List<NotificationsModel> notificationsModelList) {
        this.context = context;
        this.notificationsModelList = notificationsModelList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_display_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationsModel notificationsModel = (NotificationsModel) this.notificationsModelList.get(position);
        holder.notipostStr.setText(notificationsModel.getNotiUser() + " has posted a property \" " + notificationsModel.getNotiforType() + " at " + notificationsModel.getNotiproadd() + "\". ");
        holder.notiStatus.setText(notificationsModel.getNotiStatus());
        holder.notifor.setText(notificationsModel.getNotiforType());


        final String curstatus = holder.notiStatus.getText().toString();
        switch (curstatus) {
            case "Pending":
                holder.notiStatus.setTextColor(ContextCompat.getColor(context,R.color.pending));
                break;
            case "Approved":
                holder.notiStatus.setTextColor(ContextCompat.getColor(context,R.color.approved));
                break;
            case "Denied":
                holder.notiStatus.setTextColor(ContextCompat.getColor(context,R.color.denied));
                break;
            case "tag":
                holder.notiStatus.setVisibility(View.GONE);
                holder.btnTag.setVisibility(View.VISIBLE);
                holder.btnTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean wrapInScrollView = true;
                        final MaterialDialog dialog = new MaterialDialog.Builder(context)
                                .title(R.string.requestsentitle)
                                .customView(R.layout.senttagboxlayout,wrapInScrollView)
                                .titleGravity(GravityEnum.CENTER)
                                .show();

                        View view = dialog.getCustomView();
                        Button sentTag = (Button) view.findViewById(R.id.senttagdone);
                        sentTag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                break;
            case "requested":
                holder.notiStatus.setVisibility(View.GONE);
                holder.requestLayout.setVisibility(View.VISIBLE);
                holder.tick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean wrapInScrollView = true;
                        final MaterialDialog dialogappr = new MaterialDialog.Builder(context)
                                .title(R.string.notirequest)
                                .customView(R.layout.approvedtagboxlayout,wrapInScrollView)
                                .titleGravity(GravityEnum.CENTER)
                                .show();

                        View view = dialogappr.getCustomView();
                        Button sendMsg = (Button) view.findViewById(R.id.sendtextmsg);
                        sendMsg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogappr.dismiss();
                            }
                        });
                    }
                });
                break;
            default:
                holder.notiStatus.setVisibility(View.GONE);
                holder.btnTag.setVisibility(View.VISIBLE);
        }
    }

    public int getItemCount() {
        return notificationsModelList.size();
    }
}



