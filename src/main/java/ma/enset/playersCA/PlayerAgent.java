package ma.enset.playersCA;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;



public class PlayerAgent extends GuiAgent {
    private PlayersContainer containerPlayer;
    private ParallelBehaviour parallelBehaviour;

    @Override
    public void setup(){
        parallelBehaviour=new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {

            /***********Add This agent to list of player Agent inside of DF Agent********/
            @Override
            public void action() {
                DFAgentDescription dfAgentDescription=new DFAgentDescription();
                dfAgentDescription.setName(getAID());

                ServiceDescription serviceDescription=new ServiceDescription();
                serviceDescription.setType("Players");
                serviceDescription.setName("Player");

                dfAgentDescription.addServices(serviceDescription);

                try{
                    DFService.register(this.getAgent(),dfAgentDescription);
                }catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        if(this.getArguments().length!=0){
            containerPlayer=(PlayersContainer)getArguments()[0];
            //setHandle Of Agent for specify the agent
            containerPlayer.playerAgent=this;
        }

        //Listen to the msgBox
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour(){
            @Override
            public void action() {
                ACLMessage rcvMsg=receive();
                if(rcvMsg!=null){
                    String msg=rcvMsg.getContent();
                    containerPlayer.newMessage(msg);
                }else {
                    block();
                }
            }
        });

        addBehaviour(parallelBehaviour);

    }
    public void takeDown(){
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        String number= (String) guiEvent.getParameter(0);
        //create Message
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(number);
        //add a server as a receiver
        message.addReceiver(new AID("server", jade.core.AID.ISLOCALNAME));
        //send message
        send(message);
    }
}
