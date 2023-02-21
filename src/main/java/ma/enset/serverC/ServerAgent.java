package ma.enset.serverC;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("I'm Server Agent!!!");

        addBehaviour(new CyclicBehaviour(){

            @Override
            public void action() {
                ACLMessage recMsg=receive();
                if(recMsg!=null){
                    System.out.println("Sender : "+recMsg.getSender());
                    System.out.println("Content : "+recMsg.getContent());
                    System.out.println("Act : "+ACLMessage.getPerformative(recMsg.getPerformative()));
                }else block();
            }
        });
    }
}
