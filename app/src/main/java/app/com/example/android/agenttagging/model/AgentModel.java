package app.com.example.android.agenttagging.model;

import java.io.Serializable;

/**
 * Created by shuvam on 07-09-2016.
 */
public class AgentModel implements Serializable {
    private static final long serialVersionUID = 1;
    private String agentPic;
    private String agentNumber;
    private String agentName;
    private String agentID;


    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getAgentPic() {
        return agentPic;
    }

    public void setAgentPic(String agentPic) {
        this.agentPic = agentPic;
    }

    public String getAgentNumber() {
        return agentNumber;
    }

    public void setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
