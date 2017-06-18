package ejercicios.eje8;

import jade.core.Agent;
import jade.core.behaviours.*;

public class MiAgente2 extends Agent{

    protected void setup(){
        addBahaviour(new MiComportamiento1());
    }
    private class MiComportamiento1 extends Behaviour{
        public void action(){
            System.out.println("Mi nombre es :"+getName());
            System.out.println("Soy el primer comportamiento");

            myAgent.addBahaviour(new MiComportamiento2());
        }
        public boolean done(){
            return true;
        }
    }
    private class MiComportamiento2 extends Behaviour{
        public void action(){
            System.out.println("Soy el segundo comportamiento");
        }
        public boolean done(){
            return true;
        }
    }
}