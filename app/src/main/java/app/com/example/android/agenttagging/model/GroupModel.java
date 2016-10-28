package app.com.example.android.agenttagging.model;

import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shuvam on 09-10-2016.
 */

public class GroupModel implements Serializable {
    private static final long serialVersionUID = 1;
    private String top_banner;
    private String mid_banner1,mid_banner2;
    private ArrayList<String> agentsName,agentsImage,agentsPhone;
    private String company_des;
    private String company_recruit;
    private String contact_name;
    private String contact_number;
    private String contact_pic;
    private LinearLayout addAgent;

    public LinearLayout getAddAgent() {return addAgent;}

    public void setAddAgent(LinearLayout addAgent){this.addAgent = addAgent;}


    public ArrayList<String> getAgentsName(){return agentsName;}

    public void setAgentsName(ArrayList<String> agentsName){this.agentsName = agentsName;}

    public ArrayList<String> getAgentsImage(){return agentsImage;}

    public void setAgentsImage(ArrayList<String> agentsImage){this.agentsImage = agentsImage;}

    public ArrayList<String> getAgentsPhone(){return agentsPhone;}

    public void setAgentsPhone(ArrayList<String> agentsPhone){this.agentsPhone = agentsPhone;}


    public String getTop_banner() {
        return top_banner;
    }

    public void setTop_banner(String top_banner) {
        this.top_banner = top_banner;
    }

    public String getMid_banner1() {
        return mid_banner1;
    }

    public void setMid_banner1(String mid_banner1) {
        this.mid_banner1 = mid_banner1;
    }

    public String getMid_banner2() {
        return mid_banner2;
    }

    public void setMid_banner2(String mid_banner2) {
        this.mid_banner2 = mid_banner2;
    }

    public String getCompany_des() {
        return company_des;
    }

    public void setCompany_des(String company_des) {
        this.company_des = company_des;
    }

    public String getCompany_recruit() {
        return company_recruit;
    }

    public void setCompany_recruit(String company_recruit) {
        this.company_recruit = company_recruit;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getContact_pic() {
        return contact_pic;
    }

    public void setContact_pic(String contact_pic) {
        this.contact_pic = contact_pic;
    }
}
