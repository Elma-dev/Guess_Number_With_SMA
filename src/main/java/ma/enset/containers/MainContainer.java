package ma.enset.containers;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class MainContainer {
    public static void main(String[] args) throws Exception {
        //Enverenment Instentiation
        Runtime runtime=Runtime.instance();
        //Configure Profile Jade
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.GUI,"true");

        //creation ofmain container
        AgentContainer mainContainer = runtime.createMainContainer(profile);
        mainContainer.start();
    }
}