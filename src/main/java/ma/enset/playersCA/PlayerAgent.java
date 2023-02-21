package ma.enset.playersCA;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;


public class PlayerAgent extends GuiAgent {
    private PlayersContainer containerPlayer;

    @Override
    public void setup(){

        if(this.getArguments().length!=0){
            containerPlayer=(PlayersContainer)getArguments()[0];
            //setHandle Of Agent for specify the agent
            containerPlayer.playerAgent=this;
        }

    }
    public void takeDown(){

    }

    public void afterMove(){

    }

    public void beforeMove(){

    }
    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        System.out.println(guiEvent.getParameter(0));
    }
}
