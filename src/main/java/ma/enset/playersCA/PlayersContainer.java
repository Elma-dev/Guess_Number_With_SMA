package ma.enset.playersCA;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayersContainer extends Application {

    protected PlayerAgent playerAgent;
    VBox contentBox=new VBox(5);
    ObservableList<Node> observableList= FXCollections.observableArrayList();
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage myStage) throws Exception{
        /***Create A Player Container***/
        PlayerContainer();


        /*****Create Window For Communicate*****/
        AnchorPane root=new AnchorPane();
        root.setPadding(new Insets(5));

        Text titleMsg=new Text("You are in the game 👇");
        titleMsg.setLayoutX(130);
        titleMsg.setLayoutY(15);
        titleMsg.setFont(Font.font("Arial",FontWeight.BOLD,13));
        titleMsg.setFill(Color.RED);
        root.getChildren().add(titleMsg);

        AnchorPane pane= new AnchorPane();
        pane.setPrefHeight(350);
        pane.setPrefWidth(350);
        pane.setLayoutX(25);
        pane.setLayoutY(25);
        pane.setStyle("-fx-background-color:black ; -fx-background-radius: 20");


        Text text=new Text("> Guess Number : ");
        text.setFill(Color.valueOf("#016418"));
        text.setFont(Font.font("Arial", FontWeight.BOLD,12));


        TextField textField=new TextField();
        textField.setStyle("-fx-border-color: black; -fx-background-color: black; -fx-text-fill: #016418");




        HBox rowContent=new HBox();
        rowContent.getChildren().addAll(text,textField);
        rowContent.setAlignment(Pos.CENTER_LEFT);


        ListView<Node> listView =new ListView<>(observableList);
        listView.setLayoutX(10);
        listView.setLayoutY(10);
        listView.setPrefWidth(320);
        listView.setPrefHeight(320);
        listView.setStyle("-fx-control-inner-background: black;-fx-background: black;-fx-background-insets: 0; ");

        observableList.add(rowContent);

        pane.getChildren().add(listView);
        root.getChildren().add(pane);



        Scene scene=new Scene(root,400,400);

        listView.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().toString().equals("ENTER")){
                HBox hBox=(HBox) observableList.get(observableList.size()-1);
                TextField textField1=(TextField)hBox.getChildren().get(1);
                //Disable the input
                textField1.setDisable(true);
                textField1.setStyle("-fx-opacity: 1;-fx-border-color: black; -fx-background-color: black; -fx-text-fill: #016418");
                GuiEvent guiEvent=new GuiEvent(this,1);
                guiEvent.addParameter(textField1.getText());
                playerAgent.onGuiEvent(guiEvent);

            }
        });



        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        myStage.setResizable(false);
        myStage.setTitle("Guess Number");
        myStage.getIcons().add(new Image(getClass().getResource("/hat.png").toExternalForm()));
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
        AgentController player1 = agentContainer.createNewAgent("Player1", "ma.enset.playersCA.PlayerAgent", new Object[]{this});
        //running of agent
        player1.start();
    }

    public void newMessage(String message){


        Text textMsg=new Text("> "+message);
        textMsg.setFill(Color.valueOf("#016418"));
        textMsg.setFont(Font.font("Arial", FontWeight.BOLD,12));

        HBox rowContentMsg=new HBox();

        rowContentMsg.getChildren().addAll(textMsg);
        rowContentMsg.setSpacing(10);



        Text text=new Text("> Guess Number : ");
        text.setFill(Color.valueOf("#016418"));
        text.setFont(Font.font("Arial", FontWeight.BOLD,12));


        TextField textField=new TextField();
        textField.setStyle("-fx-border-color: black; -fx-background-color: black; -fx-text-fill: #016418");




        HBox rowContent=new HBox();
        rowContent.getChildren().addAll(text,textField);
        rowContent.setAlignment(Pos.CENTER_LEFT);

        if(message.indexOf("WIN")==-1){
            Platform.runLater(()->{
                observableList.addAll(rowContentMsg,rowContent);
            });
        }else{
            Platform.runLater(()->{
                observableList.addAll(rowContentMsg);
            });
        }


    }

}
