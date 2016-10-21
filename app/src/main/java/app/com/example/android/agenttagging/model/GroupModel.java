package app.com.example.android.agenttagging.model;

import android.widget.LinearLayout;

import java.io.Serializable;

import static app.com.example.android.agenttagging.R.id.mid_banner;

/**
 * Created by shuvam on 09-10-2016.
 */

public class GroupModel implements Serializable {
    private static final long serialVersionUID = 1;
    private String top_banner;
    private String mid_banner1,mid_banner2;
    private String agent_pic1,agent_pic2,agent_pic3,agent_pic4;
    private String agent_name1,agent_name2,agent_name3,agent_name4;
    private String agent_number1,agent_number2,agent_number3,agent_number4;
    private String company_des;
    private String company_recruit;
    private String contact_name;
    private String contact_number;
    private String contact_pic;
    private LinearLayout addAgent;

    public LinearLayout getAddAgent() {return addAgent;}

    public void setAddAgent(LinearLayout addAgent){this.addAgent = addAgent;}


    public String getAgent_pic1() {
        return agent_pic1;
    }

    public void setAgent_pic1(String agent_pic1) {
        this.agent_pic1 = agent_pic1;
    }

    public String getAgent_name1() {
        return agent_name1;
    }

    public void setAgent_name1(String agent_name1) {
        this.agent_name1 = agent_name1;
    }

    public String getAgent_number1() {
        return agent_number1;
    }

    public void setAgent_number1(String agent_number1) {
        this.agent_number1 = agent_number1;
    }


    public String getAgent_pic2() {
        return agent_pic2;
    }

    public void setAgent_pic2(String agent_pic2) {
        this.agent_pic2 = agent_pic2;
    }

    public String getAgent_name2() {
        return agent_name2;
    }

    public void setAgent_name2(String agent_name2) {
        this.agent_name2 = agent_name2;
    }

    public String getAgent_number2() {
        return agent_number2;
    }

    public void setAgent_number2(String agent_number2) {
        this.agent_number2 = agent_number2;
    }


    public String getAgent_pic3() {
        return agent_pic3;
    }

    public void setAgent_pic3(String agent_pic3) {
        this.agent_pic3 = agent_pic3;
    }

    public String getAgent_name3() {
        return agent_name3;
    }

    public void setAgent_name3(String agent_name3) {
        this.agent_name3 = agent_name3;
    }

    public String getAgent_number3() {
        return agent_number3;
    }

    public void setAgent_number3(String agent_number3) {
        this.agent_number3 = agent_number3;
    }


    public String getAgent_pic4() {
        return agent_pic4;
    }

    public void setAgent_pic4(String agent_pic4) {
        this.agent_pic4 = agent_pic4;
    }

    public String getAgent_name4() {
        return agent_name4;
    }

    public void setAgent_name4(String agent_name4) {
        this.agent_name4 = agent_name4;
    }

    public String getAgent_number4() {
        return agent_number4;
    }

    public void setAgent_number4(String agent_number4) {
        this.agent_number4 = agent_number4;
    }



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
