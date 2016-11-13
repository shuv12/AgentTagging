package app.com.example.android.agenttagging.model;

import java.io.Serializable;

/**
 * Created by shuvam on 08-09-2016.
 */
public class NotificationsModel implements Serializable {
    private String notiPic;
    private String notiText;
    private String notiforType;
    private String notiproid;
    private String notiID;
    private String notiStatus;
    private String accessToken;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getNotiPic() {
        return notiPic;
    }

    public void setNotiPic(String notiPic) {
        this.notiPic = notiPic;
    }

    public String getNotiText() {
        return notiText;
    }

    public void setNotiText(String notiText) {
        this.notiText = notiText;
    }

    public String getNotiforType() {
        return notiforType;
    }

    public void setNotiforType(String notiforType) {
        this.notiforType = notiforType;
    }

    public String getNotiStatus() {
        return notiStatus;
    }

    public void setNotiStatus(String notiStatus) {
        this.notiStatus = notiStatus;
    }

    public String getNotiproid() {
        return notiproid;
    }

    public void setNotiproid(String notiproid) {
        this.notiproid = notiproid;
    }

    public String getNotiID() {
        return notiID;
    }

    public void setNotiID(String notiID) {
        this.notiID = notiID;
    }
}

