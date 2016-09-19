package app.com.example.android.agenttagging.model;

import java.io.Serializable;

/**
 * Created by shuvam on 08-09-2016.
 */
public class NotificationsModel implements Serializable {
    private static final long serialVersionUID = 1;
    private String notiPic;
    private String notiUser;
    private String notiproType;
    private String notiforType;
    private String notiproadd;
    private String notiStatus;


    public String getNotiPic() {
        return notiPic;
    }

    public void setNotiPic(String notiPic) {
        this.notiPic = notiPic;
    }

    public String getNotiUser() {
        return notiUser;
    }

    public void setNotiUser(String notiUser) {
        this.notiUser = notiUser;
    }

    public String getNotiproType() {
        return notiproType;
    }

    public void setNotiproType(String notiproType) {
        this.notiproType = notiproType;
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

    public String getNotiproadd() {
        return notiproadd;
    }

    public void setNotiproadd(String notiproadd) {
        this.notiproadd = notiproadd;
    }
}

