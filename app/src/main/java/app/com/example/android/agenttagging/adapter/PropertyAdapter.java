package app.com.example.android.agenttagging.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.com.example.android.agenttagging.PropertyDetails;
import app.com.example.android.agenttagging.R;
import app.com.example.android.agenttagging.model.PropertyModel;

/**
 * Created by shuvam on 30-08-2016.
 */
public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
    private Context context;
    private List<PropertyModel> propertyModelList;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView propertyHeadline, propertyAddress, propertyType, propertyOwner, propertyPrice;
        ImageView propertyImage;
        private final Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.propertyAddress = (TextView) itemView.findViewById(R.id.property_address);
            this.propertyHeadline = (TextView) itemView.findViewById(R.id.property_headline);
            this.propertyOwner = (TextView) itemView.findViewById(R.id.property_owner);
            this.propertyType = (TextView) itemView.findViewById(R.id.property_type);
            this.propertyPrice = (TextView) itemView.findViewById(R.id.property_price);
            this.propertyImage = (ImageView) itemView.findViewById(R.id.property_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;
                    intent = new Intent(context, PropertyDetails.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    public PropertyAdapter(Context context, List<PropertyModel> propertyModelList) {
        this.context = context;
        this.propertyModelList = propertyModelList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.property_display_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        PropertyModel propertyModel = (PropertyModel) this.propertyModelList.get(position);
        holder.propertyAddress.setText(propertyModel.getPropertyAddress());
        holder.propertyPrice.setText(propertyModel.getPropertyPrice());
        holder.propertyHeadline.setText(propertyModel.getPropertyHeadline());
        holder.propertyType.setText(propertyModel.getPropertyType());
        holder.propertyOwner.setText(propertyModel.getPropertyOwner());
        //holder.propertyImage.setImageResource(getItemId(R.drawable.houses));
    }

    public int getItemCount() {
        return propertyModelList.size();
    }
}
