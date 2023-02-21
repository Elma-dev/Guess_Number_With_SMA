package ma.enset.serverC;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class ServersContainer {
    public static void main(String[] args) throws Exception {
        Runtime instance = Runtime.instance();

        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST,"localhost");

        AgentContainer agentContainer = instance.createAgentContainer(profile);
        AgentController server = agentContainer.createNewAgent("server", "ma.enset.serverC.ServerAgent", new Object[]{});

        server.start();

    }

}
