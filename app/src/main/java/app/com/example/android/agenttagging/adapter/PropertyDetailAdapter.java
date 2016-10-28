package app.com.example.android.agenttagging.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.com.example.android.agenttagging.R;
import app.com.example.android.agenttagging.ViewProfile;
import app.com.example.android.agenttagging.model.PropertyDetailModel;

/**
 * Created by shuvam on 04-10-2016.
 */

public class PropertyDetailAdapter extends RecyclerView.Adapter<PropertyDetailAdapter.ViewHolder> {
    private Context context;
    private List<PropertyDetailModel> propertyDetailModelList;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView propertyDetailtitle,propertyDetailAskingPrice,propertyDetailArea,propertyDetailPriceperUnit, propertyDetailAddress, propertyDetailAreaUnit, propertyDetailNoofBedrooms,propertyNoofBathrooms,
                propertyDetailListedDate, propertyDetailPurpose,propertyDetailUnitNo,propertyDetailDistrict,propertyDetailBlockno,
                propertyDetailPostcode,propertyDetailTenure, pdtotalnounits,pdtotalfloors,pdtopyear,pdfurnished,pdfloor,pdspl,
                pdoispace,pdff,propertyDetailDescription,propertyDetailFacilities;
        ImageView propertyUserPic,propertyDetailPic,videoThumbnail;
        Button show_more,show_less,call_agent;
        LinearLayout show_more_layout, blockll, districtll,thisIsMyProperty;
        //YouTubePlayerView youTubePlayerView;
       // Context context;
        View blockLine,districtLine;
        RelativeLayout playVideo;
        //public static final String API_KEY = "AIzaSyAhajiuQLmXvRNz-xL570WNaHMrA_Jw1Vk";

        public ViewHolder(View itemView) {
            super(itemView);
           // context = itemView.getContext();
            this.propertyDetailAddress = (TextView) itemView.findViewById(R.id.property_detail_address);
            this.propertyDetailAreaUnit = (TextView) itemView.findViewById(R.id.property_detail_area_unit);
            this.propertyDetailBlockno = (TextView) itemView.findViewById(R.id.pro_block_no);
            this.propertyDetailAskingPrice = (TextView) itemView.findViewById(R.id.property_detail_price);
            this.propertyDetailDescription = (TextView) itemView.findViewById(R.id.pro_description);
            this.propertyDetailArea = (TextView) itemView.findViewById(R.id.property_detail_area);
            this.propertyDetailPriceperUnit = (TextView) itemView.findViewById(R.id.property_detail_price_per_unit);
            this.propertyDetailDistrict = (TextView) itemView.findViewById(R.id.pro_district);
            this.pdff = (TextView) itemView.findViewById(R.id.pro_fnf);
            this.propertyDetailtitle = (TextView) itemView.findViewById(R.id.property_detail_headline);
            this.propertyDetailNoofBedrooms = (TextView) itemView.findViewById(R.id.noofbeds);
            this.propertyNoofBathrooms = (TextView) itemView.findViewById(R.id.noofbaths);
            this.propertyDetailListedDate = (TextView) itemView.findViewById(R.id.pro_listed);
            this.propertyDetailPurpose = (TextView) itemView.findViewById(R.id.pro_listing_type);
            this.propertyDetailUnitNo = (TextView) itemView.findViewById(R.id.pro_unit_no);
            this.propertyDetailPostcode = (TextView) itemView.findViewById(R.id.pro_postcode);
            this.propertyDetailTenure = (TextView) itemView.findViewById(R.id.pro_tenure);
            this.pdtotalnounits = (TextView) itemView.findViewById(R.id.pro_total_unit);
            this.pdtotalfloors = (TextView) itemView.findViewById(R.id.pro_total_floor);
            this.pdtopyear = (TextView) itemView.findViewById(R.id.pro_top_year);
            this.pdfurnished = (TextView) itemView.findViewById(R.id.pro_furnished);
            this.pdfloor = (TextView) itemView.findViewById(R.id.pro_floor);
            this.pdspl = (TextView) itemView.findViewById(R.id.pro_spl_feat);
            this.pdoispace = (TextView) itemView.findViewById(R.id.pro_iospace);
            this.propertyDetailFacilities = (TextView) itemView.findViewById(R.id.pro_facilities);
            this.propertyUserPic = (ImageView) itemView.findViewById(R.id.profile_image);
            this.propertyDetailPic = (ImageView) itemView.findViewById(R.id.detail_property_image);
            this.show_more = (Button) itemView.findViewById(R.id.show_more_button);
            this.show_less = (Button) itemView.findViewById(R.id.show_less);
            this.show_more_layout = (LinearLayout) itemView.findViewById(R.id.show_more);
            this.call_agent = (Button) itemView.findViewById(R.id.call_agent);
            this.blockll = (LinearLayout) itemView.findViewById(R.id.blockll);
            this.districtll = (LinearLayout) itemView.findViewById(R.id.districtll);
            this.blockLine = (View) itemView.findViewById(R.id.blockline);
            this.districtLine = (View) itemView.findViewById(R.id.districtline);
            this.thisIsMyProperty = (LinearLayout) itemView.findViewById(R.id.mypropertydetail);
            //this.youTubePlayerView = (YouTubePlayerView) itemView.findViewById(R.id.youtube_player_view);

            this.videoThumbnail = (ImageView) itemView.findViewById(R.id.youtubetumbnail);
            this.playVideo = (RelativeLayout) itemView.findViewById(R.id.playvideo);

           /* propertyUserPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ViewProfile.class);
                    context.startActivity(intent);
                }
            });*/


            show_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_more_layout.setVisibility(View.VISIBLE);
                    show_more.setVisibility(View.GONE);
                }
            });

            show_less.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_more.setVisibility(View.VISIBLE);
                    show_more_layout.setVisibility(View.GONE);
                }
            });
        }
    }

    public PropertyDetailAdapter(Context context, List<PropertyDetailModel> propertyDetailModelList) {
        this.context = context;
        this.propertyDetailModelList = propertyDetailModelList;
    }

    public PropertyDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PropertyDetailAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.property_detail_display_item, parent, false));
    }

    public void onBindViewHolder(final PropertyDetailAdapter.ViewHolder holder, int position) {
        final PropertyDetailModel propertyDetailModel = (PropertyDetailModel) this.propertyDetailModelList.get(position);
        holder.propertyDetailAddress.setText(propertyDetailModel.getPropertyDetailAddress());
        holder.propertyDetailAreaUnit.setText(propertyDetailModel.getPropertyDetailAreaUnit());
        if (propertyDetailModel.getPropertyDetailBlockno().isEmpty()) {
            holder.blockll.setVisibility(View.GONE);
            holder.blockLine.setVisibility(View.GONE);
        } else {
            holder.propertyDetailBlockno.setText(propertyDetailModel.getPropertyDetailBlockno());
        }

        if (propertyDetailModel.getPropertyDetailDistrict().isEmpty()) {
            holder.districtll.setVisibility(View.GONE);
            holder.districtLine.setVisibility(View.GONE);
        } else {
            holder.propertyDetailDistrict.setText(propertyDetailModel.getPropertyDetailDistrict());
        }
        holder.propertyDetailDescription.setText(propertyDetailModel.getPropertyDetailDescription());

        holder.pdff.setText(propertyDetailModel.getPdff());
        holder.propertyDetailPurpose.setText(propertyDetailModel.getPropertyDetailPurpose());
        holder.propertyDetailAskingPrice.setText(propertyDetailModel.getPropertyDetailAskingPrice());
        holder.propertyDetailArea.setText(propertyDetailModel.getPropertyDetailArea());
        holder.propertyDetailPriceperUnit.setText(propertyDetailModel.getPropertyDetailPriceperUnit());
        holder.propertyDetailtitle.setText(propertyDetailModel.getPropertyDetailtitle());
        holder.propertyDetailNoofBedrooms.setText(propertyDetailModel.getPropertyDetailNoofBedrooms());
        holder.propertyNoofBathrooms.setText(propertyDetailModel.getPropertyNoofBathrooms());
        holder.propertyDetailListedDate.setText(propertyDetailModel.getPropertyDetailListedDate());
        holder.propertyDetailUnitNo.setText(propertyDetailModel.getPropertyDetailUnitNo());
        holder.propertyDetailPostcode.setText(propertyDetailModel.getPropertyDetailPostcode());
        holder.propertyDetailTenure.setText(propertyDetailModel.getPropertyDetailTenure());
        holder.pdtotalnounits.setText(propertyDetailModel.getPdtotalnounits());
        holder.pdtotalfloors.setText(propertyDetailModel.getPdtotalfloors());
        holder.pdtopyear.setText(propertyDetailModel.getPdtopyear());
        holder.pdfurnished.setText(propertyDetailModel.getPdfurnished());
        holder.pdfloor.setText(propertyDetailModel.getPdfloor());
        holder.pdspl.setText(propertyDetailModel.getPdspl());
        holder.pdoispace.setText(propertyDetailModel.getPdoispace());
        holder.propertyDetailFacilities.setText(propertyDetailModel.getPropertyDetailFacilities());
        Picasso.with(context).load(propertyDetailModel.getPropertyUserPic()).fit().into(holder.propertyUserPic);
        Picasso.with(context).load(propertyDetailModel.getPropertyDetailPic()).fit().into(holder.propertyDetailPic);
        holder.call_agent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + propertyDetailModel.getAgentnumber()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(callIntent);
            }
        });

        if (propertyDetailModel.getIsMyproperty()) {
            holder.call_agent.setVisibility(View.GONE);
            holder.thisIsMyProperty.setVisibility(View.VISIBLE);
        } else {
            holder.thisIsMyProperty.setVisibility(View.GONE);
            holder.call_agent.setVisibility(View.VISIBLE);
        }

        if (propertyDetailModel.getVideoID() != null || propertyDetailModel.getVideoID() != ""){
            Picasso.with(context).load("http://img.youtube.com/vi/" + propertyDetailModel.getVideoID() + "/default.jpg").fit().into(holder.videoThumbnail);
            holder.playVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + propertyDetailModel.getVideoID()));
                    appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + propertyDetailModel.getVideoID()));
                    webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        context.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        context.startActivity(webIntent);
                    }
                }
            });
        }else {
            holder.videoThumbnail.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.novideo));
            //Picasso.with(context).load(R.drawable.novidd).fit().into(holder.videoThumbnail);
        }

        holder.propertyUserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myProfile = new Intent(context, ViewProfile.class);
                myProfile.putExtra("myprofile",true);
                myProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent agentProfile = new Intent(context, ViewProfile.class);
                agentProfile.putExtra("agentdetailID",propertyDetailModel.getUserId());
                agentProfile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (propertyDetailModel.getIsMyproperty()){
                    context.startActivity(myProfile);
                }
                else context.startActivity(agentProfile);

            }
        });


    }


    public int getItemCount() {
        return propertyDetailModelList.size();
    }
}
