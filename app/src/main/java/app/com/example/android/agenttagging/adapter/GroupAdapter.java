package app.com.example.android.agenttagging.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
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
        ImageView top_banner,mid_banner,contact_pic,callgroupagent;
        TextView company_des,company_recruit,contact_name,contact_number;
        LinearLayout addAgent;
        int imagecount = 0;
        int midImageCount = 0;
        Handler h = new Handler();
        int delay = 5000;

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
        }
    }

    public GroupAdapter(Context context,List<GroupModel> groupModelList){
        this.context = context;
        this.groupModelList = groupModelList;
    }

    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GroupModel groupModel = (GroupModel) this.groupModelList.get(position);
        holder.company_des.setText(groupModel.getCompany_des());
        holder.company_recruit.setText(groupModel.getCompany_recruit());
        holder.contact_name.setText(groupModel.getContact_name());
        holder.contact_number.setText(groupModel.getContact_number());

        for (int i=0; i<groupModel.getAgentsName().size();i++){

            LinearLayout addlinearLayout = new LinearLayout(context);
            addlinearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(27, 27, 27, 27);
            addlinearLayout.setLayoutParams(layoutParams);

            ImageView addimageView = new ImageView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(360, 360);
            addimageView.setLayoutParams(lp);
            Picasso.with(context).load(groupModel.getAgentsImage().get(i)).into(addimageView);

            TextView addtextView = new TextView(context);
            addtextView.setText(groupModel.getAgentsName().get(i));
            addtextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            addtextView.setTypeface(Typeface.DEFAULT_BOLD);
            addtextView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.setMargins(0,27,0,0);
            addtextView.setLayoutParams(params);
            addtextView.setTextColor(ContextCompat.getColor(context, R.color.textmain));

            TextView addphonetext = new TextView(context);
            addphonetext.setText(groupModel.getAgentsPhone().get(i));
            addphonetext.setLayoutParams(params);
            addphonetext.setGravity(Gravity.CENTER);
            addphonetext.setTextColor(ContextCompat.getColor(context, R.color.textmain));

            addlinearLayout.addView(addimageView);
            addlinearLayout.addView(addtextView);
            addlinearLayout.addView(addphonetext);

            holder.addAgent.addView(addlinearLayout);
        }




        holder.top_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.imagecount == groupModel.getTopBanner().size()-1){
                    holder.imagecount = 0;
                }
                else holder.imagecount++;

                Picasso.with(context).load(groupModel.getTopBanner().get(holder.imagecount)).fit().into(holder.top_banner);
            }
        });



        holder.h.postDelayed(new Runnable(){
            public void run(){
                if (holder.imagecount == groupModel.getTopBanner().size()-1){
                    holder.imagecount = 0;
                }
                else holder.imagecount++;

                if (holder.midImageCount == groupModel.getMidBanner().size()-1){
                    holder.midImageCount = 0;
                }
                else holder.midImageCount++;

                Picasso.with(context).load(groupModel.getMidBanner().get(holder.midImageCount)).fit().into(holder.mid_banner);
                Picasso.with(context).load(groupModel.getTopBanner().get(holder.imagecount)).fit().into(holder.top_banner);

                holder.h.postDelayed(this, holder.delay);
            }
        }, holder.delay);


        holder.mid_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.midImageCount == groupModel.getMidBanner().size()-1){
                    holder.midImageCount = 0;
                }
                else holder.midImageCount++;

                Picasso.with(context).load(groupModel.getMidBanner().get(holder.midImageCount)).fit().into(holder.mid_banner);
            }
        });


        //Picasso.with(context).load(groupModel.getTopBanner().get(holder.imagecount)).fit().into(holder.top_banner);
       // Picasso.with(context).load(groupModel.getMidBanner().get(holder.midImageCount)).fit().into(holder.mid_banner);
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
