package ma.enset.playersCA;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class PlayerAgent extends GuiAgent {
    private PlayersContainer containerPlayer;


    @Override
    public void setup(){

        if(this.getArguments().length!=0){
            containerPlayer=(PlayersContainer)getArguments()[0];
            //setHandle Of Agent for specify the agent
            containerPlayer.playerAgent=this;
        }

        //Listen to the msgBox
        addBehaviour(new CyclicBehaviour(){
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

    }
    public void takeDown(){

    }

    public void afterMove(){

    }

    public void beforeMove(){

    }
    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        String number= (String) guiEvent.getParameter(0);
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(number);
        message.addReceiver(new AID("server", jade.core.AID.ISLOCALNAME));
        send(message);
    }
}
