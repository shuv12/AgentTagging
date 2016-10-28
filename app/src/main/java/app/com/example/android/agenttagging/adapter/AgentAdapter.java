package app.com.example.android.agenttagging.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.com.example.android.agenttagging.R;
import app.com.example.android.agenttagging.ViewProfile;
import app.com.example.android.agenttagging.model.AgentModel;

/**
 * Created by shuvam on 07-09-2016.
 */
public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter.ViewHolder> {
    private Context context;
    private List<AgentModel> agentModelList;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView agentName, agentNumber;
        ImageView agentPic;
        Button viewAgentProfile;
        //private final Context context;

        public ViewHolder(final View itemView) {
            super(itemView);
            //context = itemView.getContext();
            this.agentName = (TextView) itemView.findViewById(R.id.agentname);
            this.agentNumber = (TextView) itemView.findViewById(R.id.agentnum);
            this.agentPic = (ImageView) itemView.findViewById(R.id.agentpic);
            this.viewAgentProfile = (Button) itemView.findViewById(R.id.viewagentprofile);
           /* viewAgentProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;
                    intent = new Intent(context, ViewProfile.class);
                    context.startActivity(intent);
                }
            });*/

        }
    }

    public AgentAdapter(Context context, List<AgentModel> agentModelList) {
        this.context = context;
        this.agentModelList = agentModelList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_display_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final AgentModel agentModel = (AgentModel) this.agentModelList.get(position);
        holder.agentName.setText(agentModel.getAgentName());
        holder.agentNumber.setText(agentModel.getAgentNumber());
        //holder.agentPic.setImageResource(agentModel.getAgentPic());
        Picasso.with(context).load(agentModel.getAgentPic()).resize(300, 400).into(holder.agentPic);
        holder.viewAgentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewProfile.class);
                intent.putExtra("agentdetailID",agentModel.getAgentID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return agentModelList.size();
    }
}


