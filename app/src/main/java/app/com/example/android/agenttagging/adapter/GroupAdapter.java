package app.com.example.android.agenttagging.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.com.example.android.agenttagging.R;
import app.com.example.android.agenttagging.model.GroupModel;

/**
 * Created by shuvam on 09-10-2016.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private Context context;
    private List<GroupModel> groupModelList;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        ImageView top_banner,mid_banner,contact_pic,callgroupagent,agent1,agent2,agent3,agent4;
        TextView company_des,company_recruit,contact_name,contact_number,
                nameagent1,nameagent2,nameagent3,nameagent4,noagent1,noagent2,noagent3,noagent4;
        LinearLayout addAgent;

        public ViewHolder(View itemView){
            super(itemView);
            this.top_banner = (ImageView) itemView.findViewById(R.id.top_banner);
            this.mid_banner = (ImageView) itemView.findViewById(R.id.mid_banner);
            this.contact_number = (TextView) itemView.findViewById(R.id.contact_number);
            this.contact_name = (TextView) itemView.findViewById(R.id.contact_name);
            this.contact_pic = (ImageView) itemView.findViewById(R.id.contact_image);
            this.company_recruit = (TextView) itemView.findViewById(R.id.company_recruit);
            this.company_des = (TextView) itemView.findViewById(R.id.company_des);
            this.callgroupagent = (ImageView) itemView.findViewById(R.id.callgroupagent);
            this.addAgent = (LinearLayout) itemView.findViewById(R.id.addagents);
            this.agent1 = (ImageView) itemView.findViewById(R.id.agentpic1);
            this.agent2 = (ImageView) itemView.findViewById(R.id.agentpic2);
            this.agent3 = (ImageView) itemView.findViewById(R.id.agentpic3);
            this.agent4 = (ImageView) itemView.findViewById(R.id.agentpic4);
            this.nameagent1 = (TextView) itemView.findViewById(R.id.agentname1);
            this.nameagent2 = (TextView) itemView.findViewById(R.id.agentname2);
            this.nameagent3 = (TextView) itemView.findViewById(R.id.agentname3);
            this.nameagent4 = (TextView) itemView.findViewById(R.id.agentname4);
            this.noagent1 = (TextView) itemView.findViewById(R.id.agentnum1);
            this.noagent2 = (TextView) itemView.findViewById(R.id.agentnum2);
            this.noagent3 = (TextView) itemView.findViewById(R.id.agentnum3);
            this.noagent4 = (TextView) itemView.findViewById(R.id.agentnum4);
        }
    }

    public GroupAdapter(Context context,List<GroupModel> groupModelList){
        this.context = context;
        this.groupModelList = groupModelList;
    }

    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder,int position) {
        final GroupModel groupModel = (GroupModel) this.groupModelList.get(position);
        holder.company_des.setText(groupModel.getCompany_des());
        holder.company_recruit.setText(groupModel.getCompany_recruit());
        holder.contact_name.setText(groupModel.getContact_name());
        holder.contact_number.setText(groupModel.getContact_number());

        holder.nameagent1.setText(groupModel.getAgent_name1());
        holder.noagent1.setText(groupModel.getAgent_number1());
        Picasso.with(context).load(groupModel.getAgent_pic1()).fit().into(holder.agent1);
        holder.nameagent2.setText(groupModel.getAgent_name2());
        holder.noagent2.setText(groupModel.getAgent_number2());
        Picasso.with(context).load(groupModel.getAgent_pic2()).fit().into(holder.agent2);
        holder.nameagent3.setText(groupModel.getAgent_name3());
        holder.noagent3.setText(groupModel.getAgent_number3());
        Picasso.with(context).load(groupModel.getAgent_pic3()).fit().into(holder.agent3);
        holder.nameagent4.setText(groupModel.getAgent_name4());
        holder.noagent4.setText(groupModel.getAgent_number4());
        Picasso.with(context).load(groupModel.getAgent_pic4()).fit().into(holder.agent4);

        Picasso.with(context).load(groupModel.getTop_banner()).fit().centerCrop().into(holder.top_banner);
        Picasso.with(context).load(groupModel.getMid_banner2()).fit().into(holder.mid_banner);
        Picasso.with(context).load(groupModel.getContact_pic()).fit().into(holder.contact_pic);
        holder.callgroupagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + groupModel.getContact_number()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);
            }
        });
    }

    public int getItemCount() {
        return groupModelList.size();
    }
}
