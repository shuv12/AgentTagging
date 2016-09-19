package app.com.example.android.agenttagging.model;

import java.io.Serializable;

/**
 * Created by shuvam on 30-08-2016.
 */
public class PropertyModel implements Serializable {
    private static final long serialVersionUID = 1;
    private String propertyType;
    private String propertyOwner;
    private String propertyAddress;
    private String propertyHeadline;
    private String propertyPic;
    private String propertyPrice;
    private String views;


    public String getPropertyPic() {
        return this.propertyPic;
    }

    public void setPropertyPic(String propertyPic) {
        this.propertyPic = propertyPic;
    }


    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyOwner() {
        return this.propertyOwner;
    }

    public void setPropertyOwner(String propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    public String getPropertyAddress() {
        return this.propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getPropertyPrice() {
        return this.propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getPropertyHeadline() {
        return this.propertyHeadline;
    }

    public void setPropertyHeadline(String propertyHeadline) {
        this.propertyHeadline = propertyHeadline;
    }

    public String getViews() {
        return this.views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}

