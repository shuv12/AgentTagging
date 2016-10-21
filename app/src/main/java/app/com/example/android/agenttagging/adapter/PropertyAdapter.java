package app.com.example.android.agenttagging.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        TextView propertyHeadline, propertyAddress, propertyType, propertyOwner, propertyPrice,propertyPurpose, propertyPricePerUnit, propertyArea, propertyAreaUnit;
        ImageView propertyImage;
       // private final Context context;
        LinearLayout mainll;

        public ViewHolder(View itemView) {
            super(itemView);
           // context = itemView.getContext();
            this.propertyAddress = (TextView) itemView.findViewById(R.id.property_address);
            this.propertyHeadline = (TextView) itemView.findViewById(R.id.property_headline);
            //this.propertyOwner = (TextView) itemView.findViewById(R.id.property_owner);
            this.propertyPurpose = (TextView) itemView.findViewById(R.id.property_purpose);
            this.propertyType = (TextView) itemView.findViewById(R.id.property_type);
            this.propertyPrice = (TextView) itemView.findViewById(R.id.property_price);
            this.propertyImage = (ImageView) itemView.findViewById(R.id.property_image);
            this.propertyAreaUnit = (TextView) itemView.findViewById(R.id.property_area_unit);
            this.propertyArea = (TextView) itemView.findViewById(R.id.property_area);
            this.propertyPricePerUnit = (TextView) itemView.findViewById(R.id.property_price_per_unit);
            this.mainll = (LinearLayout) itemView.findViewById(R.id.mainll);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;
                    intent = new Intent(context, PropertyDetails.class);
                    context.startActivity(intent);
                }
            });*/
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
        final PropertyModel propertyModel = (PropertyModel) this.propertyModelList.get(position);
        holder.propertyAddress.setText(propertyModel.getPropertyAddress());
        holder.propertyPrice.setText(propertyModel.getPropertyPrice());
        holder.propertyHeadline.setText(propertyModel.getPropertyHeadline());
        holder.propertyType.setText(propertyModel.getPropertyType());
        holder.propertyPurpose.setText(propertyModel.getPropertyPurpose());
        holder.propertyPricePerUnit.setText(propertyModel.getPropertyPricePerUnit());
        holder.propertyArea.setText(propertyModel.getPropertyArea());
        holder.propertyAreaUnit.setText(propertyModel.getPropertyAreaUnit());
        Picasso.with(context).load(propertyModel.getPropertyPic()).resize(300, 400).into(holder.propertyImage);
        holder.mainll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PropertyDetails.class);
                intent.putExtra("PropertyID",propertyModel.getPropertyID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return propertyModelList.size();
    }
}
