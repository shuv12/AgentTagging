package app.com.example.android.agenttagging.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.com.example.android.agenttagging.R;
import app.com.example.android.agenttagging.model.AgentModel;

/**
 * Created by shuvam on 07-09-2016.
 */
public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter.ViewHolder> {
    private Context context;
    private List<AgentModel> agentModelList;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder{
        TextView agentName,agentNumber;
        ImageView agentPic;

        public ViewHolder(View itemView){
            super(itemView);
            this.agentName = (TextView) itemView.findViewById(R.id.agentname);
            this.agentNumber = (TextView) itemView.findViewById(R.id.agentnum);
            this.agentPic = (ImageView) itemView.findViewById(R.id.agentpic);
        }
    }

    public AgentAdapter(Context context, List<AgentModel> agentModelList){
        this.context = context;
        this.agentModelList = agentModelList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_display_item,parent,false));
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        AgentModel agentModel = (AgentModel) this.agentModelList.get(position);
        holder.agentName.setText(agentModel.getAgentName());
        holder.agentNumber.setText(agentModel.getAgentNumber());
        //holder.agentPic.setImageResource(agentModel.getAgentPic());
        Picasso.with(context).load(agentModel.getAgentPic()).resize(300,400).into(holder.agentPic);
    }

    public int getItemCount(){
        return agentModelList.size();
    }
}


