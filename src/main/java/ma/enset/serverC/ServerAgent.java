package ma.enset.serverC;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class ServerAgent extends Agent {
    private ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
    @Override
    protected void setup() {
        final int number = (int) (Math.random() * 100);
        System.out.println(number);
        final DFAgentDescription[] dfdTemplate = new DFAgentDescription[1];

        parallelBehaviour.addSubBehaviour(new TickerBehaviour(this,5000){
            @Override
            protected void onTick() {
                //System.out.println("I'm Server");
                dfdTemplate[0] =new DFAgentDescription();

                ServiceDescription serviceDescription=new ServiceDescription();
                serviceDescription.setType("Players");

                dfdTemplate[0].addServices(serviceDescription);


            }
        });

        parallelBehaviour.addSubBehaviour(new CyclicBehaviour(){
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

                    AID[] playerName;
                    //get List Of players in df
                    try {
                        DFAgentDescription dfAgentDescription[]= DFService.search(myAgent, dfdTemplate[0]);
                        playerName=new AID[dfAgentDescription.length];
                        for(int i=0;i<playerName.length;i++){
                            playerName[i]=dfAgentDescription[i].getName();
                        }

                    } catch (FIPAException e) {
                        throw new RuntimeException(e);
                    }

                    /***Server Replay ****/
                    if(Double.parseDouble(recMsg.getContent())== number){
                        rspMsg.setContent("You WIN 🎉🎉🎉");
                        send(rspMsg);

                        ACLMessage alertWin=new ACLMessage(ACLMessage.INFORM);
                        alertWin.setContent(sender+" WIN, try in another Game...");
                        for (int i=0;i<playerName.length;i++){
                            if(!playerName[i].getLocalName().equals(sender))
                                alertWin.addReceiver(playerName[i]);
                        }
                        send(alertWin);

                    }else if(Double.parseDouble(recMsg.getContent())> number){
                        rspMsg.setContent("Your number is grather than ...");
                        send(rspMsg);
                    }else {
                        rspMsg.setContent("Your number is Less than...");
                        send(rspMsg);
                    }

                }else block();
            }
        });

        addBehaviour(parallelBehaviour);
    }
}
