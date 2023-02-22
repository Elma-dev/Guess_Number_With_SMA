package ma.enset.containers;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class MainContainer {
    public static void main(String[] args) throws Exception {
        //Environment Instantiation
        Runtime runtime=Runtime.instance();
        //Configure Profile Jade
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.GUI,"true");

        //creation of main container
        AgentContainer mainContainer = runtime.createMainContainer(profile);
        mainContainer.start();
    }
}