package org.example.containers;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import org.example.Agents.PlayerAgent;

public class AgentContainers {

    public static void main(String[] args) throws Exception {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);


        AgentController agent=agentContainer.createNewAgent("agent1","org.example.Agents.CAgent",new Object[]{"Book1","Book2"});
        AgentController agent2=agentContainer.createNewAgent("agent2","org.example.Agents.CAgent",new Object[]{"Book1","Book2"});

        agent.start();
        agent2.start();


    }


}
