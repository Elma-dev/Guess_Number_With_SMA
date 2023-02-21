package ma.enset.playersCA;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

public class CAgent extends Agent {
    @Override
    public void setup(){
        Object[] objects=this.getArguments();
            for(Object o: objects)
                System.out.println((String)o);
        /**********************Behaviours*********************/
        /*
        addBehaviour(new Behaviour() {
            int count=0;
            @Override
            public void action() {

                System.out.println("-----------------------");
                System.out.println("count : "+count);
                ++count;
            }

            @Override
            public boolean done() {
                return count==10;
            }
        });
         */

        /*
        addBehaviour(new OneShotBehaviour(){
            public void action(){
                System.out.println("I do action one time!!!");
            }
        });
         */

        /*
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("I'm Like while(true)...");
            }
        })
         */


        /*
        addBehaviour(new TickerBehaviour(this,1000){

            @Override
            protected void onTick() {
                System.out.println("I do action each a period of time!");
            }


        });
         */

        /***********************ParallelBehaviour****************************/

        /*

        ParallelBehaviour parallelBehaviour=new ParallelBehaviour();

        this.addBehaviour(parallelBehaviour);
        parallelBehaviour.addSubBehaviour(new Behaviour(){
            public int a=0;
            @Override
            public void action() {
                System.out.println("========= Behaviour ==========");
                System.out.println(a++);
            }

            @Override
            public boolean done() {
                return a==10;
            }

        });

        parallelBehaviour.addSubBehaviour(new OneShotBehaviour(){
            @Override
            public void action() {
                System.out.println("========= OnShotBehaviour ==========");
            }
        });

        parallelBehaviour.addSubBehaviour(new CyclicBehaviour(){
            @Override
            public void action() {
                System.out.println("========= CyclicBehaviour ==========");
            }
        });

         */

        System.out.println(getAID().getLocalName());

        /**************** ACL : Agent Connection Language (Send)*******************/
        /**************** ACL : Agent Connection Language (Receive)****************/

        this.addBehaviour(new CyclicBehaviour(){
            @Override
            public void action(){
                ACLMessage message=receive();
                if(message!=null){
                    System.out.println("-------------------------------------------------------------");
                    System.out.println("I'm "+getAgent().getLocalName());
                    System.out.println("Message : "+message.getContent());
                    System.out.println("Sender : "+message.getSender().getLocalName());
                    System.out.println("Act : "+ACLMessage.getPerformative(message.getPerformative()));
                    /*****Resend The Message*****/
                    ACLMessage rmsg=new ACLMessage(ACLMessage.INFORM);
                    rmsg.setContent("Message who i receive : '"+message.getContent()+"'.");
                    rmsg.addReceiver(message.getSender());
                    send(rmsg);

                }
                else block();
            }
        });




    }

    @Override
    public void beforeMove(){
        System.out.println("Before move");
    }
    public void afterMove(){
        System.out.println("After Move");
    }

    @Override
    public void doDelete() {
        System.out.println("I'm going to die!");
    }

    @Override
    protected void takeDown() {
        System.out.println("I'm die.");
    }
}
