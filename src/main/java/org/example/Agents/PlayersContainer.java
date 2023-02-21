package org.example.Agents;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Agents.PlayerAgent;

public class PlayersContainer extends Application {

    public PlayerAgent playerAgent;
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage myStage) throws Exception{
        /***Create A Player Container***/
        PlayerContainer();

        /*****Create Window For Communicate*****/
        AnchorPane root=new AnchorPane();
        root.setPadding(new Insets(5));


        AnchorPane pane= new AnchorPane();
        pane.setPrefHeight(350);
        pane.setPrefWidth(350);
        pane.setLayoutX(25);
        pane.setLayoutY(25);
        pane.setStyle("-fx-background-color:black ; -fx-background-radius: 20");


        Text text=new Text("> Guess Number : ");
        text.setFill(Color.valueOf("#016418"));
        text.setFont(Font.font("Arial", FontWeight.BOLD,12));
        text.setLayoutY(25);
        text.setLayoutX(10);

        TextField textField=new TextField();
        textField.setLayoutX(115);
        textField.setLayoutY(8);
        textField.setStyle("-fx-border-color: black; -fx-background-color: black; -fx-text-fill: #016418");


        pane.getChildren().add(text);
        pane.getChildren().add(textField);
        root.getChildren().add(pane);



        Scene scene=new Scene(root,400,400);

        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().toString().equals("ENTER")){
                GuiEvent guiEvent=new GuiEvent(this,1);
                guiEvent.addParameter(textField.getText());
                playerAgent.onGuiEvent(guiEvent);
                textField.setDisable(true);
                textField.setStyle("-fx-opacity: 1;-fx-border-color: black; -fx-background-color: black; -fx-text-fill: #016418");
            }
        });


        myStage.setResizable(false);
        myStage.setScene(scene);
        myStage.show();
    }

    public void PlayerContainer() throws Exception{
        /*****Create Container for Players*****/
        Runtime runtime=Runtime.instance();
        //create profile jade
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST,"localhost");
        //create agentContainer
        AgentContainer agentContainer = runtime.createAgentContainer(profile);
        //create new Agent in container
        AgentController player1 = agentContainer.createNewAgent("Player1", "org.example.Agents.PlayerAgent", new Object[]{this});
        //running of agent
        player1.start();
    }

}
