package ma.enset.serverC;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ServerAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("I'm Server Agent!!!");
        int number=(int) (Math.random()*100);
        System.out.println(number);

        addBehaviour(new CyclicBehaviour(){
            @Override
            public void action() {
                ACLMessage recMsg=receive();
                if(recMsg!=null){

                    System.out.println("Sender : "+recMsg.getSender());
                    System.out.println("Content : "+recMsg.getContent());
                    System.out.println("Act : "+ACLMessage.getPerformative(recMsg.getPerformative()));

                    String sender=recMsg.getSender().getLocalName();
                    ACLMessage rspMsg=new ACLMessage(ACLMessage.INFORM);
                    rspMsg.addReceiver(new AID(sender,AID.ISLOCALNAME));

                    if(Double.parseDouble(recMsg.getContent())==number){
                        rspMsg.setContent("You WIN 🎉🎉🎉");
                    }else if(Double.parseDouble(recMsg.getContent())>number){
                        rspMsg.setContent("Your number is grather than ...");
                    }else
                        rspMsg.setContent("Your number is Less than...");

                    send(rspMsg);
                }else block();
            }
        });
    }
}
