package ejercicios.eje11;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.Behaviour;

public class ParallelBehaviourAgent extends Agent{
    private static final long serialVersionUID = 1L;
    public static final int WHEN_ALL = 0;
    public static final int WHEN_ANY = 1;
    protected void setup(){
        ParallelBehaviour pb = new ParallelBehaviour(this,2);
        pb.addSubBehaviour(new OneShotBehaviour(this){
            public void action(){
                System.out.println("one");
            }
        });
        pb.addSubBehaviour(new OneShotBehaviour(this){
            public void action(){
                System.out.println("two");
            }
        });
        pb.addSubBehaviour(new OneShotBehaviour(this){
            public void action(){
                System.out.println("three");
            }
        });
        pb.addSubBehaviour(new OneShotBehaviour(this){
            public void action(){
                System.out.println("four");
            }
        });
        addBehaviour(pb);
    }
}