package app.com.example.android.agenttagging.adapter;

import android.content.Context;
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
import app.com.example.android.agenttagging.model.EventModel;

/**
 * Created by shuvam on 19-09-2016.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private Context context;
    private List<EventModel> eventModelList;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView title, address, timing, date, headline, description;
        ImageView image;
        LinearLayout mainell;
        Boolean expanded = false;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.timing = (TextView) itemView.findViewById(R.id.ue_timing);
            this.title = (TextView) itemView.findViewById(R.id.ue_title);
            this.address = (TextView) itemView.findViewById(R.id.ue_address);
            this.date = (TextView) itemView.findViewById(R.id.ue_date);
            this.headline = (TextView) itemView.findViewById(R.id.ue_headline);
            this.description = (TextView) itemView.findViewById(R.id.ue_description);
            this.image = (ImageView) itemView.findViewById(R.id.ue_image);
            this.mainell = (LinearLayout) itemView.findViewById(R.id.mainell);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expanded) {
                        description.setVisibility(View.GONE);
                        headline.setVisibility(View.VISIBLE);
                    }
                    else {
                        description.setVisibility(View.VISIBLE);
                        mainell.requestFocus();
                        headline.setVisibility(View.GONE);
                    }
                    expanded = !expanded;
                }
            });
        }
    }

    public EventAdapter(Context context, List<EventModel> eventModelList) {
        this.context = context;
        this.eventModelList = eventModelList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_event_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        EventModel eventModel = (EventModel) this.eventModelList.get(position);
        holder.title.setText(eventModel.getTitle());
        holder.date.setText(eventModel.getDate());
        holder.address.setText(eventModel.getAddress());
        holder.timing.setText(eventModel.getTiming());
        holder.description.setText(eventModel.getDescription());
        holder.headline.setText(eventModel.getHeadline());
        Picasso.with(context).load(eventModel.getImage()).into(holder.image);
    }

    public int getItemCount() {
        return eventModelList.size();
    }
}
